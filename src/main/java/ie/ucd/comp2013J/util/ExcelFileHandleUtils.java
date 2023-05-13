package ie.ucd.comp2013J.util;

import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelFileHandleUtils {

    // 二维数组用于存储课程信息和教室信息
    public Course[][] courses = new Course[6][5];
    public Classroom[][] classrooms = new Classroom[6][5];

    public ArrayList<Course> getCoursesFromExcel(InputStream inputStream) {
        handleExcelFile(inputStream);
        List<Course> courseList = new ArrayList<>();
        for (Course[] courseRow : courses) {
            courseList.addAll(Arrays.asList(courseRow));
        }
        return new ArrayList<>(courseList);
    }

    public ArrayList<Classroom> getClassroomsFromExcel(InputStream inputStream) {
        handleExcelFile(inputStream);
        List<Classroom> classroomList = new ArrayList<>();
        for (Classroom[] classroomRow : classrooms) {
            classroomList.addAll(Arrays.asList(classroomRow));
        }
        return new ArrayList<>(classroomList);
    }

    public  void handleExcelFile(InputStream inputStream) {
        try {
            // 创建Workbook，用于读取Excel文件
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 选择第一张工作表(sheet)
            Sheet sheet = workbook.getSheetAt(0);
            // 循环处理每一个单元格(cell)
            for (int rowIndex = 2; rowIndex <= 7; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                for (int colIndex = 2; colIndex <= 6; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        String cellValue = cell.getStringCellValue();
                        // 如果单元格内容不为空，解析课程和教室信息
                        if (!cellValue.trim().isEmpty()) {
                            parseCourseAndClassroomInfo(cellValue, rowIndex - 2, colIndex - 2);
                        }
                    }
                }
            }
            workbook.close(); // 关闭workbook
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseCourseAndClassroomInfo(String cellValue, int row, int col) {
        // Updated regular expression to match the special cases
        //Pattern pattern = Pattern.compile("(.+?)/(\\w+)/(\\(\\d+-\\d+节\\)\\d+-\\d+周)(.*?)(?:/(.*))?/(.*)?");
        Pattern pattern = Pattern.compile("(.+?)/(\\w+)/(\\(\\d+-\\d+节\\)\\d+-\\d+周)/(.+?)/(.+?)/(.+)");
        Matcher matcher = pattern.matcher(cellValue);
        //TODO 正则表达式存在BUG,会导致Course的name无法被正确提取而产生null,最后在sql语句中造成报错
        if (matcher.find()) {
            String courseInfo = matcher.group(1).trim() + "/" + matcher.group(2).trim();

            int startWeek = Integer.parseInt(matcher.group(3).split("-")[0].replace("(\\d+-\\d+节)", ""));
            int endWeek = Integer.parseInt(matcher.group(3).split("-")[1].replace("周", ""));

            String classroomInfo = matcher.group(4).trim();
            int roomNumber = -1;
            if (classroomInfo.matches("\\d+-\\d+")) {
                String[] split = classroomInfo.split("-");
                roomNumber = Integer.parseInt(split[1]);
            }
            String teacherAndClassInfo = matcher.group(5) + "/" + matcher.group(6);
            if (!classroomInfo.contains("智慧树平台")) {
                Classroom classroom = new Classroom();
                classroom.setNumber(roomNumber);

                Course course = new Course();
                course.setName(courseInfo);
                course.setStartWeek(startWeek);
                course.setEndWeek(endWeek);
                course.setDetail(teacherAndClassInfo);

                // Set weekday and schooltime according to the position in the array
                course.setWeekDay(col + 1);
                course.setSchooltime(row + 1);

                courses[row][col] = course;
                classrooms[row][col] = classroom;
            }
        }
    }
}
