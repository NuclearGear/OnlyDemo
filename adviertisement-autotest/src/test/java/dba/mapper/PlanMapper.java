package dba.mapper;

import dba.model.Ad;
import dba.model.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PlanMapper {


    /**
     * 根据planId，返回投放计划的详细信息
     * @param id
     * @return
     */
    Plan selectById(Long id);


    /**
     * 根据name,返回投放的详细信息
     */
    Plan selectByName(String name);

    /**
     * 根据name,返回投放的详细信息
     */
    List<Plan>  selectLikeName(String name);

    /**
     * 根据adId，返回其下的plan信息
     */
    List<Plan> selectByAdId(Long adId);


    /**
     * 根据boothCode和状态值，返回其下的所有plan信息
     */
    List<Plan> selectByBoothCodeAndStatus(@Param("boothCode") String boothCode,
                                          @Param("status") int status);


    /**
     * 根据boothCode和frameNum，返回其下的所有plan信息
     */
    List<Plan> selectByBoothCodeAndFrameNum(@Param("boothCode") String boothCode,
                                            @Param("frameNum") int frameNum);



    /**
     * 根据状态status值，返回符合状态的plans
     */
    List<Plan> selectByStatus(int status);



    /**
     * 获取管理员下广告计划信息总量
     *
     * @return
     */
    int getPlansCount(Map<String, Object> map);


    /**
     * 根据页面要求返回广告计划列表，管理员账号的返回
     *
     * @return
     */
    List<Ad> getPlansWithPage(Map<String, Object> map);


    /**
     * 根据planId和status的值，更改投放状态
     */
    int updatePlanStatusById(@Param("id") long id,
                             @Param("status") int status);




    /**
     * 根据名称删除相应的记录
     *
     * @param name
     * @return
     */
    int deleteLikeName(String name);

    /**
     * 删除关系表中无意义的记录,无adId的情况
     * @return
     */
     int deletePlanNotInAD();

    /**
     * 删除关系表中无意义的记录,无boothCode的情况
     * @return
     */
     int deletePlanNotInPlanBoothRelation();



}