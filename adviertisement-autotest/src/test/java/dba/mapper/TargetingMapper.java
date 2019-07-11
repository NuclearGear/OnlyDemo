package dba.mapper;

import dba.model.Targeting;

import java.util.List;

public interface TargetingMapper {


    /**
     * 根据id值返回Targeting的基本信息
     *
     * @param id
     * @return
     */
    Targeting selectById(Long id);


    /**
     * 根据planId返回targeting关系列表信息
     * @param planId
     * @return
     */
    List<Targeting> selectByPlanId(Long planId);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteTargetingNotInPlan();

}