package dba.mapper;

import dba.model.TargettingTime;

import java.util.List;

public interface TargettingTimeMapper {

    /**
     * 根据planId返回时间定向信息表
     * @param planId
     * @return
     */
    List<TargettingTime> selectByPlanId(long planId);


    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteTargettingTimeNotInPlan();
}