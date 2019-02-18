package com.jinhui.contract.controller.admin;

import com.jinhui.contract.service.ContractService;
import com.jinhui.contract.service.UserService;
import com.jinhui.contract.vo.User;
import com.jinhui.contract.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 后端管理的页面跳转
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ContractService contractService;

    /**
     * 后台首页
     * @return
     */
    @RequestMapping("/home")
    public String homePage(){
        return "redirect:/admin/contract/index";
    }

    /**
     * 跳转到index页面(上传表格、下载模板)
     * @param model
     * @return
     */
    @RequestMapping("/contract/index")
    public String index(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "admin/contract/index";
    }

    /**
     * 跳转到表单页面
     * @param model
     * @return
     */
    @RequestMapping("/contract/list")
    public String tablePage(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "admin/contract/contractList";
    }

    /**
     * 跳转到合同总额图表页面
     * @param model
     * @return
     */
    @RequestMapping("/charts/amount")
    public String amount(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "admin/charts/amount";
    }

    /**
     * 跳转到回款情况图表页面
     * @param model
     * @return
     */
    @RequestMapping("/charts/returned")
    public String returned(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "admin/charts/returned";
    }
}
