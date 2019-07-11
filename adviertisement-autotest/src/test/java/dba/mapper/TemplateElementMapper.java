package dba.mapper;

import dba.model.TemplateElement;

import java.util.List;

public interface TemplateElementMapper {


    TemplateElement selectById(Long id);

    /**
     * 根据templateId，返回模板元素
     */
    List<TemplateElement> selectByTemplateId(Long templateId);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteTemplateElementNotInTemplate();


}