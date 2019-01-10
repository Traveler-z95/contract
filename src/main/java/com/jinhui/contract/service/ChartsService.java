package com.jinhui.contract.service;

import com.jinhui.contract.mapper.ChartsMapper;
import com.jinhui.contract.vo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChartsService {
    @Autowired
    private ChartsMapper chartsMapper;

    /**
     * 获取合同总数信息
     */
    public List<Contract> selectAmount() {
        return chartsMapper.selectAmount();
    }

    /**
     * 获取回款情况信息
     */
    public List<Contract> selectReturned() {
        return chartsMapper.selectReturned();
    }
}
