package dba.mapper;

import dba.model.BoothFrequencyControl;

public interface BoothFrequencyControlMapper {


    /**
     * 根据boothCode返回表中的广告位频次控制信息
     * @param boothCode
     * @return
     */
    BoothFrequencyControl selectByBoothCode(String boothCode);


    /**
     * 删除没意义的配置信息
     */
    int deleteRelationNotInBooth();
}