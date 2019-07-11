package dba.mapper;

import dba.model.Slot;

import java.util.List;

public interface SlotMapper {


    /**
     * 根据sell_id返回所有DSP信息
     */
    List<Slot> selectBySellId(Long sellId);


}