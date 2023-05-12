package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.CourseMapper;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.util.ExcelFileHandleUtils;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;
import java.util.ArrayList;

//pojo实体类
public class CourseService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    ExcelFileHandleUtils excelFileHandleUtil = new ExcelFileHandleUtils();

    public ArrayList<Course> insertExcelFile(InputStream inputStream) {
        ArrayList<Course> courses = excelFileHandleUtil.getCoursesFromExcel(inputStream);
        for (int i = 0; i < courses.size(); i++) {
            Course updatedIDCourse = this.insertCourse(courses.get(i));
            courses.set(i, updatedIDCourse); //使得列表中的Course对象的id都有具体的值,这样可以用于之后的ClassroomCourse表的插入
        }
        return courses;
    }

    public Course insertCourse(Course course) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            Course existingCourse = mapper.selectCourseByNameStartWeekEndWeekWeekDaySchooltime(course);
            if (existingCourse != null) { //已经存在该课程
                return existingCourse;
            } else { //尚未存在该课程
                mapper.insertCourse(course);
                sqlSession.commit();
                return course;
            }
        }
    }


}
