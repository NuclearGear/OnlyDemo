package dba.model;

import java.util.Date;

public class BoothTemplateRelation {
    private Long id;

    private String boothCode;

    private Long templateId;

    private Date createdTime;

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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}