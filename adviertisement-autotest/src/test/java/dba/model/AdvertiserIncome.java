package dba.model;

import java.util.Date;

public class AdvertiserIncome {
    private Long id;

    private Long advertiserId;

    private String activityName;

    private String business;

    private Date activityPeriodStart;

    private Date activityPeriodEnd;

    private Float income;

    private String operator;

    private String activityComment;

    private Date updateTime;

    private Date createdTime;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Long advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business == null ? null : business.trim();
    }

    public Date getActivityPeriodStart() {
        return activityPeriodStart;
    }

    public void setActivityPeriodStart(Date activityPeriodStart) {
        this.activityPeriodStart = activityPeriodStart;
    }

    public Date getActivityPeriodEnd() {
        return activityPeriodEnd;
    }

    public void setActivityPeriodEnd(Date activityPeriodEnd) {
        this.activityPeriodEnd = activityPeriodEnd;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getActivityComment() {
        return activityComment;
    }

    public void setActivityComment(String activityComment) {
        this.activityComment = activityComment == null ? null : activityComment.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}