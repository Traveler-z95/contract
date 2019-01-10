package com.jinhui.contract.mapper;

import com.jinhui.contract.vo.Contract;
import com.jinhui.contract.vo.Pager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContractMapper {
    void saveContract(Contract contract);

    List<Contract> loadContract(Map<String, Object> param);

    int initPage(Pager pager);

    void deleteContract(Integer contractId);

    void updateContract(Contract contract);

    Contract searchContract(Integer contractId);

    void addContract(Contract contract);

    List<Contract> searchAllContract(Map<String, Object> param);

}
