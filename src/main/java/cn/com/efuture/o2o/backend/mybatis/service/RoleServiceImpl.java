package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.Module;
import cn.com.efuture.o2o.backend.mybatis.entity.Role;
import cn.com.efuture.o2o.backend.mybatis.entity.RoleModule;
import cn.com.efuture.o2o.backend.mybatis.mapper.RoleMapper;
import cn.com.efuture.o2o.backend.util.ParameterHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleServiceImpl {

    @Autowired
    private RoleMapper roleMapper;

    public int addRole(Map<String, Object> map) {
        return roleMapper.addRole(map);
    }

    public List<Role> getRoleList(Map<String, Object> map) {
        return roleMapper.getRoleList(map);
    }

    public int updateRole(Map<String, Object> map) {
        return roleMapper.updateRole(map);
    }

    public JSONObject getRoleModule(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        List<RoleModule> roleModule = roleMapper.getRoleModule(map);
        List<Map<String, Object>> list = new ArrayList<>();
        if (roleModule.size() > 0) {
            for (RoleModule module : roleModule) {
                JSONObject jsonObject = new JSONObject();
                List signList = ParameterHelper.signToSignArray(module.getSign());
                jsonObject.put("moduleId", module.getModuleId());
                jsonObject.put("sign", signList);
                list.add(jsonObject);
            }
        }
        json.put("roleModuleList", list);
        json.put("roleId", roleModule.get(0).getRoleId());

        return json;
    }

    @Transactional
    public void addRoleModule(JSONObject map) {
        Map<String, Object> tempMap = new HashMap<>();
        Integer roleId = map.getInteger("roleId");
        tempMap.put("roleId", roleId);
        roleMapper.deleteRoleModule(tempMap);
        JSONArray roleModuleList = map.getJSONArray("roleModuleList");
        for (Object o : roleModuleList) {
            JSONObject jsonObject = (JSONObject) o;
            JSONArray signs = jsonObject.getJSONArray("sign");
            Integer sign = ParameterHelper.signArrayToSign(signs);
            RoleModule roleModule = new RoleModule();
            roleModule.setSign(sign);
            roleModule.setModuleId(jsonObject.getInteger("moduleId"));
            roleModule.setRoleId(roleId);
            roleMapper.addRoleModule(roleModule);
        }
    }

    public JSONObject getModuleList(Map<String, Object> map) {
        JSONObject resultObject = new JSONObject();
        List<Module> moduleList = roleMapper.getModule(map);
        JSONArray jsonArray = new JSONArray();
        for (Module module : moduleList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("moduleId", module.getModuleId());
            jsonObject.put("enable", module.getEnable());
            jsonObject.put("moduleName", module.getModuleName());
            jsonObject.put("url", module.getUrl());
            jsonObject.put("note", module.getNote());
            jsonObject.put("messages", module.getMessages());
            jsonObject.put("sign", ParameterHelper.signToSignArray(module.getSign()));
            jsonArray.add(jsonObject);
        }
        resultObject.put("moduleList", jsonArray);
        return resultObject;

    }


    public int saveModuleMessage(Map<String, Object> map) {
        return roleMapper.saveModuleMessage(map);
    }

    public int checkRoleName(Map<String, Object> map) {
        return roleMapper.checkRoleName(map);
    }
}
