package dba.mapper;

public interface TargettingAgeMapper {


    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteTargetingAgeNotInPlan();


}