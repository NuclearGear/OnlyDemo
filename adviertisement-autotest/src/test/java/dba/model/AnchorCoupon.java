package dba.model;

import java.util.Date;

public class AnchorCoupon {
    private Long id;

    private Long roomId;

    private Long couponId;

    private String couponMd5;

    private Date gmtCreated;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponMd5() {
        return couponMd5;
    }

    public void setCouponMd5(String couponMd5) {
        this.couponMd5 = couponMd5 == null ? null : couponMd5.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}