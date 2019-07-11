package dba.mapper;

import dba.model.Booth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoothMapper {

    /**
     * 根据展位Code获取相应的广告展位信息
     *
     * @return
     */
    Booth selectByBoothCode(String boothCode);


    /**
     * 根据likeboothCode获取展位列表
     */
    List<Booth> selectLikeBoothCode(String boothCode);

    /**
     * 根据名称获取展位列表,只返回status=1有效数据
     */
    List<Booth> selectLikeBoothName(String name);


    /**
     * 根据positionId,只返回status=1有效数据
     */
    List<Booth> selectByPositionId(Long positionId);

    /**
     * 获取管理员下广告计划信息总量
     *
     * @return
     */
    int getBoothsCount();

    /**
     * 获取符合条件的广告计划信息总量
     */
    int getBoothsWithPageCount(@Param("boothName") String boothName,
                               @Param("resourceType") int resourceType,
                               @Param("position") String position);

    /**
     * 根据页面要求返回广告主列表
     *
     * @param size       数量
     * @param crrentPage 起始数
     * @return
     */
    List<Booth> getBoothWithPage(@Param("size") int size,
                                 @Param("crrentPage") int crrentPage,
                                 @Param("boothName") String boothName,
                                 @Param("resourceType") int resourceType,
                                 @Param("position") String position);


    /**
     * 根据boothCode删除相应的记录
     *
     * @param boothCode
     * @return
     */
    int deleteLikeBoothCode(String boothCode);


    /**
     * 根据名称删除相应的记录
     *
     * @param name
     * @return
     */
    int deleteLikeName(String name);

}