package dba.mapper;

import dba.model.MaterialElement;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MaterialElementMapper {


    /**
     * 根据material_id,得到素材元素信息
     */
    List<MaterialElement> selectByMaterialIdType(@Param("materialId") long materialId,
                                                 @Param("type") int type);



    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteRelationNotInMaterial();

}