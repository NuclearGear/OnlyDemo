package dba.mapper;

import dba.model.AdReport;

public interface AdReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdReport record);

    int insertSelective(AdReport record);

    AdReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdReport record);

    int updateByPrimaryKey(AdReport record);




    /**
     * 根据名称删除相应的记录
     * @param name
     * @return
     */
    int deleteLikeName(String name);
}