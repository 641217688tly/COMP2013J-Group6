package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.pojo.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomCourseMapper {
    List<ClassroomCourse> selectClassroomCoursesByCourseIds(@Param("courseList") List<Course> courseList);

    void insertClassroomCourse(ClassroomCourse classroomCourse);

    ClassroomCourse selectByCourseIDAndClassroomID(ClassroomCourse classroomCourse);

    List<ClassroomCourse> selectByClassroomId(Integer classroomId);

}
