package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.ClassroomMapper;
import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.util.ExcelFileHandleUtils;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        //插入成功的情况:插入了已经存在的教室;插入了尚未存在的教室;此时返回一个id不为空的classroom对象
        //插入失败的情况:classroomNumber这个必须值没有被上传,此时返回null
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

                int i = mapper.insertClassroom(classroom);
                if (i > 0) { //如果插入成功
                    sqlSession.commit(); //事务提交
                } else { //如果插入失败
                    return null;
                }
                sqlSession.close();
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

    public List<Classroom> getByClassroomCourses(List<ClassroomCourse> classroomCourseList) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            List<Classroom> classroomList = new ArrayList<>();
            for (int i = 0; i < classroomCourseList.size(); i++) {
                classroomList.add(i, mapper.selectById(classroomCourseList.get(i).getClassroomId()));
            }
            return classroomList;
        }
    }

    //得到第pageNumber页的Classroom对象(每页呈现pageSize个Classroom的信息)
    public List<Classroom> getClassroomsForPage(int pageNumber, int pageSize) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            // 计算从哪个索引开始获取教室
            int startIndex = (pageNumber - 1) * pageSize;
            return mapper.selectClassroomsByPage(startIndex, pageSize);
        }
    }

    //获取所有教室的总数量
    public int getTotalClassrooms() {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectTotalClassrooms();
        }
    }

    public Classroom getByClassroomId(Integer classroomId){
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectById(classroomId);
        }
    }

}
