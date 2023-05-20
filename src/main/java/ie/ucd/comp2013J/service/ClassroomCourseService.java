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

//pojo实体类
public class ClassroomCourseService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public boolean insertExcelFile(ArrayList<Course> courses, ArrayList<Classroom> classrooms) {
        if (courses.size() != classrooms.size()) {
            throw new IllegalArgumentException("Excel课表文件解析存在错误");
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

    private ClassroomCourse insertClassroomCourse(Course course, Classroom classroom) { //该insert方法为insertExcelFile提供调用
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);

            ClassroomCourse classroomCourse = new ClassroomCourse();
            classroomCourse.setCourseId(course.getId());
            classroomCourse.setClassroomId(classroom.getId());

            ClassroomCourse existingClassroomCourse = mapper.selectByCourseIDAndClassroomID(classroomCourse);
            if (existingClassroomCourse != null) { //已经存在该课程
                return existingClassroomCourse;
            } else { //尚未存在该课程
                mapper.insertClassroomCourse(classroomCourse);
                sqlSession.commit();
                return classroomCourse;
            }
        }
    }

    public boolean insertSingleClassroomCourse(Course course, Classroom classroom) { //该insert方法在插入单个课表信息时被调用
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomCourseMapper mapper = sqlSession.getMapper(ClassroomCourseMapper.class);

            ClassroomCourse classroomCourse = new ClassroomCourse();
            if (course == null || classroom == null) { //course或classroom插入失败
                return false;
            }
            classroomCourse.setCourseId(course.getId());
            classroomCourse.setClassroomId(classroom.getId());

            ClassroomCourse existingClassroomCourse = mapper.selectByCourseIDAndClassroomID(classroomCourse);
            if (existingClassroomCourse != null) { //已经存在该课程
                //不插入
                return true;
            } else { //尚未存在该课程
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
