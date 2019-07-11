package dba.mapper;

import dba.model.TargettingKeywords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TargettingKeywordsMapper {



    List<TargettingKeywords> selectByPlanId(Long planId);


    /**
     * 根据planId返回地域定向信息列表
     * @param planId
     * @param type  1:关键字定向的关系，2：科室定向的关系， 3：自定义标签定向的关系
     * @return
     */
    List<TargettingKeywords> selectByPlanIdType(@Param("planId") long planId,
                                                @Param("type") int type);



    /**
     * 删除关系表中无意义记录，不存在的boothCode
     */
    int deleteTargettingKeywordsNotInPlan();

}