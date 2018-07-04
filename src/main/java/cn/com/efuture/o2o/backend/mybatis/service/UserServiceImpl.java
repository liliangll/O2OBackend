package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.User;
import cn.com.efuture.o2o.backend.mybatis.entity.UserRole;
import cn.com.efuture.o2o.backend.mybatis.entity.UserShop;
import cn.com.efuture.o2o.backend.mybatis.mapper.UserMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2017-01-17.
 */

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserMapper {

    private static final String SALT = "$2a$10$necBOMlJucIQq.pEjXnQUe";

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public User getUserById(int id) {
        return this.userMapper.getUserById(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public User getUserByMail(String username) {
        return this.userMapper.getUserByMail(username);
    }


    public User getUserByNameAndPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), SALT));
        return this.userMapper.getUserByNameAndPassword(user);
    }

    @CachePut(keyGenerator = "wiselyKeyGenerator")
    public int registerUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), SALT));
        return userMapper.registerUser(user);
    }

    public User getUserByName(String username) {
        return this.userMapper.getUserByName(username);
    }

    public List<User> getUserList(Map<String, Object> map) {
        return this.userMapper.getUserList(map);
    }

    public List<UserShop> getUserShops(Map<String, Object> map) {
        return userMapper.getUserShops(map);
    }

    public void deleteUserShop(UserShop userShop) {
        userMapper.deleteUserShop(userShop);
    }

    public void insertUserShop(UserShop userShop) {
        userMapper.insertUserShop(userShop);
    }

    public User checkUser(Map<String, Object> map) {
        return userMapper.checkUser(map);
    }

    @CachePut(keyGenerator = "wiselyKeyGenerator")
    public void insertUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), SALT));
        userMapper.insertUser(user);
    }

    public List<UserRole> getUserRoles(Map<String, Object> map) {
        return userMapper.getUserRoles(map);
    }

    public void deleteUserRole(UserRole userRole) {
        userMapper.deleteUserRole(userRole);
    }

    public void insertUserRole(UserRole userRole) {
        userMapper.insertUserRole(userRole);
    }

    @CachePut(keyGenerator = "wiselyKeyGenerator")
    public void updateUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), SALT));
        }
        userMapper.updateUser(user);
    }

    public List<Map<String, Object>> getMenu(Map<String, Object> map) {
        return userMapper.getMenu(map);
    }

    @Transactional
    public void saveUserShops(Map<String, Object> map) {
        Integer userId = Integer.parseInt(map.get("userId").toString());
        UserShop userShop = new UserShop();
        userShop.setUserId(userId);
        userShop.setUserName(map.get("userName").toString());
        deleteUserShop(userShop);
        // 优化,进行批量插入
        insertUserShops(map);
//        List<String> shopList = (List<String>) map.get("shopList");
//        for (String shopId : shopList) {
//            userShop.setShopId(shopId);
//            insertUserShop(userShop);
//        }
    }

    public void insertUserShops(Map<String,Object> map) {
        userMapper.insertUserShops(map);
    }

    @Transactional
    public void saveUserRoles(Map<String, Object> map) {
        Integer userId = Integer.parseInt(map.get("userId").toString());
        @SuppressWarnings("unchecked")
        List<Integer> roleList = (List<Integer>) map.get("roleList");
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        deleteUserRole(userRole);
        for (Integer roleId : roleList) {
            userRole.setRoleId(roleId);
            insertUserRole(userRole);
        }
    }

    public List<Map<String, Object>> getRoleModuleListByUserId(int userId) {
        return userMapper.getRoleModuleListByUserId(userId);
    }

    public void addUserRetailFormat(Map<String, Object> map) {
        userMapper.addUserRetailFormat(map);
    }

    public void deleteUserRetailFormat(Map<String, Object> map) {
        deleteUserShopById(map);
        userMapper.deleteUserRetailFormat(map);
    }

    @Override
    public void deleteUserShopById(Map<String, Object> map) {
        userMapper.deleteUserShopById(map);
    }
}
