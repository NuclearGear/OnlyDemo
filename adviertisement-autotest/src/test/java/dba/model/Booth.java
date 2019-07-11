package dba.model;

import java.util.Date;

public class Booth {
    private Long id;

    private String boothCode;

    private String boothName;

    private String size;

    private Long frameCount;

    private Long boothType;

    private Boolean closeable;

    private Long interactType;

    private Long positionId;

    private String previewUrl;

    private Long xaxis;

    private Long yaxis;

    private Date gmtCreate;

    private Date gmtModify;

    private Byte bizType;

    private Byte supportDsp;

    private String largeSize;

    private Long count;

    private Byte style;

    private Byte resourceType;

    private Byte deliveryStrategy;

    private Long version;

    private Long status;

    private String comment;

    private Byte adPlatform;

    private String filledFrames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoothCode() {
        return boothCode;
    }

    public void setBoothCode(String boothCode) {
        this.boothCode = boothCode == null ? null : boothCode.trim();
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName == null ? null : boothName.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Long getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(Long frameCount) {
        this.frameCount = frameCount;
    }

    public Long getBoothType() {
        return boothType;
    }

    public void setBoothType(Long boothType) {
        this.boothType = boothType;
    }

    public Boolean getCloseable() {
        return closeable;
    }

    public void setCloseable(Boolean closeable) {
        this.closeable = closeable;
    }

    public Long getInteractType() {
        return interactType;
    }

    public void setInteractType(Long interactType) {
        this.interactType = interactType;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl == null ? null : previewUrl.trim();
    }

    public Long getXaxis() {
        return xaxis;
    }

    public void setXaxis(Long xaxis) {
        this.xaxis = xaxis;
    }

    public Long getYaxis() {
        return yaxis;
    }

    public void setYaxis(Long yaxis) {
        this.yaxis = yaxis;
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

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public Byte getSupportDsp() {
        return supportDsp;
    }

    public void setSupportDsp(Byte supportDsp) {
        this.supportDsp = supportDsp;
    }

    public void setLargeSize(String largeSize) {
        this.largeSize = boothCode == null ? null : largeSize.trim();
    }

    public String getLargeSize() {
        return largeSize;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Byte getStyle() {
        return style;
    }

    public void setStyle(Byte style) {
        this.style = style;
    }

    public Byte getResourceType() {
        return resourceType;
    }

    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
    }

    public Byte getDeliveryStrategy() {
        return deliveryStrategy;
    }

    public void setDeliveryStrategy(Byte deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Byte getAdPlatform() {
        return adPlatform;
    }

    public void setAdPlatform(Byte adPlatform) {
        this.adPlatform = adPlatform;
    }

    public String getFilledFrames() {
        return filledFrames;
    }

    public void setFilledFrames(String filledFrames) {
        this.filledFrames = filledFrames == null ? null : filledFrames.trim();
    }
}