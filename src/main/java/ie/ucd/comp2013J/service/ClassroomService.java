package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSessionFactory;

//pojo实体类
public class ClassroomService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    //TODO 需要编写一个方法,其参数为一个Excel课表文件,它能够将该Excel课表文件的给分批处理并在数据库内进行更新(Step2)
    public boolean updateExcelCoursesFile() {

        return true;
    }

    //TODO 需要编写一个方法,其参数为一个Course对象,它能够将该课程信息上传到数据库中(Step1)
    public boolean updateCourse(Course course) {

        return true;
    }

}
