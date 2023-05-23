package ie.ucd.comp2013J.pojo;

public class Classroom {
    private Integer id;
    private Integer number;
    private Integer floor;
    private String capacity;
    private Boolean status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getFloor() {
        return floor;
    }

    public String getCapacity() {
        return capacity;
    }

    public Boolean getStatus() {
        return status;
    }
}
