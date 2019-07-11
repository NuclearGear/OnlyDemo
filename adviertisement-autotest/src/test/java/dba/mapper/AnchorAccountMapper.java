package dba.mapper;

import dba.model.AnchorAccount;

import java.util.List;

public interface AnchorAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AnchorAccount record);

    int insertSelective(AnchorAccount record);

    AnchorAccount selectByPrimaryKey(Long id);

    List<AnchorAccount> selectAllAnchor();

    int updateByPrimaryKeySelective(AnchorAccount record);

    int updateByPrimaryKey(AnchorAccount record);
}