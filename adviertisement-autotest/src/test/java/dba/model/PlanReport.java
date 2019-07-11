package dba.model;

import java.util.Date;

public class PlanReport {
    private Long id;

    private Long adId;

    private Long adType;

    private String adName;

    private String configLocation;

    private String actualLocation;

    private String statDate;

    private Long click;

    private Long pv;

    private String ctr;

    private Date gmtCreate;

    private Date gmtModify;

    private Long planId;

    private Long displayCount;

    private Long validDisplayCount;

    private Long loadingPagePv;

    private Long loadingPageUv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getAdType() {
        return adType;
    }

    public void setAdType(Long adType) {
        this.adType = adType;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName == null ? null : adName.trim();
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation == null ? null : configLocation.trim();
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(String actualLocation) {
        this.actualLocation = actualLocation == null ? null : actualLocation.trim();
    }

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate == null ? null : statDate.trim();
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr == null ? null : ctr.trim();
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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Long displayCount) {
        this.displayCount = displayCount;
    }

    public Long getValidDisplayCount() {
        return validDisplayCount;
    }

    public void setValidDisplayCount(Long validDisplayCount) {
        this.validDisplayCount = validDisplayCount;
    }

    public Long getLoadingPagePv() {
        return loadingPagePv;
    }

    public void setLoadingPagePv(Long loadingPagePv) {
        this.loadingPagePv = loadingPagePv;
    }

    public Long getLoadingPageUv() {
        return loadingPageUv;
    }

    public void setLoadingPageUv(Long loadingPageUv) {
        this.loadingPageUv = loadingPageUv;
    }
}