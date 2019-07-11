package dba.mapper;

import dba.model.TargettingLocation;

import java.util.List;

public interface TargettingLocationMapper {

    /**
     * 根据planId返回地域定向信息列表
     * @param planId
     * @return
     */
     List<TargettingLocation> selectByPlanId(long planId);


    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteTargettingLocationNotInPlan();


}