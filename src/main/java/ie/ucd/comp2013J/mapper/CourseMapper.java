package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Course;

import java.util.List;

public interface CourseMapper {
    int insert(Course course);

    int update(Course course);

    int delete(Integer id);

    Course selectById(Integer id);

    List<Course> selectAll();
}