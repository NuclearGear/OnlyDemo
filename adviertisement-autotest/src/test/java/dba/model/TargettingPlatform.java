package dba.model;

public class TargettingPlatform {
    private Long id;

    private Long planId;

    private Integer platform;

    private Byte relation;

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

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }
}