package dba.mapper;

import dba.model.MaterialTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialTemplateMapper {


    MaterialTemplate selectById(Long id);


    MaterialTemplate selectByName(String name);


    /**
     * 根据条件获取广告主信息总量
     * @return
     */
    int getMetTemCount(@Param("name") String name);


    /**
     * 根据页面要求返回广告计划列表
     * @return
     */
    List<MaterialTemplate> getMateTemplWithPage(@Param("size") int size,
                                                @Param("crrentPage") int crrentPage,
                                                @Param("name") String name);



    /**
     * 根据名称删除相应的记录
     * @param Name
     * @return
     */
    int deleteLikeName(String Name);
}