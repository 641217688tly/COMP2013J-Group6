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

public class CourseService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    ExcelFileHandleUtils excelFileHandleUtil = new ExcelFileHandleUtils();

    // Insert courses from Excel file
    public ArrayList<Course> insertExcelFile(InputStream inputStream) {
        ArrayList<Course> courses = excelFileHandleUtil.getCoursesFromExcel(inputStream);
        for (int i = 0; i < courses.size(); i++) {
            Course updatedIDCourse = this.insertCourse(courses.get(i));
            courses.set(i, updatedIDCourse); // Make the ids of Course objects in the list have specific values, which can be used for later ClassroomCourse table insertions
        }
        return courses;
    }

    // Insert a course
    public Course insertCourse(Course course) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            Course existingCourse = mapper.selectCourseByNameStartWeekEndWeekWeekDaySchooltime(course);
            if (existingCourse != null) { //The course already exists
                return existingCourse;
            } else { //The course yet do not exist
                int i = mapper.insertCourse(course);
                if (i > 0) { //Successful insertion
                    sqlSession.commit();
                } else { // Insert the failure
                    return null;
                }
                sqlSession.close();
                return course;
            }
        }
    }

    // Get courses by classroom courses
    public List<Course> getByClassroomCourses(List<ClassroomCourse> classroomCourses) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectByClassroomCourses(classroomCourses);
        }
    }

    // Get course by course ID
    public Course getByCourseId(Integer courseId) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectById(courseId);
        }
    }

    // Update a course
    public void updateCourse(Course newCourse) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int i = mapper.updateCourse(newCourse);
            if (i > 0) { // update successfully
                sqlSession.commit();
            }
        }
    }

    // Get courses by specific name and page
    public List<Course> getCoursesBySpecificNameAndPage(String specificName, Integer startIndex, Integer pageSize) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectCoursesBySpecificNameAndPage(specificName, startIndex, pageSize);
        }
    }

    // Get total number of courses with specific name
    public int getTotalCoursesWithSpecificName(String specificName) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectTotalCoursesWithSpecificName(specificName);
        }
    }

    // Unused SQL statements:

    // Get total number of courses
    public int getTotalCourses() {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectTotalCourses();
        }
    }

    // Get courses for a specific page
    public List<Course> getCoursesForPage(int pageNumber, int pageSize) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int startIndex = (pageNumber - 1) * pageSize;
            return mapper.selectCoursesByPage(startIndex, pageSize);
        }
    }

    // Get courses by classroom ID
    public List<Course> getCoursesByClassroomId(int classroomId) {
        try (SqlSession sqlSession = factory.openSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.selectCoursesByClassroomId(classroomId);
        }
    }
}