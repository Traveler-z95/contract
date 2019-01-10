package com.jinhui.contract.controller.admin;

import com.jinhui.contract.service.UserService;
import com.jinhui.contract.util.Md5Util;
import com.jinhui.contract.util.ResultInfo;
import com.jinhui.contract.util.ResultInfoFactory;
import com.jinhui.contract.vo.User;
import com.jinhui.contract.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录的验证，弃用
     * 通过spring security去操作
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "/login/auth",method = RequestMethod.POST)
    public ResultInfo loginAuth(User user, HttpSession session){
        ResultInfo resultInfo = null;
        User userInfo = userService.loadUserByUserName(user.getUserName());
        if (userInfo==null){
            resultInfo =  ResultInfoFactory.getErrorResultInfo("账号不存在");
        }else{
            if (userInfo.getPassWord().equals(Md5Util.pwdDigest(user.getPassWord()))){
                resultInfo = ResultInfoFactory.getSuccessResultInfo();
            }else {
                resultInfo = ResultInfoFactory.getErrorResultInfo("账号或密码错误");
            }
            session.setAttribute("user",userInfo);
        }

        return resultInfo;

    }

    /**
     * 修改密码
     * @param oldPassWord
     * @param newPassWord
     * @param request
     * @return
     */
    @RequestMapping("/admin/password/update")
    @ResponseBody
    public ResultInfo updatePassword(String oldPassWord, String newPassWord, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (!Md5Util.pwdDigest(oldPassWord).equals(user.getPassWord())){
            return ResultInfoFactory.getErrorResultInfo("原密码错误！！！");
        }
        user.setPassWord(Md5Util.pwdDigest(newPassWord));
        userService.updatePassWord(user);
        return ResultInfoFactory.getSuccessResultInfo("修改成功！！！");
    }

    /**
     * 获取用户信息
     * @param model 对象
     * @return
     */
    @RequestMapping("/admin/userinfo/get")
    public String getUserInfo(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "admin/partial/userinfo";
    }

    @RequestMapping("/admin/userInfo/getUserInfo")
    public String getUserInfo1(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "admin/partial/sidebar";
    }
    /**
     * 更新个人信息
     * @param userInfo
     * @return
     */
    @RequestMapping("/admin/userinfo/update")
    @ResponseBody
    public ResultInfo updateInfo(UserInfo userInfo, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        userInfo.setUserName(user.getUserName());
        userService.updateUserInfo(userInfo);
        return ResultInfoFactory.getSuccessResultInfo("更新个人信息成功!!");
    }
}
