package dba.mapper;

import dba.model.PlanMaterialRelation;

import java.util.List;

public interface PlanMaterialRelationMapper {


    /**
     * 根据planId，获取他关联的所有materialId
     */
     List<PlanMaterialRelation> selectByPlanId(Long planId);


    /**
     * 删除无用的关系
     * @return
     */
    int deleteIdNotInMaterial();


}