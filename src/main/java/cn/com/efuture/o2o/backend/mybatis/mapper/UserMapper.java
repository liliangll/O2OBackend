package cn.com.efuture.o2o.backend.mybatis.mapper;

import cn.com.efuture.o2o.backend.mybatis.entity.User;
import cn.com.efuture.o2o.backend.mybatis.entity.UserRole;
import cn.com.efuture.o2o.backend.mybatis.entity.UserShop;
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
public interface UserMapper extends Serializable {

    User getUserById(int id);

    User getUserByMail(String email);

    User getUserByNameAndPassword(User user);

    int registerUser(User user);

    User getUserByName(String username);

    List<User> getUserList(Map<String, Object> map);

    List<UserShop> getUserShops(Map<String, Object> map);

    void deleteUserShop(UserShop userShop);

    void insertUserShop(UserShop userShop);

        User checkUser(Map<String, Object> map);

    void insertUser(User user);

    List<UserRole> getUserRoles(Map<String, Object> map);

    void deleteUserRole(UserRole userRole);

    void insertUserRole(UserRole userRole);

    void updateUser(User user);

    List<Map<String,Object>> getMenu(Map<String, Object> map);

    List<Map<String,Object>> getRoleModuleListByUserId(int userId);

    void addUserRetailFormat(Map<String, Object> map);

    void deleteUserRetailFormat(Map<String,Object> map);

    void deleteUserShopById(Map<String,Object> map);

    void insertUserShops(Map<String, Object> map);
}
