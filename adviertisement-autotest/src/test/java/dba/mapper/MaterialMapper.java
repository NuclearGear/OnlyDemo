package dba.mapper;

import dba.model.Material;

import java.util.List;

public interface MaterialMapper {

    /**
     * 根据id值获取物料的详细信息
     * @param id
     * @return
     */
    Material selectById(Long id);


    /**
     * 根据物料信息返回物料列表
     */
    List<Material> selectLikeContentKey(String likeName);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteMaterialNotInPlan();

}