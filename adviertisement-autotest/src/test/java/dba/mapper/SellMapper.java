package dba.mapper;

import dba.model.Sell;

public interface SellMapper {


    /**
     * 根据id值返回AD的基本信息
     * @param id
     * @return
     */
    Sell selectById(Long id);



}