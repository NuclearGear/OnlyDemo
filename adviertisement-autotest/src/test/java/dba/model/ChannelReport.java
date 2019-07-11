package dba.model;

import java.util.Date;

public class ChannelReport {
    private Long id;

    private String statDate;

    private String channelName;

    private String boothName;

    private Integer displayCount;

    private Integer displayUserCount;

    private Integer validDisplayCount;

    private Integer clickCount;

    private Integer clickUserCount;

    private String ctr;

    private Double cpmPrice;

    private Double cpcPrice;

    private Double putPrice;

    private Integer planCount;

    private Integer type;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate == null ? null : statDate.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName == null ? null : boothName.trim();
    }

    public Integer getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Integer displayCount) {
        this.displayCount = displayCount;
    }

    public Integer getDisplayUserCount() {
        return displayUserCount;
    }

    public void setDisplayUserCount(Integer displayUserCount) {
        this.displayUserCount = displayUserCount;
    }

    public Integer getValidDisplayCount() {
        return validDisplayCount;
    }

    public void setValidDisplayCount(Integer validDisplayCount) {
        this.validDisplayCount = validDisplayCount;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Integer getClickUserCount() {
        return clickUserCount;
    }

    public void setClickUserCount(Integer clickUserCount) {
        this.clickUserCount = clickUserCount;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr == null ? null : ctr.trim();
    }

    public Double getCpmPrice() {
        return cpmPrice;
    }

    public void setCpmPrice(Double cpmPrice) {
        this.cpmPrice = cpmPrice;
    }

    public Double getCpcPrice() {
        return cpcPrice;
    }

    public void setCpcPrice(Double cpcPrice) {
        this.cpcPrice = cpcPrice;
    }

    public Double getPutPrice() {
        return putPrice;
    }

    public void setPutPrice(Double putPrice) {
        this.putPrice = putPrice;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}