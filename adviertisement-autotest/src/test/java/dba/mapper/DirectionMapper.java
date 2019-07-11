package dba.mapper;

import dba.model.Direction;

import java.util.List;

public interface DirectionMapper {



    Direction selectByPrimaryKey(Long id);

    /**
     * 根据广告Id搜索所有的定向
     * @return
     */
    List<Direction> selectByAdId(Long adId);

    /**
     * 删除关系表中无意义的记录
     * @return
     */
    int deleteirectionNotInAD();


}