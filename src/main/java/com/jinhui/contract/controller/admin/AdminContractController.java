package com.jinhui.contract.controller.admin;

import com.jinhui.contract.service.ContractService;
import com.jinhui.contract.service.UserService;
import com.jinhui.contract.util.*;
import com.jinhui.contract.vo.Contract;
import com.jinhui.contract.vo.Pager;
import com.jinhui.contract.vo.User;
import com.jinhui.contract.vo.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/admin/contract")
public class AdminContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserService userService;

    private static Log log = LogFactory.getLog(AdminContractController.class);

    /**
     * 上传Excel
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    public String uploadExcel(@RequestParam(value = "filename") MultipartFile file,
                              HttpServletRequest request, Model model) throws IOException {
        log.info("...uploadExcel() start");

        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);

        String Msg = null;
        boolean b = false;

        //判断文件是否为空
        if (file == null) {
            Msg = "文件是为空！";
            request.getSession().setAttribute("msg", Msg);
            return "redirect:/admin/contract/index";
        }

        //获取文件名
        String name = file.getOriginalFilename();

        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）验证文件名是否合格
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0 && !ImportExcelUtil.validateExcel(name)) {
            Msg = "文件格式不正确！请使用.xls或.xlsx后缀文档。";
            request.getSession().setAttribute("msg", Msg);
            return "redirect:/admin/contract/index";
        }

        //创建处理EXCEL
        ImportExcelUtil importExcelUtil = new ImportExcelUtil();
        //解析excel，获取客户信息集合。
        List<Contract> contractList = importExcelUtil.getExcelInfo(name, file);
        if (contractList != null && !contractList.toString().equals("[]") && contractList.size() >= 1) {
            b = true;
        }

        if (b) {
            //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
            for (Contract contract : contractList) {
                //添加到数据库
                contractService.saveContract(contract);
            }
            Msg = "批量导入EXCEL成功！";
            request.getSession().setAttribute("msg", Msg);
        } else {
            Msg = "批量导入EXCEL失败！";
            request.getSession().setAttribute("msg", Msg);
        }
        return "redirect:/admin/contract/index";
    }

    /**
     * 下载Excel模板
     *
     * @param
     * @param response
     * @return
     */
    @RequestMapping("/downloadTemplate")
    public String download(HttpServletResponse response) throws IOException {
        System.out.println("控制台输出：下载模板");
        String fileName = "muban";
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
        try {
            String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("./file/muban.xlsx")).getPath();
            System.out.println(path);
            InputStream inputStream = new FileInputStream(new File(path));

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  返回值要注意，要不然就出现下面这句错误！
        //java+getOutputStream() has already been called for this response
        return null;
    }

    /**
     * 初始化文章分页信息
     * @param pager 分页对象
     * @return
     */
    @RequestMapping("/initPage")
    @ResponseBody
    public Pager initPage(Pager pager) {
        contractService.initPager(pager);
        return pager;
    }

    /**
     * 初始化合同列表
     * @param pager 分页对象 分页对象
     * @param contractSignYear 搜索条件 合同签署年度
     * @param itemCoding 搜索条件 项目编码
     * @param salePerson 搜索条件 销售人员
     * @param model
     * @return
     */
    @RequestMapping("/loadContract")
    public String loadContract(Pager pager, String contractSignYear, String itemCoding, String salePerson, Model model){
        /**
         * 设置start位置
         */
        pager.setStart(pager.getStart());
        //封装查询条件
        Map<String, Object> param = new HashMap<>();
        param.put("contractSignYear",contractSignYear);
        param.put("itemCoding",itemCoding);
        param.put("salePerson",salePerson);
        param.put("pager",pager);
        //获取文章列表
        List<Contract> contractList = contractService.loadContract(param);
        model.addAttribute("contractList",contractList);
        return "admin/contract/contractTable";
    }

    /**
     * 按id删除合同信息
     * @param contractId
     * @return
     */
    @RequestMapping("/delete/{contractId}")
    @ResponseBody
    public ResultInfo deleteContract(@PathVariable Integer contractId){
        try {

            contractService.deleteContract(contractId);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResultInfoFactory.getErrorResultInfo("删除失败!");
        }
        return ResultInfoFactory.getSuccessResultInfo();
    }

    /**
     * 查询合同信息
     * @param contractId
     * @return
     */
    @RequestMapping("/searchContract")
    public String searchContract(Integer contractId, Model model){
        Contract contract = contractService.searchContract(contractId);
        model.addAttribute("contract",contract);
        return "admin/contract/contractEditInfo";
    }

    /**
     * 更新合同信息
     * @param contract
     * @return
     */
    @RequestMapping("/update")
    public String updateContract(Contract contract){
        contractService.updateContract(contract);
        return "redirect:/admin/contract/list";
    }

    /**
     * 手动添加合同信息
     * @param contract
     * @return
     */
    @RequestMapping("/add")
    public String addContract(Contract contract){
        contractService.addContract(contract);
        return "redirect:/admin/contract/list";
    }

    /**
     * 导出全部
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportAllContract")
    public void  ExportAllContract(String contractSignYear, String itemCoding, String salePerson, HttpServletResponse response)throws Exception{
        try {
            //封装查询条件
            Map<String, Object> param = new HashMap<>();
            param.put("contractSignYear",contractSignYear);
            param.put("itemCoding",itemCoding);
            param.put("salePerson",salePerson);

            String fileName = "合同"+ Calendar.getInstance().getTimeInMillis();
            String[] columnNames = { "序号", "合同签署年度", "项目编码", "销售人员", "项目名称", "合同额", "2015年开票金额", "2015年回款金额",
                    "2016年开票金额", "2016年回款日期", "2016年回款金额", "2017年开票金额", "2017年回款日期", "2017年回款金额", "2018年开票日期",
                    "2018年开票金额", "2018年回款日期", "2018年回款金额", "开票应收款", "开票合计", "未开票金额", "回款合计"};
            List<Contract> list = contractService.searchAllContract(param);
            //ExportExcel<Contract> util = new ExportExcel<Contract>();
            ExportExcelWrapper<Contract> util = new ExportExcelWrapper<Contract>();
            //util.exportExcel(fileName,columnNames, list, new FileOutputStream("D:/test.xls"), ExportExcel.EXCEl_FILE_2007);
            util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcel.EXCEl_FILE_2007);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 导出当前页
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportCurrentContract")
    public void ExportCurrentContract(Pager pager, String contractSignYear, String itemCoding, String salePerson, HttpServletResponse response)throws Exception{
        try {
            pager.setStart(pager.getStart());
            //封装查询条件
            Map<String, Object> param = new HashMap<>();
            param.put("contractSignYear",contractSignYear);
            param.put("itemCoding",itemCoding);
            param.put("salePerson",salePerson);
            param.put("pager",pager);

            String fileName = "合同"+ Calendar.getInstance().getTimeInMillis();
            String[] columnNames = { "序号", "合同签署年度", "项目编码", "销售人员", "项目名称", "合同额", "2015年开票金额", "2015年回款金额",
                    "2016年开票金额", "2016年回款日期", "2016年回款金额", "2017年开票金额", "2017年回款日期", "2017年回款金额", "2018年开票日期",
                    "2018年开票金额", "2018年回款日期", "2018年回款金额", "开票应收款", "开票合计", "未开票金额", "回款合计"};

            List<Contract> list = contractService.loadContract(param);
            ExportExcelWrapper<Contract> util = new ExportExcelWrapper<Contract>();
            util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcel.EXCEl_FILE_2007);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
