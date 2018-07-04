package cn.com.efuture.o2o.backend.api.user;


import cn.com.efuture.o2o.backend.mybatis.entity.RetailFormat;
import cn.com.efuture.o2o.backend.mybatis.entity.User;
import cn.com.efuture.o2o.backend.mybatis.entity.UserRole;
import cn.com.efuture.o2o.backend.mybatis.entity.UserShop;
import cn.com.efuture.o2o.backend.mybatis.service.RetailFormatServiceImpl;
import cn.com.efuture.o2o.backend.mybatis.service.UserServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2017-01-17.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final RetailFormatServiceImpl retailFormatService;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserController(UserServiceImpl userServiceImpl, RetailFormatServiceImpl retailFormatService) {
        this.userServiceImpl = userServiceImpl;
        this.retailFormatService = retailFormatService;
    }

    @ApiOperation(value = "按用户名获取用户信息", notes = "通过用户id来获取用户信息")
    @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String")
    @RequestMapping(method = RequestMethod.GET)
    public JsonResponse getUserByName(@RequestParam("name") String username, HttpServletRequest request) {

        String user_name = request.getSession().getAttribute("username").toString();
        if (null == user_name || !user_name.equalsIgnoreCase(username)) {
            return JsonResponse.NO_PERMISSION;
        }
        logger.info("------------getUserByName-----------");
        try {
            User rUser = userServiceImpl.getUserByName(username);
            if (null == rUser) {
                return JsonResponse.notOk(204, "用户不存在");
            } else {
                // 记录此次登陆的IP
                String ip = request.getHeader("x-forwarded-for");
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
                logger.info("------------Host----------- {}", ip);
                User user = new User();
                user.setLast_act_ip(ip);
                user.setUserId(rUser.getUserId());
                userServiceImpl.updateUser(user);
                JSONObject resultJsonObject = new JSONObject();
                resultJsonObject.put("user", rUser);
                List<Map<String, Object>> list = userServiceImpl.getRoleModuleListByUserId(rUser.getUserId());
                if (list.size() > 0) {
                    JSONArray roleModuleList = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    // 去除重复权限
                    for (Map<String, Object> map : list) {
                        int moduleId = Integer.parseInt(map.get("moduleId").toString());
                        if (jsonObject.get("moduleId") != null && moduleId == (Integer.parseInt(jsonObject.get("moduleId").toString()))) {
                            int sign1 = Integer.parseInt(map.get("sign").toString());
                            int sign2 = Integer.parseInt(jsonObject.get("sign").toString());
                            jsonObject.put("sign", (sign1 | sign2));
                        } else {
                            jsonObject = new JSONObject();
                            jsonObject.put("moduleId", map.get("moduleId"));
                            jsonObject.put("sign", map.get("sign"));
                            roleModuleList.add(jsonObject);
                        }
                    }
                    // 拆分权限
                    for (Object o : roleModuleList) {
                        JSONObject listJSONObject = (JSONObject) o;
                        if (listJSONObject.get("sign") instanceof Integer) {
                            listJSONObject.put("sign", ParameterHelper.signToSignArray(listJSONObject.getIntValue("sign")));
                        } else {
                            break;
                        }
                    }
                    resultJsonObject.put("roleModuleList", roleModuleList);
                }
                return JsonResponse.ok(resultJsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.toString());
            return JsonResponse.SERVER_ERR;
        }
    }


    @ApiOperation(value = "按ID获取用户信息", notes = "通过用户id来获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户Id", required = true, dataType = "Int")
        @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
        public JsonResponse getUserById(@PathVariable int id) {

            logger.info("------------getUserById-----------");
            try {
                User rUser = userServiceImpl.getUserById(id);
                if (null == rUser) {
                    return JsonResponse.notOk(204, "用户不存在");
                } else {
                    return JsonResponse.ok(rUser);
                }
            } catch (Exception e) {
                logger.debug(e.toString());
                return JsonResponse.notOk(500, "数据查询失败");
            }
        }

    @ApiOperation(value = "注册用户", notes = "注册用户")
    @ApiImplicitParam(name = "用户信息", value = "用户信息", required = true, dataType = "User")
    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse registerUser(@RequestBody User user, HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        logger.info("------------Host----------- {}", ip);
        try {
            user.setLast_act_ip(ip);
            int id = userServiceImpl.registerUser(user);
            logger.info("------------registerUser----------- {}", id);
            if (id > 0) {
                return JsonResponse.ok("用户注册成功");
            } else {
                return JsonResponse.notOk(500, "用户注册失败");
            }
        } catch (Exception e) {
            logger.debug(e.toString());
            return JsonResponse.notOk(500, "用户注册失败");
        }
    }

    //校验用户名是否重复
    @RequestMapping(value = "/checkUser", method = RequestMethod.GET)
    public JsonResponse checkUser(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------checkUser-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            if (map.isEmpty()) {
                return JsonResponse.notOk("请录入数据");
            }
            User user = userServiceImpl.checkUser(map);
            if (user != null) {
                return JsonResponse.notOk("该用户名已存在");
            }
            return JsonResponse.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    //新增用户
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    public JsonResponse insertUser(@RequestParam(value = "data") String data, HttpServletRequest request) {
        logger.info("------------insertUser-----------");
        User user = JSONObject.parseObject(data, User.class);
        if (user.getUserName() != null && user.getUserName().length() > 30) {
            return JsonResponse.notOk(500, "账号最大长度为30");
        }
        if (user.getPassword() != null && user.getPassword().length() > 50) {
            return JsonResponse.notOk(500, "密码最大长度为50");
        }
        if (user.getPhone().length() > 11) {
            return JsonResponse.notOk(500, "手机最大长度为11");
        }
        if (user.getEmail().length() > 100) {
            return JsonResponse.notOk(500, "邮箱最大长度为100");
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        logger.info("------------Host----------- {}", ip);
        try {
            // 密码验证
            if (user.getPassword().matches("^[0-9]+$") || user.getPassword().matches("^[a-zA-Z]+$")) {
                return JsonResponse.notOk("密码不能为纯数字或者纯字母");
            }
            //校验用户名是否重复
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", user.getUserName());
            User user1 = userServiceImpl.checkUser(jsonObject);
            if (user1 != null) {
                return JsonResponse.notOk("该用户名已存在");
            }
            user.setLast_act_ip(ip);
            userServiceImpl.insertUser(user);
            return JsonResponse.ok("新增成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    //修改用户信息
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public JsonResponse updateUser(@RequestParam(value = "data") String data, HttpServletRequest request) {
        logger.info("------------insertUser-----------");
        User user = JSONObject.parseObject(data, User.class);
        if (user.getUserName() != null && user.getUserName().length() > 30) {
            return JsonResponse.notOk(500, "账号最大长度为30");
        }
        if (user.getPassword() != null && user.getPassword().length() > 50) {
            return JsonResponse.notOk(500, "密码最大长度为50");
        }
        if (user.getPhone().length() > 11) {
            return JsonResponse.notOk(500, "手机最大长度为11");
        }
        if (user.getEmail().length() > 100) {
            return JsonResponse.notOk(500, "邮箱最大长度为100");
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        logger.info("------------Host----------- {}", ip);
        try {
            // 密码验证
            if (user.getPassword() != null && (user.getPassword().matches("^[0-9]+$") || user.getPassword().matches("^[a-zA-Z]+$"))) {
                return JsonResponse.notOk("密码不能为纯数字或者纯字母");
            }
            user.setLast_act_ip(ip);
            userServiceImpl.updateUser(user);
            return JsonResponse.ok("修改成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 获取所有用户
     *
     * @param data 可选参数
     * @return json
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public JsonResponse getUserList(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------getUserList-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            List<User> list = userServiceImpl.getUserList(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 根据userId获取用户角色管理信息
     *
     * @param data {"userId":"xxx"}
     * @return json
     */
    @RequestMapping(value = "/getUserRoles", method = RequestMethod.GET)
    public JsonResponse getUserRoles(@RequestParam(value = "data") String data) {
        logger.info("------------getUserRoles-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            JSONArray roleList = new JSONArray();
            List<UserRole> list = userServiceImpl.getUserRoles(map);
            for (UserRole userRole : list) {
                roleList.add(userRole.getRoleId());
            }
            return JsonResponse.ok(roleList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 保存用户和用户对应关系
     *
     * @param data {"userId":"xxx";"roleList":[xx,xxx,xxxx]}
     * @return json
     */
    @RequestMapping(value = "/saveUserRoles", method = RequestMethod.POST)
    public JsonResponse saveUserRoles(@RequestParam(value = "data") String data) {
        logger.info("------------saveUserRoles-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            userServiceImpl.saveUserRoles(map);
            return JsonResponse.ok("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 根据userId获取用户门店管理信息
     *
     * @param data {"userId":"xxx"}
     * @return json
     */
    @RequestMapping(value = "/getUserShops", method = RequestMethod.GET)
    public JsonResponse getUserShops(@RequestParam(value = "data") String data) {
        logger.info("------------getUserShops-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            List<UserShop> list = userServiceImpl.getUserShops(map);
            PageInfo<UserShop> pageInfo = new PageInfo<>(list);
            JSONObject city = new JSONObject();
            JSONArray cityList = new JSONArray();
            JSONArray shopList = new JSONArray();
            for (UserShop userShop : list) {
                if (city.get(userShop.getCity()) == null) {
                    city.put(userShop.getCity(), userShop.getCity());
                    shopList = new JSONArray();
                    shopList.add(userShop.getShopId());
                    JSONObject tempJson = new JSONObject();
                    tempJson.put("city", userShop.getCity());
                    tempJson.put("shopList", shopList);
                    cityList.add(tempJson);
                } else {
                    shopList.add(userShop.getShopId());
                }
            }
            return JsonResponse.ok(pageInfo.getTotal(), cityList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 保存用户和门店对应关系
     *
     * @param data {"userId":"xxx";"userName":"xxx";"shopList":[]}
     * @return json
     */
    //
    @RequestMapping(value = "/saveUserShops", method = RequestMethod.POST)
    public JsonResponse saveUserShops(@RequestParam(value = "data") String data) {
        logger.info("------------saveUserShops-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            userServiceImpl.saveUserShops(map);
            return JsonResponse.ok("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 新增用户业态
     *
     * @param data RetailFormat
     * @return json
     */
    @RequestMapping(value = "/addUserRetailFormat", method = RequestMethod.POST)
    public JsonResponse addUserRetailFormat(@RequestParam(value = "data") String data) {
        logger.info("------------addUserRetailFormat-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            userServiceImpl.addUserRetailFormat(map);
            List<RetailFormat> list = retailFormatService.getRetailFormatListByUserId(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /**
     * 删除用户业态
     *
     * @param data {"userId":"xxx"}
     * @return json
     */
    @RequestMapping(value = "/deleteUserRetailFormat", method = RequestMethod.POST)
    public JsonResponse deleteUserRetailFormat(@RequestParam(value = "data") String data) {
        logger.info("------------deleteUserRetailFormat-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            userServiceImpl.deleteUserRetailFormat(map);
            List<RetailFormat> list = retailFormatService.getRetailFormatListByUserId(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }


    //获取用户菜单
    @RequestMapping(value = "/getMenu", method = RequestMethod.GET)
    public JsonResponse getMenu(@RequestParam(value = "data") String data) {
        logger.info("------------getMenu-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            List<Map<String, Object>> rList = new ArrayList<>();
            List<Map<String, Object>> list1 = new ArrayList<>();
            List<Map<String, Object>> list2 = new ArrayList<>();
            List<Map<String, Object>> list = userServiceImpl.getMenu(map);
            //获取一级二级菜单
            for (Map<String, Object> map1 : list) {
                if (1 == Integer.parseInt(map1.get("class").toString())) {
                    list1.add(map1);
                } else if (2 == Integer.parseInt(map1.get("class").toString())) {
                    list2.add(map1);
                }
            }
            //菜单归类
            for (Map<String, Object> cls1 : list1) {
                List<Map<String, Object>> children = new ArrayList<>();
                for (Map<String, Object> cls2 : list2) {
                    if (cls1.get("menuId").equals(cls2.get("headMenuId"))) {
                        children.add(cls2);
                        cls1.put("children", children);
                    }
                }
                rList.add(cls1);

            }
            //有模块则显示菜单
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> rMap : rList) {
                if (0 == Integer.parseInt(rMap.get("isModule").toString()) && !rMap.containsKey("children"))
                    tempList.add(rMap);
            }
            if (tempList.size() > 0) {
                rList.removeAll(tempList);
            }
            return JsonResponse.ok(rList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }

    }

    //获取主页
    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public JsonResponse getIndex(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------getIndex-----------");
        Map<String, Object> map = JSONObject.parseObject(data);
        try {
            if (map != null) {
                logger.info(map.toString());
            }
            JSONObject rData = new JSONObject();
            JSONArray list1 = new JSONArray();
            Map<String, Object> map1 = new HashMap<>();
            Map<String, Object> map2 = new HashMap<>();
            Map<String, Object> map3 = new HashMap<>();
            map1.put("name", "百度外卖");
            map1.put("Monday", 13000);
            map1.put("Tuesday", 28000);
            map1.put("Wednesday", 35000);
            map1.put("Thursday", 43900);
            map1.put("Friday", 54800);
            map1.put("Saturday", 65000);
            map1.put("Sunday", 31000);

            map2.put("name", "饿了么");
            map2.put("Monday", 73000);
            map2.put("Tuesday", 68000);
            map2.put("Wednesday", 55000);
            map2.put("Thursday", 43900);
            map2.put("Friday", 34800);
            map2.put("Saturday", 55000);
            map2.put("Sunday", 43000);

            map3.put("name", "美团");
            map3.put("Monday", 23000);
            map3.put("Tuesday", 58000);
            map3.put("Wednesday", 85000);
            map3.put("Thursday", 93900);
            map3.put("Friday", 64800);
            map3.put("Saturday", 35000);
            map3.put("Sunday", 38000);
            list1.add(map1);
            list1.add(map2);
            list1.add(map3);
            rData.put("channelData", list1);

            JSONArray list2 = new JSONArray();
            Map<String, Object> map4 = new HashMap<>();
            map4.put("x", "2017-11-01");
            map4.put("y", 3000);
            list2.add(map4);
            Map<String, Object> map5 = new HashMap<>();
            map5.put("x", "2017-11-02");
            map5.put("y", 8000);
            list2.add(map5);
            Map<String, Object> map6 = new HashMap<>();
            map6.put("x", "2017-11-03");
            map6.put("y", 8800);
            list2.add(map6);
            Map<String, Object> map7 = new HashMap<>();
            map7.put("x", "2017-11-04");
            map7.put("y", 7880);
            list2.add(map7);
            Map<String, Object> map8 = new HashMap<>();
            map8.put("x", "2017-11-05");
            map8.put("y", 7880);
            list2.add(map8);
            Map<String, Object> map9 = new HashMap<>();
            map9.put("x", "2017-11-06");
            map9.put("y", 6880);
            list2.add(map9);
            Map<String, Object> map10 = new HashMap<>();
            map10.put("x", "2017-11-07");
            map10.put("y", 10880);
            list2.add(map10);
            rData.put("totalData", list2);
            return JsonResponse.ok(rData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }
}
