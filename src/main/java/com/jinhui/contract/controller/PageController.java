package com.jinhui.contract.controller;

import com.jinhui.contract.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String loginPage(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user != null){
            return "forward:/admin/home";
        }
        return "login";
    }

    /**
     * 跳转到错误404页面
     */
    @RequestMapping("/error/404")
    public String error(){
        return "error/404";
    }
}
