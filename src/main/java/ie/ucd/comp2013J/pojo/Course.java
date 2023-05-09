package ie.ucd.comp2013J.pojo;

public class Course {
    private Integer id;
    private String name;
    private Integer startWeek;//第几周开始
    private Integer endWeek; //第几周结束
    private Integer weekDay; //星期几的课
    private Integer schooltime; //教学时段
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

