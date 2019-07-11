package dba.model;

import java.util.Date;

public class BoothSellRelation {
    private Long id;

    private String boothCode;

    private Long sellId;

    private Date gmtCreate;

    private Date gmtModify;

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

    public Long getSellId() {
        return sellId;
    }

    public void setSellId(Long sellId) {
        this.sellId = sellId;
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