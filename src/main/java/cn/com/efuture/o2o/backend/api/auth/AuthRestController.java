package cn.com.efuture.o2o.backend.api.auth;
import cn.com.efuture.o2o.backend.mybatis.entity.User;
import cn.com.efuture.o2o.backend.security.JwtTokenUtil;
import cn.com.efuture.o2o.backend.security.entity.JwtAuthenticationRequest;
import cn.com.efuture.o2o.backend.security.entity.JwtAuthenticationResponse;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Eric on 2017-01-22.
 */
@RestController
@RequestMapping("/api")
public class AuthRestController {
    private final JwtTokenUtil jwtTokenUtil;
    //    @Autowired
    //  private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    private final AuthenticationManager authenticationManagerBean;

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuthRestController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManagerBean) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManagerBean = authenticationManagerBean;
    }

    @ApiOperation(value = "登录后获取token", notes = "登录后获取token")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public JsonResponse authenticationRequest(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) throws AuthenticationException {

        try {
//            String verifyCode = authenticationRequest.getVerifyCode();
//            String sessionId = authenticationRequest.getSessionId();
//            if(redisTemplate.opsForValue().get(sessionId) == null){
//                return JsonResponse.notOk(4001, "验证码已失效");
//            }
//            logger.error("############# verifyCode ####### " + verifyCode);
//            logger.error("###### 生成的 verifyCode ###########"+redisTemplate.opsForValue().get(sessionId));
//            logger.error("###### 生成的 verifyCode ###########"+SessionHelper.getVerifyCode());
//            if ("".equals(verifyCode) || !verifyCode.equalsIgnoreCase(redisTemplate.opsForValue().get(sessionId).toString())) {
//                return JsonResponse.notOk(4001, "验证码错误");
//            }
            // Perform the authentication
            Authentication requestAuth = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            Authentication resultAuth = authenticationManagerBean.authenticate(requestAuth);
            SecurityContextHolder.getContext().setAuthentication(resultAuth);

            // generate Token (User ID, Authorities, Device Type, Created Date)
            @SuppressWarnings("unchecked")
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) resultAuth.getAuthorities();
            String commaSprAuthorities = StringUtils.join(authorities, ",");
            User loginUser = new User();
            loginUser.setUserName(resultAuth.getName());
            loginUser.setAuthorities(commaSprAuthorities);
            String token = this.jwtTokenUtil.generateToken(loginUser, device);
            return JsonResponse.ok(new JwtAuthenticationResponse(token));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(e.getMessage());
        }
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions(HttpServletRequest request) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            logger.debug("-------1------" + username);
        } else {
            username = principal.toString();
            logger.debug("--------2-----" + username);
        }
        Map<String, Object> map = new HashMap<>();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("sessionId", request.getSession().getId());
        map.put("token", request.getHeader("x-auth-token"));
        map.put("creationTime", sd.format(request.getSession().getCreationTime()));
        map.put("MaxInactive", request.getSession().getMaxInactiveInterval());
        map.put("LastAccessedTime", sd.format(request.getSession().getLastAccessedTime()));
        map.put("SPRING_SECURITY_CONTEXT", request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
        return map;
    }


    @ApiOperation(value = "获取sessionId", notes = "获取sessionId")
    @RequestMapping(value = "/sessionId", method = RequestMethod.GET)
    public JsonResponse sessionId(HttpServletRequest request) throws AuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId",request.getSession().getId());
        return JsonResponse.ok(jsonObject);
    }


    @Value("${verifyCode_width}")
    private int width; //验证码图片的宽度

    @Value("${verifyCode_height}")
    private int height; //验证码图片的高度。

    @Value("${verifyCode_count}")
    private int codeCount; //验证码字符个数

    /**
     * codeSequence 表示字符允许出现的序列值
     */
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','L', 'J',
            'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7',
            '8', '9'};

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public void verifyCode(@RequestParam(value = "data") String data, HttpServletResponse response) throws java.io.IOException {
        int fontHeight; //字体高度
        int codeX; //第一个字符的x轴值，因为后面的字符坐标依次递增，所以它们的x轴值是codeX的倍数
        int codeY; //codeY ,验证字符的y轴值，因为并行所以值一样

        //width-4 除去左右多余的位置，使验证码更加集中显示，减得越多越集中。
        //codeCount+1     //等比分配显示的宽度，包括左右两边的空格
        codeX = (width - 4) / (codeCount + 1);
        //height - 10 集中显示验证码
        fontHeight = height - 10;
        codeY = height - 7;

        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        gd.setFont(font);
        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.gray);
        for (int i = 0; i < 16; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();
        int red, green, blue;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(32)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(strRand, (i + 1) * codeX, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
//        HttpSession session = request.getSession();
//        session.setAttribute("verifyCode", randomCode.toString());
        JSONObject jsonObject = JSONObject.parseObject(data);
        String sessionId = jsonObject.getString("sessionId");
        logger.error("sessionId："+ sessionId);
        logger.error("生成的验证码："+ randomCode);
        // 将生成的sessionId 和验证码存入redis
        redisTemplate.opsForValue().set(sessionId,randomCode);
        redisTemplate.expire(sessionId,600, TimeUnit.SECONDS);
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}
