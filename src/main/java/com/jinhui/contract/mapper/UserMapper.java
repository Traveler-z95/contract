package com.jinhui.contract.mapper;

import com.jinhui.contract.vo.User;
import com.jinhui.contract.vo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User getUserByUserName(String userName);

    UserInfo getUserInfo(String userName);

    void updatePassWord(User user);

    void updateUserInfo(UserInfo userInfo);

    void updateAvatar(@Param("url") String url,@Param("userName") String userName);
}
