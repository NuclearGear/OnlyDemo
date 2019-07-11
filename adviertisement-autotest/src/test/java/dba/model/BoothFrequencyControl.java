package dba.model;

public class BoothFrequencyControl {
    private Long id;

    private String boothCode;

    private Integer minutes;

    private Integer times;

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

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}