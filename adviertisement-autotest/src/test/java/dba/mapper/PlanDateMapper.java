package dba.mapper;

import dba.model.PlanDate;

import java.util.List;

public interface PlanDateMapper {


    /**
     * 根据planId返回关系时间列表信息
     */
    List<PlanDate> selectByPlanId(Long planId);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deletePlanDateNotInPlan();
}