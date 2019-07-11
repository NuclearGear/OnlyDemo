package dba.model;

import java.util.Date;

public class Channel {
    private Integer id;

    private Integer type;

    private String name;

    private String operator;

    private String idWrapper;

    private Integer flowFlag;

    private Date gmtCreated;

    private Date gmtModify;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getIdWrapper() {
        return idWrapper;
    }

    public void setIdWrapper(String idWrapper) {
        this.idWrapper = idWrapper == null ? null : idWrapper.trim();
    }

    public Integer getFlowFlag() {
        return flowFlag;
    }

    public void setFlowFlag(Integer flowFlag) {
        this.flowFlag = flowFlag;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}