package dba.mapper;

import dba.model.ElementAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElementAttributeMapper {

    ElementAttribute selectById(Long id);

    /**
     * 根据elementId获取元素的属性
     */
    List<ElementAttribute> selectByEleId(Long eleId);


    /**
     * 根据elementId和type获取元素属性
     */
    ElementAttribute selectByEleIdAndType(@Param("eleId") long eleId,
                                          @Param("type") int type);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteElementAttributeNotInElement();

}