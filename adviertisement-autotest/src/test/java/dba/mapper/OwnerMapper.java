package dba.mapper;

import dba.model.Owner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OwnerMapper {


    Owner selectById(int id);

    /**
     * 根据名称查询广告主信息
     * @param name 广告主的名称
     * @return
     */
    Owner selectByName(String name);


    /**
     * 根据名称查询广告主信息,返回符合规则的owner
     * @param name 广告主的名称
     * @return
     */
    List<Owner> selectLikeName(String name);



    /**
     * 获取管理员下广告主信息总量
     * @param name
     * @param type
     * @return
     */
    int getOwnersCount(@Param("name") String name,
                       @Param("type") int type);

    /**
     * 根据运营账号获取广告主信息总量
     * @param name
     * @param type
     * @param uid
     * @return
     */
    int getOwnersCountByUid(@Param("name") String name,
                            @Param("type") int type,
                            @Param("uid") String uid);

    /**
     * 根据页面要求返回广告主列表，管理员账号的返回
     * @param size 数量
     * @param crrentPage 起始数
     * @return
     */
    List<Owner> getOwnersWithPage(@Param("size") int size,
                                  @Param("crrentPage") int crrentPage,
                                  @Param("name") String name,
                                  @Param("type") int type);

    /**
     * 根据页面要求返回广告主列表，根据运营域账号的返回
     * @param size
     * @param crrentPage
     * @param name
     * @param type
     * @param uid
     * @return
     */
    List<Owner> getOwnersWithPageByUid(@Param("size") int size,
                                       @Param("crrentPage") int crrentPage,
                                       @Param("name") String name,
                                       @Param("type") int type,
                                       @Param("uid") String uid);

    /**
     * 根据OwnerId所属的uid列表
     * @param OwnerId
     * @return
     */
    List<String> getUidWithOwnerId(int OwnerId);




    /**
     * 根据名称删除相应的记录
     * @param name
     * @return
     */
    int deleteLikeName(String name);

    /**
     * 删除关系表中无意义的记录
     * @return
     */
    int deleteUserAdOwnerRelationNotInOwner();

}