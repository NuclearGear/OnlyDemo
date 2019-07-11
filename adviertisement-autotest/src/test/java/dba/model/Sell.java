package dba.model;

import java.util.Date;

public class Sell {
    private Long id;

    private Long channelId;

    private Long throughput;

    private Byte sellType;

    private Byte cashType;

    private Long price;

    private Long iosSlotId;

    private Byte slotType;

    private Byte creativeType;

    private Date gmtCreate;

    private Date gmtModify;

    private Long androidSlotId;

    private String iosSlot;

    private String androidSlot;

    private Byte frameNum;

    private Byte status;

    private Integer androidPrice;

    private Integer iosPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getThroughput() {
        return throughput;
    }

    public void setThroughput(Long throughput) {
        this.throughput = throughput;
    }

    public Byte getSellType() {
        return sellType;
    }

    public void setSellType(Byte sellType) {
        this.sellType = sellType;
    }

    public Byte getCashType() {
        return cashType;
    }

    public void setCashType(Byte cashType) {
        this.cashType = cashType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getIosSlotId() {
        return iosSlotId;
    }

    public void setIosSlotId(Long iosSlotId) {
        this.iosSlotId = iosSlotId;
    }

    public Byte getSlotType() {
        return slotType;
    }

    public void setSlotType(Byte slotType) {
        this.slotType = slotType;
    }

    public Byte getCreativeType() {
        return creativeType;
    }

    public void setCreativeType(Byte creativeType) {
        this.creativeType = creativeType;
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

    public Long getAndroidSlotId() {
        return androidSlotId;
    }

    public void setAndroidSlotId(Long androidSlotId) {
        this.androidSlotId = androidSlotId;
    }

    public String getIosSlot() {
        return iosSlot;
    }

    public void setIosSlot(String iosSlot) {
        this.iosSlot = iosSlot == null ? null : iosSlot.trim();
    }

    public String getAndroidSlot() {
        return androidSlot;
    }

    public void setAndroidSlot(String androidSlot) {
        this.androidSlot = androidSlot == null ? null : androidSlot.trim();
    }

    public Byte getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(Byte frameNum) {
        this.frameNum = frameNum;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getAndroidPrice() {
        return androidPrice;
    }

    public void setAndroidPrice(Integer androidPrice) {
        this.androidPrice = androidPrice;
    }

    public Integer getIosPrice() {
        return iosPrice;
    }

    public void setIosPrice(Integer iosPrice) {
        this.iosPrice = iosPrice;
    }
}