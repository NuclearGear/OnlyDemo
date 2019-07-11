package dba.model;

import java.util.Date;

public class Ad {
    private Long id;

    private String adName;

    private Long adType;

    private Long ownerId;

    private Long status;

    private Long startTime;

    private Long endTime;

    private Long duration;

    private Date gmtCreate;

    private Date gmtModify;

    private String adIdWrapper;

    private Integer putType;

    private String ownerName;

    private String operator;

    private Byte system;

    private Byte promotion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName == null ? null : adName.trim();
    }

    public Long getAdType() {
        return adType;
    }

    public void setAdType(Long adType) {
        this.adType = adType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getAdIdWrapper() {
        return adIdWrapper;
    }

    public void setAdIdWrapper(String adIdWrapper) {
        this.adIdWrapper = adIdWrapper == null ? null : adIdWrapper.trim();
    }

    public Integer getPutType() {
        return putType;
    }

    public void setPutType(Integer putType) {
        this.putType = putType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Byte getSystem() {
        return system;
    }

    public void setSystem(Byte system) {
        this.system = system;
    }

    public Byte getPromotion() {
        return promotion;
    }

    public void setPromotion(Byte promotion) {
        this.promotion = promotion;
    }
}