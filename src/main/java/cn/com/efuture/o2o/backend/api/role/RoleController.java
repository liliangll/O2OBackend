package cn.com.efuture.o2o.backend.api.role;


import cn.com.efuture.o2o.backend.mybatis.entity.Role;
import cn.com.efuture.o2o.backend.mybatis.service.RoleServiceImpl;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    /*
     * 查询角色列表
     */
    @RequestMapping(value = "getRoleList", method = RequestMethod.GET)
    public JsonResponse getRoleList(@RequestParam(value = "data", required = false) String data) {
        logger.info("------------getRoleList查询角色列表----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);//parseObject方法将json字符串转换成Map
            List<Role> list = roleServiceImpl.getRoleList(map);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /*
     * 新增角色
     */
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    public JsonResponse addRole(@RequestParam(value = "data") String data) {
        logger.info("------------addRole新增角色----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            if(map.get("roleName").toString().length() > 15){
                return JsonResponse.notOk(500, "角色最大长度为15");
            }
            int n = roleServiceImpl.checkRoleName(map);
            if (n > 0) {
                return JsonResponse.notOk(500, "角色名已存在");
            }
            int id = roleServiceImpl.addRole(map);
            if (id > 0) {
                return JsonResponse.ok("角色新增成功");
            } else {
                return JsonResponse.notOk(500, "角色新增失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /*
     * 修改角色
     */
    @RequestMapping(value = "updateRole", method = RequestMethod.POST)
    public JsonResponse updateRole(@RequestParam(value = "data") String data) {
        logger.info("------------updateRole修改角色----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            if(map.get("roleName").toString().length() > 15){
                return JsonResponse.notOk(500, "角色最大长度为15");
            }
            int n = roleServiceImpl.checkRoleName(map);
            if (n > 0) {
                return JsonResponse.notOk(500, "角色名已存在");
            }
            int id = roleServiceImpl.updateRole(map);
            if (id > 0) {
                return JsonResponse.ok("角色修改成功");
            } else {
                return JsonResponse.notOk(500, "角色修改失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /*
     * 获取模块信息
     */
    @RequestMapping(value = "getRoleModule", method = RequestMethod.GET)
    public JsonResponse getRoleModule(@RequestParam(value = "data") String data) {
        logger.info("------------getRoleModule获取模块信息----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            return JsonResponse.ok(roleServiceImpl.getRoleModule(map));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /*
     * 添加与删除角色模块
     */
    @RequestMapping(value = "addRoleModule", method = RequestMethod.POST)
    public JsonResponse addRoleModule(@RequestParam(value = "data") String data) {
        logger.info("------------addRoleModule添加与删除角色模块----------");
        try {
            JSONObject map = JSONObject.parseObject(data);
            roleServiceImpl.addRoleModule(map);
            return JsonResponse.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /*
     * 查询模块
     */
    @GetMapping(value = "getModuleList")
    public JsonResponse getModuleList(@RequestParam(value = "data" ,required = false) String data) {
        logger.info("------------getModuleList模块查询----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            return JsonResponse.ok(roleServiceImpl.getModuleList(map));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    /*
     * 保存模块文档
     */
    @PostMapping(value = "saveModuleMessage")
    public JsonResponse saveModuleMessage(@RequestParam(value = "data") String data) {
        logger.info("------------saveModuleMessage保存模块文档----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            int id = roleServiceImpl.saveModuleMessage(map);
            if (id > 0) {
                return JsonResponse.ok("保存成功");
            } else {
                return JsonResponse.notOk(500, "保存失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @RequestMapping(value = "checkRoleName", method = RequestMethod.GET)
    public JsonResponse checkRoleName(@RequestParam(value = "data") String data) {
        logger.info("------------checkRoleName检查角色名是否重复----------");
        try {
            Map<String, Object> map = JSONObject.parseObject(data);
            int n = roleServiceImpl.checkRoleName(map);
            if (n > 0) {
                return JsonResponse.notOk(500, "角色名已存在");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
        return JsonResponse.ok("");
    }

}
