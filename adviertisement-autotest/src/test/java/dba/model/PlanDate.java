package dba.model;

import java.util.Date;

public class PlanDate {
    private Long id;

    private Long planId;

    private Date startTime;

    private Date endTime;

    private Byte weekDeliveryMode;    //表示时间标识。0：全部  1：工作日  2：周末

    private Byte status;

    private Date createdTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getWeekDeliveryMode() {
        return weekDeliveryMode;
    }

    public void setWeekDeliveryMode(Byte weekDeliveryMode) {
        this.weekDeliveryMode = weekDeliveryMode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}