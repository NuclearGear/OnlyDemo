package dba.mapper;

import dba.model.PlanReport;

import java.util.List;

public interface PlanReportMapper {
    int deleteByPrimaryKey(Long id);



    PlanReport selectByPrimaryKey(Long id);



    /**
     * 根据adId， 以天位单位获取统计值
     */
    List<PlanReport> selectSumByAdId(Long adId);



    /**
     * 根据名称删除相应的记录
     * @param name
     * @return
     */
    int deleteLikeName(String name);
}