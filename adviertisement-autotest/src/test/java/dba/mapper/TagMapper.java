package dba.mapper;


import dba.model.Tag;

import java.util.List;

public interface TagMapper {


    /**
     * 根据标签id,
     * @param id
     * @return
     */
    Tag selectById(Long id);


    /**
     * 根据parentId获取旗下的所有标签名称，限定level desc排序
     * 已经知道parent_id =5158
     */
     List<Tag> selectDepTags();


    /**
     * 根据materialId，返回关键字的名称,tag_material_relation.tag_type=100
     */
    List<Tag> selectKeyByMaterialId(Long materialId);



    /**
     * 返回可用标签列表
     */
    List<Tag> selectUsefulTags();

}