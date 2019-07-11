package dba.mapper;

import dba.model.User;

import java.util.List;

public interface UserMapper {

    /**
     * 根据uid获取用户权限
     * @param uid 用户的域账号名称
     * @return
     */
    User selectByUid(String uid);

    /**
     * 根据权限搜到所有用户
     * @param type //用户的权限，1：运营，2：管理员
     * @return
     */
    List<User> selectByRightType(int type);


    /**
     * 根据权限搜到uid，重复的合并
     * @param type //用户的权限，1：运营，2：管理员
     * @return
     */
    List<String> getDisUidByRightType(int type);


    /**
     * 根据名称删除相应的记录
     * @param uid
     * @return
     */
    int deleteLikeUid(String uid);



}