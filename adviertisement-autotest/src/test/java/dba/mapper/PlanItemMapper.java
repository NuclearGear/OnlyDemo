package dba.mapper;

import dba.model.PlanItem;

import java.util.List;

public interface PlanItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlanItem record);

    int insertSelective(PlanItem record);

    PlanItem selectByPrimaryKey(Long id);

    List<PlanItem> selectByAnchorId(Long anchorId);

    int updateByPrimaryKeySelective(PlanItem record);

    int updateByPrimaryKey(PlanItem record);
}
