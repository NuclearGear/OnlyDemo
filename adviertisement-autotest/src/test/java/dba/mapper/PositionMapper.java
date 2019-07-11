package dba.mapper;

import dba.model.Position;

import java.util.List;

public interface PositionMapper {

    /**
     * 根据名称id返回模块的信息
     * @param id
     * @return
     */
    Position selectById(Long id);

    /**
     * 根据名称返回模块的信息
     * @param Name
     * @return
     */
    Position selectByName(String Name);


    /**
     * 根据名称删除相应的记录
     * @param name
     * @return
     */
    int deleteLikeName(String name);


    /**
     * 返回所有的position
     */
    List<Position> getAllPosition();

}