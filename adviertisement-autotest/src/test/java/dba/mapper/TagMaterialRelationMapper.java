package dba.mapper;

import dba.model.TagMaterialRelation;

import java.util.List;

public interface TagMaterialRelationMapper {


    TagMaterialRelation selectById(Long id);


    /**
     * 根据materialId的值返回标签物料的关联关系
     */
    List<TagMaterialRelation> selectByMaterialId(Long MaterialId);




    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteTagMaterialRelationNotInMaterial();


}