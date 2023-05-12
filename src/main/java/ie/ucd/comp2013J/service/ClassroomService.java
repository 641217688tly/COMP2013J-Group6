package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.ClassroomMapper;
import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.util.ExcelFileHandleUtils;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;
import java.util.ArrayList;

//pojo实体类
public class ClassroomService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    ExcelFileHandleUtils excelFileHandleUtil = new ExcelFileHandleUtils();

    public ArrayList<Classroom> insertExcelFile(InputStream inputStream) {
        ArrayList<Classroom> classrooms = excelFileHandleUtil.getClassroomsFromExcel(inputStream);
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom updatedIDClassroom = this.insertClassroom(classrooms.get(i));
            classrooms.set(i, updatedIDClassroom); //使得列表中的Course对象的id都有具体的值,这样可以用于之后的ClassroomCourse表的插入
        }
        return classrooms;//此时的classrooms都已经获得了ID
    }

    public Classroom insertClassroom(Classroom classroom) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            Classroom existingClassroom = mapper.selectByNumber(classroom.getNumber());
            if (existingClassroom != null) {//该教室已经存在
                return existingClassroom;
            } else { //该教室尚未存在
                int floor = (classroom.getNumber() / 100) % 10;
                classroom.setFloor(floor);

                String capacity = decideCapacity(classroom.getNumber());
                classroom.setCapacity(capacity);

                boolean status = true;
                classroom.setStatus(status);

                mapper.insertClassroom(classroom);
                sqlSession.commit();
                return classroom;
            }
        }
    }

    private String decideCapacity(int classroomNumber) {
        String stringNumber = String.valueOf(classroomNumber);
        String lastTwoDigits = stringNumber.substring(1);
        if (lastTwoDigits.equals("14") || lastTwoDigits.equals("13") || lastTwoDigits.equals("02") || lastTwoDigits.equals("01")) {
            return "大";
        } else if (lastTwoDigits.equals("04") || lastTwoDigits.equals("05") || lastTwoDigits.equals("06") || lastTwoDigits.equals("07") || lastTwoDigits.equals("10") || lastTwoDigits.equals("11") || lastTwoDigits.equals("15") || lastTwoDigits.equals("16") || lastTwoDigits.equals("17") || lastTwoDigits.equals("18")) {
            return "中";
        } else if (lastTwoDigits.equals("08") || lastTwoDigits.equals("09") || lastTwoDigits.equals("19") || lastTwoDigits.equals("20")) {
            return "小";
        } else {
            return "中";
        }
    }
}
