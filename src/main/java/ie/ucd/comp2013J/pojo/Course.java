package ie.ucd.comp2013J.pojo;

public class Course {
    private Integer id;
    private String name;
    private Integer startWeek;//Start of the week
    private Integer endWeek; //End of the week
    private Integer weekDay; //Day of the week for classes
    private Integer schooltime; //Teaching time period
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getSchooltime() {
        return schooltime;
    }

    public void setSchooltime(Integer schooltime) {
        this.schooltime = schooltime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

