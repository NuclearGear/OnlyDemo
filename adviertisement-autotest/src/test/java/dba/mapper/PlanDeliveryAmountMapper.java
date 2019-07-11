package dba.mapper;

import dba.model.PlanDeliveryAmount;
import org.apache.ibatis.annotations.Param;

public interface PlanDeliveryAmountMapper {

    /**
     * 根据时间和planId获取当天的投放限制量
     * @return
     */
    PlanDeliveryAmount selectByPlanIdDay(@Param("planId") long planId,
                                         @Param("day") String day);



    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteDeliveryAmountNotInPlan();


}