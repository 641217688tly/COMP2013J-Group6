package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.CourseMapper;
import ie.ucd.comp2013J.mapper.UserMapper;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.util.ExcelFileHandleUtils;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

//pojo实体类
public class CourseService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    ExcelFileHandleUtils excelFileHandleUtil = new ExcelFileHandleUtils();

    //TODO 需要编写一个方法,其参数为一个Excel课表文件,它能够处理该Excel课表文件,提取出所有course的参数,之后将所有教室都插入到表中
    public boolean insertExcelFile() {
        //处理Excel文件的方法在工具包Util下新建一个工具类去完成
        return true;
    }


    public boolean insertCourse(Course course) {
        SqlSession sqlSession = factory.openSession();
        CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
        //先检测表中是否已经有了该课程
        Course checkedCourse = mapper.selectCourseByNameStartWeekEndWeekSchooltime(course);
        if (checkedCourse != null) {
            return false; //相同的课程已经存在了
        }
        int rows = mapper.insertCourse(course);
        if (rows > 0) {//插入成功
            return true;
        } else { //插入失败
            return false;
        }
    }


}
