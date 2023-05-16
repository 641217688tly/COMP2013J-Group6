package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.CourseMapper;
import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.util.ExcelFileHandleUtils;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        //插入成功的情况:插入了已经存在的课程;插入了尚未存在的课程;此时返回一个id不为空的course对象
        //插入失败的情况:name;startWeek;endWeek;weekDay;schooltime这些必须值中有没有被上传的,此时返回null
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            Course existingCourse = mapper.selectCourseByNameStartWeekEndWeekWeekDaySchooltime(course);
            if (existingCourse != null) { //已经存在该课程
                return existingCourse;
            } else { //尚未存在该课程
                int i = mapper.insertCourse(course);
                if (i > 0) { //插入成功
                    sqlSession.commit();
                } else { //插入失败
                    return null;
                }
                sqlSession.close();
                return course;
            }
        }
    }

    //得到第pageNumber页的Course对象(每页呈现pageSize个Course的信息)
    public List<Course> getCoursesForPage(int pageNumber, int pageSize) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            // 计算从哪个索引开始获取课程
            int startIndex = (pageNumber - 1) * pageSize;
            return mapper.selectCoursesByPage(startIndex, pageSize);
        }
    }

    //获取所有课程的总数量
    public int getTotalCourses() {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectTotalCourses();
        }
    }


}
