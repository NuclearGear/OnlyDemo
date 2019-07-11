package dba.model;

import java.util.Date;

public class PlanItem {
    private Long id;

    private Long planId;

    private Long spuid;

    private Long sellerId;

    private String sellerName;

    private Long categoryId;

    private String categoryName;

    private String itemName;

    private Integer brokerageRatio;

    private Long price;

    private Byte status;

    private Integer isChoice;

    private Date gmtCreated;

    private Date gmtModified;

    private Long version;

    private Long profitShare;

    private Integer inventory;

    private Integer profitId;

    private Byte flag;

    private String picture;

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

    public Long getSpuid() {
        return spuid;
    }

    public void setSpuid(Long spuid) {
        this.spuid = spuid;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Integer getBrokerageRatio() {
        return brokerageRatio;
    }

    public void setBrokerageRatio(Integer brokerageRatio) {
        this.brokerageRatio = brokerageRatio;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getIsChoice() {
        return isChoice;
    }

    public void setIsChoice(Integer isChoice) {
        this.isChoice = isChoice;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getProfitShare() {
        return profitShare;
    }

    public void setProfitShare(Long profitShare) {
        this.profitShare = profitShare;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getProfitId() {
        return profitId;
    }

    public void setProfitId(Integer profitId) {
        this.profitId = profitId;
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }
}