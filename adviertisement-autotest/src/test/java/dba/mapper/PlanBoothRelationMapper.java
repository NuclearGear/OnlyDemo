package dba.mapper;

import dba.model.PlanBoothRelation;

import java.util.List;

public interface PlanBoothRelationMapper {


    /**
     * 根据planId返回关系表的信息,一个planId只能对应一个boothCode
     */
    PlanBoothRelation selectByPlanId(Long planId);

    /**
     * 根据BoothCode返回关系表的信息，查看BoothCode下有多少plan
     */
    List<PlanBoothRelation> selectByBoothCode(String boothCode);


    /**
     * 根据BoothCode返回关系表中plan状态值大于3的关系
     */
    List<PlanBoothRelation> selectByBoothCodePlanInCommit(String boothCode);


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deletePlanBoothRelationNotInPlan();


    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deletePlanBoothRelationNotInBooth();

    /**
     * 根据boothCode删除关系表中的记录
     */
    int deletePlanBoothRelationByBoothCode(String boothCode);

}