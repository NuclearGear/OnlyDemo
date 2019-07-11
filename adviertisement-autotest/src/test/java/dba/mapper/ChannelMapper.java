package dba.mapper;

import dba.model.Channel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChannelMapper {

    /**
     * 根据Id值获取相应的Channel信息
     *
     * @param id
     * @return
     */
    Channel selectById(Integer id);


    /**
     *根据名称删除记录
     * @return
     */
    int deleteLikeName(String name);


    /**
     * 根据运营账号获取广告主信息总量
     *
     * @param name
     * @return
     */
    int getChannelsCount(@Param("name") String name,
                         @Param("id") int id);


    /**
     * 根据页面要求返回广告主列表，管理员账号的返回
     *
     * @param size       数量
     * @param crrentPage 起始数
     * @return
     */
    List<Channel> getChannelsWithPage(@Param("size") int size,
                                      @Param("crrentPage") int crrentPage,
                                      @Param("name") String name,
                                      @Param("id") int type);
}