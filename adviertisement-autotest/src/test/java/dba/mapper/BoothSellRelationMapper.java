package dba.mapper;

import dba.model.BoothSellRelation;

import java.util.List;

public interface BoothSellRelationMapper {

    /**
     * 根据boothCode，获取广告位第三方配置关系表
     */
    List<BoothSellRelation> selectByBoothCode(String boothCode);

    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteRelationNotInSell();

}