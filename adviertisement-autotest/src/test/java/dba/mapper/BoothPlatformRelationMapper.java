package dba.mapper;

import dba.model.BoothPlatformRelation;

import java.util.List;

public interface BoothPlatformRelationMapper {

    /**
    * 根据boothCode，获取广告位模板关系表
    */
    List<BoothPlatformRelation> selectByBoothCode(String boothCode);

    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteRelationNotInBooth();
}