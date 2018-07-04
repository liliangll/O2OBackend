package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.Module;
import cn.com.efuture.o2o.backend.mybatis.entity.Role;
import cn.com.efuture.o2o.backend.mybatis.entity.RoleModule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2017-01-17.
 */

@Repository
@Mapper
public interface RoleMapper extends Serializable {

	int addRole(Map<String, Object> map);

	List<Role> getRoleList(Map<String, Object> map);

	int updateRole(Map<String, Object> map);

	List<Module> getModule(Map<String, Object> map);

	void deleteRoleModule(Map<String, Object> tempMap);

	void addRoleModule(RoleModule roleModule);

	List<RoleModule> getRoleModule(Map<String, Object> map);

	int saveModuleMessage(Map<String, Object> map);

	int checkRoleName(Map<String, Object> map);
}
