package com.jinhui.contract.service;

import com.jinhui.contract.mapper.UserMapper;
import com.jinhui.contract.vo.User;
import com.jinhui.contract.vo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    public User loadUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    public UserInfo getUserInfo(String userName) {
        return userMapper.getUserInfo(userName);
    }

    /**
     * 修改密码
     * @param user
     */
    public void updatePassWord(User user) {
        userMapper.updatePassWord(user);
    }

    /**
     * 更新个人信息
     * @param userInfo
     */
    public void updateUserInfo(UserInfo userInfo) {
        userMapper.updateUserInfo(userInfo);
    }

    /**
     * 修改头像
     * @param url
     * @param userName
     */
    public void updateAvatar(@Param("url") String url, @Param("userName") String userName) {
        userMapper.updateAvatar(url,userName);
    }
}
