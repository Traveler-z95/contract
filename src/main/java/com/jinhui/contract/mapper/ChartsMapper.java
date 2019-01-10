package com.jinhui.contract.mapper;

import com.jinhui.contract.vo.Contract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChartsMapper {
    List<Contract> selectAmount();

    List<Contract> selectReturned();
}
