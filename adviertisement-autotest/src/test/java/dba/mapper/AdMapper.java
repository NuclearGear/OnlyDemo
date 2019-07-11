package dba.mapper;

import dba.model.Ad;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdMapper {

    /**
     * 根据id值返回AD的基本信息
     * @param id
     * @return
     */
    Ad selectById(Long id);


    /**
     * 根据名称返回AD的基本信息
     * @param name
     * @return
     */
    Ad selectByName(String name);

    /**
     * 根据ownerId查询所有其下广告主信息
     *
     * @param ownerId
     * @return
     */
    List<Ad> selectByOwnerId(int ownerId);


    /**
     * 根据名称获取所有相关的广告主信息
     */
    List<Ad> selectLikeAdName(String adName);

    /**
     * 获取管理员下广告计划信息总量
     *
     * @return
     */
    int getAdsCount(Map<String, Object> map);

    /**
     * 根据条件获取广告主信息总量
     *
     * @return
     */
    int getAdsCount(@Param("adType") int adType,
                    @Param("advertiserId") int advertiserId,
                    @Param("name") String name);


    /**
     * 根据页面要求返回广告计划列表，管理员账号的返回
     *
     * @return
     */
    List<Ad> getAdWithPage(@Param("size") int size,
                           @Param("crrentPage") int crrentPage,
                           @Param("adType") int adType,
                           @Param("advertiserId") int advertiserId,
                           @Param("name") String name);

    /**
     * 根据页面要求返回广告主列表，根据运营域账号的返回
     *
     * @return
     */
    List<Ad> getAdsWithPageByUid(Map<String, Object> map);


    /**
     * 根据名称删除相应的记录
     *
     * @param name
     * @return
     */
    int deleteLikeName(String name);


    /**
     * 根据名称删除相应空的ad的记录
     *
     * @param name
     * @return
     */
    int deleteNullAdLikeName(String name);

    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteAdNotInOwner();


}