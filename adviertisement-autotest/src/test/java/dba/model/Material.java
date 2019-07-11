package dba.model;

import java.util.Date;

public class Material {
    private Long id;

    private String name;

    private Long contentType;

    private String contentKey;

    private String contentSize;

    private String jumpType;

    private String jumpUrl;

    private Date gmtCreate;

    private Date gmtModify;

    private String title;

    private String subTitle;

    private String contentKey2;

    private String contentKey3;

    private Boolean style;

    private String contentSize2;

    private String contentSize3;

    private Boolean openMode;

    private String currentPrice;

    private String originalPrice;

    private Integer showTitle;

    private Long templateId;

    private String input;

    private String link;

    private Byte currencyType;

    private Byte showSign;

    private Long materialTemplateId;

    private Byte isClick;

    private Byte showStyle;

    private String storeContent;

    private Byte storeType;

    private Long skipOffset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getContentType() {
        return contentType;
    }

    public void setContentType(Long contentType) {
        this.contentType = contentType;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey == null ? null : contentKey.trim();
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize == null ? null : contentSize.trim();
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType == null ? null : jumpType.trim();
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl == null ? null : jumpUrl.trim();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public String getContentKey2() {
        return contentKey2;
    }

    public void setContentKey2(String contentKey2) {
        this.contentKey2 = contentKey2 == null ? null : contentKey2.trim();
    }

    public String getContentKey3() {
        return contentKey3;
    }

    public void setContentKey3(String contentKey3) {
        this.contentKey3 = contentKey3 == null ? null : contentKey3.trim();
    }

    public Boolean getStyle() {
        return style;
    }

    public void setStyle(Boolean style) {
        this.style = style;
    }

    public String getContentSize2() {
        return contentSize2;
    }

    public void setContentSize2(String contentSize2) {
        this.contentSize2 = contentSize2 == null ? null : contentSize2.trim();
    }

    public String getContentSize3() {
        return contentSize3;
    }

    public void setContentSize3(String contentSize3) {
        this.contentSize3 = contentSize3 == null ? null : contentSize3.trim();
    }

    public Boolean getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Boolean openMode) {
        this.openMode = openMode;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice == null ? null : currentPrice.trim();
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice == null ? null : originalPrice.trim();
    }

    public Integer getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Integer showTitle) {
        this.showTitle = showTitle;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input == null ? null : input.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public Byte getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Byte currencyType) {
        this.currencyType = currencyType;
    }

    public Byte getShowSign() {
        return showSign;
    }

    public void setShowSign(Byte showSign) {
        this.showSign = showSign;
    }

    public Long getMaterialTemplateId() {
        return materialTemplateId;
    }

    public void setMaterialTemplateId(Long materialTemplateId) {
        this.materialTemplateId = materialTemplateId;
    }

    public Byte getIsClick() {
        return isClick;
    }

    public void setIsClick(Byte isClick) {
        this.isClick = isClick;
    }

    public Byte getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(Byte showStyle) {
        this.showStyle = showStyle;
    }


    public String getStoreContent() {
        return storeContent;
    }

    public void setStoreContent(String storeContent) {
        this.storeContent = storeContent == null ? null : storeContent.trim();
    }

    public Byte getStoreType() {
        return storeType;
    }

    public void setStoreType(Byte storeType) {
        this.storeType = storeType;
    }

    public Long getSkipOffset() {
        return skipOffset;
    }

    public void setSkipOffset(Long skipOffset) {
        this.skipOffset = skipOffset;
    }
}