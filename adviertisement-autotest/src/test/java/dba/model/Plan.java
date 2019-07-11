package dba.model;

import java.util.Date;

public class Plan {
    private Long id;

    private Long duration;

    private Long adId;

    private Long materialId;

    private Long status;

    private Long state;

    private Long frameNum;

    private Long startTime;

    private Long endTime;

    private Date gmtCreate;

    private Date gmtModify;

    private String planIdWrapper;

    private Integer weight;

    private Byte sellMode;

    private Double sellPrice;

    private Double budget;

    private Byte deliveryAmountFlag;

    private String name;

    private Byte commerceAdType;

    private Byte gender;

    private Byte matchingMode;

    private Byte targettingFlag;

    private Long ownerId;

    private Long version;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    private long groupId;
    private Byte type;

    private Long consumption;

    private Integer uniformDeliveryFlag;

    private Integer platformFlag;

    private Byte isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(Long frameNum) {
        this.frameNum = frameNum;
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

    public String getPlanIdWrapper() {
        return planIdWrapper;
    }

    public void setPlanIdWrapper(String planIdWrapper) {
        this.planIdWrapper = planIdWrapper == null ? null : planIdWrapper.trim();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Byte getSellMode() {
        return sellMode;
    }

    public void setSellMode(Byte sellMode) {
        this.sellMode = sellMode;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Byte getDeliveryAmountFlag() {
        return deliveryAmountFlag;
    }

    public void setDeliveryAmountFlag(Byte deliveryAmountFlag) {
        this.deliveryAmountFlag = deliveryAmountFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getCommerceAdType() {
        return commerceAdType;
    }

    public void setCommerceAdType(Byte commerceAdType) {
        this.commerceAdType = commerceAdType;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getMatchingMode() {
        return matchingMode;
    }

    public void setMatchingMode(Byte matchingMode) {
        this.matchingMode = matchingMode;
    }

    public Byte getTargettingFlag() {
        return targettingFlag;
    }

    public void setTargettingFlag(Byte targettingFlag) {
        this.targettingFlag = targettingFlag;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getConsumption() {
        return consumption;
    }

    public void setConsumption(Long consumption) {
        this.consumption = consumption;
    }

    public Integer getUniformDeliveryFlag() {
        return uniformDeliveryFlag;
    }

    public void setUniformDeliveryFlag(Integer uniformDeliveryFlag) {
        this.uniformDeliveryFlag = uniformDeliveryFlag;
    }

    public Integer getPlatformFlag() {
        return platformFlag;
    }

    public void setPlatformFlag(Integer platformFlag) {
        this.platformFlag = platformFlag;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}