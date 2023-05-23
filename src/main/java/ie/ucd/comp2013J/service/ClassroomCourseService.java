package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.ClassroomCourseMapper;
import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ClassroomCourseService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public boolean insertExcelFile(ArrayList<Course> courses, ArrayList<Classroom> classrooms) {
        if (courses.size() != classrooms.size()) {
            throw new IllegalArgumentException("There are errors in parsing the Excel timetable file.");
        }
        try {
            for (int i = 0; i < courses.size(); i++) {
                insertClassroomCourse(courses.get(i), classrooms.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ClassroomCourse insertClassroomCourse(Course course, Classroom classroom) { //The insert method provides insertExcelFile calls
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);

            ClassroomCourse classroomCourse = new ClassroomCourse();
            classroomCourse.setCourseId(course.getId());
            classroomCourse.setClassroomId(classroom.getId());

            ClassroomCourse existingClassroomCourse = mapper.selectByCourseIDAndClassroomID(classroomCourse);
            if (existingClassroomCourse != null) { // The database already exists for the course
                return existingClassroomCourse;
            } else { // The course does not yet exist
                mapper.insertClassroomCourse(classroomCourse);
                sqlSession.commit();
                return classroomCourse;
            }
        }
    }

    public boolean insertSingleClassroomCourse(Course course, Classroom classroom) { // The insert method is called when the information for a single class schedule is inserted
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);

            ClassroomCourse classroomCourse = new ClassroomCourse();
            if (course == null || classroom == null) { // course or classroom insertion failed
                return false;
            }
            classroomCourse.setCourseId(course.getId());
            classroomCourse.setClassroomId(classroom.getId());

            ClassroomCourse existingClassroomCourse = mapper.selectByCourseIDAndClassroomID(classroomCourse);
            if (existingClassroomCourse != null) { // The course already exists
                // No insertion
                return true;
            } else { // The course does not yet exist
                mapper.insertClassroomCourse(classroomCourse);
                sqlSession.commit();
                sqlSession.close();
                return true;
            }
        }
    }

    public List<ClassroomCourse> getByCourses(List<Course> courseList) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);
            return mapper.selectClassroomCoursesByCourseIds(courseList);
        }
    }

    public List<ClassroomCourse> getByClassroom(Classroom classroom) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);
            return mapper.selectByClassroomId(classroom.getId());
        }
    }

    public void deleteByCourseIdAndClassroomId(Integer courseId, Integer classroomId) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);
            int i = mapper.deleteByCourseIdAndClassroomId(courseId, classroomId);
            if (i > 0){
                sqlSession.commit();
            }else{
                return;
            }
        }
    }

}
