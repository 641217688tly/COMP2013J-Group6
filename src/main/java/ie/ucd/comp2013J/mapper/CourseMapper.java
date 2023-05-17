package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.pojo.Course;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface CourseMapper {

    List<Course> selectCoursesByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int selectTotalCourses();

    int insertCourse(Course course);

    Course selectCourseByNameStartWeekEndWeekWeekDaySchooltime(Course course);

    List<Course> selectCoursesByClassroomId(Integer classroomId);

    List<Course> selectByClassroomCourses(@Param("classroomCourses") List<ClassroomCourse> classroomCourses);

    Course selectById(Integer id);
}
