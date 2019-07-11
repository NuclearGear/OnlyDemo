package dba.mapper;

import dba.model.AdvertiserIncome;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertiserIncomeMapper {

    /**
     * 根据id值获取数据库中的信息
     * @param id
     * @return
     */
    AdvertiserIncome selectById(Long id);


    /**
     * 根基名称获取数据库的信息
     */
    AdvertiserIncome selectByName(String name);



    /**
     * 根据advertiserId获取含有明细的未删除列表数量
     */
    int getAdvertiserIncomeCount(Long advertiserId);


    /**
     * 根据页面要求返回收入明细的列表
     */
    List<AdvertiserIncome> getIncomesWithPageByAdvId(@Param("size") int size,
                                                     @Param("crrentPage") int crrentPage,
                                                     @Param("advertiserId") Long advertiserId);



    /**
     * 根据名称或advertiserId删除相应的记录
     * @return
     */
    int deleteLikeNameOrByAdvId(@Param("advertiserId") int advertiserId,
                                @Param("name") String name);



}