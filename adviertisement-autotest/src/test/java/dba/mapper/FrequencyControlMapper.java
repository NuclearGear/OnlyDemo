package dba.mapper;

import dba.model.FrequencyControl;

public interface FrequencyControlMapper {

    /**
     * 根据planId，返回设置的频次控制信息
     * @param planId
     * @return
     */
    FrequencyControl selectByPlanId(Long planId);


    /**
     * 删除没意义的配置信息
     */
    int deleteRelationNotInPlan();
}