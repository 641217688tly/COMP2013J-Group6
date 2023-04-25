package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.ClassroomCourse;

import java.util.List;

public interface ClassroomCourseMapper {
    int insert(ClassroomCourse classroomCourse);

    int update(ClassroomCourse classroomCourse);

    int delete(Integer id);

    ClassroomCourse selectById(Integer id);

    List<ClassroomCourse> selectAll();
}
