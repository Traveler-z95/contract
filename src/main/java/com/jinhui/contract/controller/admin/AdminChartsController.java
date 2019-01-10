package com.jinhui.contract.controller.admin;

import com.jinhui.contract.service.ChartsService;
import com.jinhui.contract.vo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/charts")
public class AdminChartsController {
    @Autowired
    private ChartsService chartsService;
    /**
     * 获取合同总数信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recordAmount")
    public Object recordAmount(){
        List<Contract> contractList = chartsService.selectAmount();
        for (Contract contract : contractList){
            if (contract.getContractAmount().contains("E")){
            BigDecimal bd = new BigDecimal(contract.getContractAmount());
            String str = bd.toPlainString();
            contract.setContractAmount(str);}
        }
        System.out.print(contractList);
        return contractList;
    }

    /**
     * 获取回款情况信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recordReturned")
    public Object recordReturned(){
        List<Contract> contractList = chartsService.selectReturned();
        for (Contract contract : contractList){
            if (contract.getRecoveredAmount2015().contains("E")){
                BigDecimal RecoveredAmount2015 = new BigDecimal(contract.getRecoveredAmount2015());
                String recoveredAmount2015 = RecoveredAmount2015.toPlainString();
                contract.setRecoveredAmount2015(recoveredAmount2015);
            }
            if (contract.getRecoveredAmount2016().contains("E")){
                BigDecimal RecoveredAmount2016 = new BigDecimal(contract.getRecoveredAmount2016());
                String recoveredAmount2016 = RecoveredAmount2016.toPlainString();
                contract.setRecoveredAmount2016(recoveredAmount2016);
            }
            if (contract.getRecoveredAmount2017().contains("E")){
                BigDecimal RecoveredAmount2017 = new BigDecimal(contract.getRecoveredAmount2017());
                String recoveredAmount2017 = RecoveredAmount2017.toPlainString();
                contract.setRecoveredAmount2017(recoveredAmount2017);
            }
            if (contract.getRecoveredAmount2018().contains("E")){
                BigDecimal RecoveredAmount2018 = new BigDecimal(contract.getRecoveredAmount2018());
                String recoveredAmount2018 = RecoveredAmount2018.toPlainString();
                contract.setRecoveredAmount2016(recoveredAmount2018);
            }
            if (contract.getReceivableTotal().contains("E")){
                BigDecimal ReceivableTotal = new BigDecimal(contract.getReceivableTotal());
                String receivableTotal = ReceivableTotal.toPlainString();
                contract.setReceivableTotal(receivableTotal);
            }
        }

        System.out.print(contractList);
        return contractList;
    }
}
