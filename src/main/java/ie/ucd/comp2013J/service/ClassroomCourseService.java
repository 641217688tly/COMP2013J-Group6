package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.util.ExcelFileHandleUtils;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSessionFactory;

//pojo实体类
public class ClassroomCourseService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    ExcelFileHandleUtils excelFileHandleUtil= new ExcelFileHandleUtils();

    //TODO 需要编写一个方法,其参数为一个Excel课表文件,它能够处理该Excel课表文件,提出去所有classroom和course的参数,之后将所有ClassroomCourse都插入到表中
    public boolean insertExcelFile() {
        //处理Excel文件的方法在工具包Util下新建一个工具类去完成
        return true;
    }

    //TODO 需要编写一个方法,它能够将被用到的教室插入到数据库中
    public boolean insertClassroom(Classroom classroom) {

        return true;
    }
}
