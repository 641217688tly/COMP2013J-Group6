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

public class ClassroomService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    ExcelFileHandleUtils excelFileHandleUtil = new ExcelFileHandleUtils();

    // Insert classrooms from Excel file
    public ArrayList<Classroom> insertExcelFile(InputStream inputStream) {
        ArrayList<Classroom> classrooms = excelFileHandleUtil.getClassroomsFromExcel(inputStream);
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom updatedIDClassroom = this.insertClassroom(classrooms.get(i));
            classrooms.set(i, updatedIDClassroom); // Make the ids of Course objects in the list have specific values, which can be used for later ClassroomCourse table insertions
        }
        return classrooms; // Classrooms are obtained ID at this time
    }

    // Insert a classroom
    public Classroom insertClassroom(Classroom classroom) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            Classroom existingClassroom = mapper.selectByNumber(classroom.getNumber());
            if (existingClassroom != null) { // The classroom already exists
                return existingClassroom;
            } else { // The classroom does not yet exist
                int floor = (classroom.getNumber() / 100) % 10;
                classroom.setFloor(floor);

                String capacity = decideCapacity(classroom.getNumber());
                classroom.setCapacity(capacity);

                boolean status = true;
                classroom.setStatus(status);

                int i = mapper.insertClassroom(classroom);
                if (i > 0) { // If you insert a successful
                    sqlSession.commit(); // Transaction commit
                } else { //If failed to insert
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

    // Get classrooms by classroom courses
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

    // Get the Classroom object on page pageNumber (information about pageSize classrooms per page)
    public List<Classroom> getClassroomsForPage(int pageNumber, int pageSize) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            int startIndex = (pageNumber - 1) * pageSize;
            return mapper.selectClassroomsByPage(startIndex, pageSize);
        }
    }

    // Get total number of classrooms
    public int getTotalClassrooms() {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectTotalClassrooms();
        }
    }

    // Get classroom by classroom ID
    public Classroom getByClassroomId(Integer classroomId) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectById(classroomId);
        }
    }

    // Get all classrooms
    public List<Classroom> getAllClassrooms() {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectAllClassrooms();
        }
    }

    // Get classrooms by filter and specific number
    public List<Classroom> getClassroomsByFilterAndSpecificNumber(Integer floor, String capacity, Boolean status, Integer specificNumber) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectClassroomsByFilterAndSpecificNumber(floor, capacity, status, specificNumber);
        }
    }

    // Get classrooms by floor, capacity, and status
    public List<Classroom> getByFloorCapacityStatus(Integer floor, String capacity, boolean status) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectClassroomsByFloorCapacityStatus(floor, capacity, status);
        }
    }

    // Get classrooms by specific number
    public List<Classroom> getClassroomsBySpecificNumber(int specificNumber) {
        try (SqlSession sqlSession = factory.openSession()) {
            ClassroomMapper mapper = sqlSession.getMapper(ClassroomMapper.class);
            return mapper.selectBySpecificNumber(specificNumber);
        }
    }
}