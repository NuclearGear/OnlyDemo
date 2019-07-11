package dba.mapper;

import dba.model.ThirdMonitor;

import java.util.List;

public interface ThirdMonitorMapper {


    ThirdMonitor selectById(Long id);

    /**
     * 根据materialId的信息，返回third_monitor的列表信息
     */
    List<ThirdMonitor> selectByMaterialId(Long materialId);



    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteThirdMonitorNotInMaterial();

}