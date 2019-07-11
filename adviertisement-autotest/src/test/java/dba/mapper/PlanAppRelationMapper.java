package dba.mapper;

import dba.model.PlanAppRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanAppRelationMapper {

    /**
     * 根据boohtCode获取所有appVersion的设定信息
     */
    List<PlanAppRelation> selectByPlanId(long planId);


    /**
     * 根据boohtCode，srcId,以及platform,获取所有appVersion的设定信息
     */
    List<PlanAppRelation> selectByPlanIdPlatform(@Param("planId") long planId,
                                                 @Param("platform") int platform);
}