package dba.mapper;

import dba.model.Period;

import java.util.List;

public interface PeriodMapper {

    Period selectByPrimaryKey(Long id);

    /**
     * 根据planId返回关系时间列表信息
     */
    List<Period> selectByPlanId(Long planId);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deletePeriodNotInPlan();
}