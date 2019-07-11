package dba.mapper;

import dba.model.AnchorCoupon;

import java.util.List;

public interface AnchorCouponMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AnchorCoupon record);

    int insertSelective(AnchorCoupon record);

    AnchorCoupon selectByPrimaryKey(Long id);

    List<AnchorCoupon> selectByRoomId(Long roomId);

    int updateByPrimaryKeySelective(AnchorCoupon record);

    int updateByPrimaryKey(AnchorCoupon record);
}