package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.pojo.Course;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface CourseMapper {

    int insertCourse(Course course);

    Course selectCourseByNameStartWeekEndWeekWeekDaySchooltime(Course course);

    List<Course> selectCoursesByClassroomId(Integer classroomId);

    List<Course> selectByClassroomCourses(@Param("classroomCourses") List<ClassroomCourse> classroomCourses);

    Course selectById(Integer id);

    int updateCourse(Course newcourse);

    List<Course> selectCoursesBySpecificNameAndPage(@Param("specificName") String specificName, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    int selectTotalCoursesWithSpecificName(String specificName);

    // Unused methods:
    List<Course> selectBySpecificName(String name);

    List<Course> selectCoursesByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int selectTotalCourses();
}
