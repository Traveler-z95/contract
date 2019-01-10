package com.jinhui.contract.service;

import com.jinhui.contract.mapper.ContractMapper;
import com.jinhui.contract.vo.Contract;
import com.jinhui.contract.vo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContractService {
    @Autowired
    private ContractMapper contractMapper;

    /**
     * 保存合同信息
     * @param contract
     */
    public void saveContract(Contract contract) {
        contractMapper.saveContract(contract);
    }

    /**
     * 初始化文章分页信息
     * @param pager 分页对象
     * @return
     */
    public void initPager(Pager pager) {
        int count = contractMapper.initPage(pager);
        pager.setTotalCount(count);
    }

    /**
     * 条件查询文章（分页）
     * @param param
     * @return
     */
    public List<Contract> loadContract(Map<String, Object> param) {
        return contractMapper.loadContract(param);
    }

    /**
     * 按id删除合同信息
     * @param contractId
     */
    public void deleteContract(Integer contractId) {
        contractMapper.deleteContract(contractId);
    }

    /**
     * 更新合同信息
     * @param contract
     */
    public void updateContract(Contract contract) {
        contractMapper.updateContract(contract);
    }

    /**
     * 查询合同信息
     * @param contractId
     */
    public Contract searchContract(Integer contractId) {
        return contractMapper.searchContract(contractId);
    }

    /**
     * 手动添加合同信息
     * @param contract
     */
    public void addContract(Contract contract) {
        contractMapper.addContract(contract);
    }

    /**
     * 查询全部合同信息（条件、不分页）
     * @param param
     * @return
     */
    public List<Contract> searchAllContract(Map<String, Object> param) {
        return contractMapper.searchAllContract(param);
    }

}
