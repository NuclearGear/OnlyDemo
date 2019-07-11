package dba.model;

import java.util.Date;

public class MaterialTemplate {
    private Long id;

    private String name;

    private Byte type;

    private Byte status;

    private Byte contentType;

    private Date createdTime;

    private Date updateTime;

    private Byte isClick;

    private Byte materialStyle;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getContentType() {
        return contentType;
    }

    public void setContentType(Byte contentType) {
        this.contentType = contentType;
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

    public Byte getIsClick() {
        return isClick;
    }

    public void setIsClick(Byte isClick) {
        this.isClick = isClick;
    }

    public Byte getMaterialStyle() {
        return materialStyle;
    }

    public void setMaterialStyle(Byte materialStyle) {
        this.materialStyle = materialStyle;
    }
}