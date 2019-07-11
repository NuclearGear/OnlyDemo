package dba.mapper;

import dba.model.BoothTemplateRelation;

import java.util.List;

public interface BoothTemplateRelationMapper {

    BoothTemplateRelation selectById(Long id);

    /**
     * 根据templateId，获取广告位模板关系表
     * @param templateId
     * @return
     */
    List<BoothTemplateRelation> selectByTemplateId(Long templateId);


    /**
     * 根据boothCode，获取广告位模板关系表
     */
    List<BoothTemplateRelation> selectByBoothCode(String boothCode);

    /**
     * 根据templateId。获取广告位选择模板的数量
     */
    int getBoothCountByTemplateId(Long templateId);



    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteRelationNotInMaterialTemplate();



    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteRelationNotInBoothCode();
}