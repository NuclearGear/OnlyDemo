package dba.model;

public class TargettingAge {
    private Long id;

    private Long planId;

    private Byte startAge;

    private Byte endAge;

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

    public Byte getStartAge() {
        return startAge;
    }

    public void setStartAge(Byte startAge) {
        this.startAge = startAge;
    }

    public Byte getEndAge() {
        return endAge;
    }

    public void setEndAge(Byte endAge) {
        this.endAge = endAge;
    }

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }
}