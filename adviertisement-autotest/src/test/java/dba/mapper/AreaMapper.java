package dba.mapper;

import dba.model.Area;

import java.util.List;

public interface AreaMapper {

    /**
     * 获取所有地域信息
     * @return
     */
    List<Area> selectAllArea();

}