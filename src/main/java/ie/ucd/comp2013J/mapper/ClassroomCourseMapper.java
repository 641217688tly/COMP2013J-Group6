package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.ClassroomCourse;

import java.util.List;

public interface ClassroomCourseMapper {
    void insertClassroomCourse(ClassroomCourse classroomCourse);

    void deleteClassroomCourseById(Integer id);

    void updateClassroomCourse(ClassroomCourse classroomCourse);

    ClassroomCourse selectByCourseIDAndClassroomID(ClassroomCourse classroomCourse);

    ClassroomCourse selectClassroomCourseById(Integer id);

    List<ClassroomCourse> selectAllClassroomCourses();
}
