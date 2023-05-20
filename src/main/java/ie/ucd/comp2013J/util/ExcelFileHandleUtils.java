package ie.ucd.comp2013J.util;

import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
/*        ArrayList<Course> coursesArrayList = new ArrayList<>(courseList);
        for (int i = 0; i < coursesArrayList.size(); i++) {
            System.out.println(coursesArrayList.get(i).getName());
        }*/
        return new ArrayList<>(courseList);
    }

    public ArrayList<Classroom> getClassroomsFromExcel(InputStream inputStream) {
        handleExcelFile(inputStream);
        List<Classroom> classroomList = new ArrayList<>();
        for (Classroom[] classroomRow : classrooms) {
            classroomList.addAll(Arrays.asList(classroomRow));
        }
/*        ArrayList<Classroom> classroomsArrayList = new ArrayList<>(classroomList);
        for (int i = 0; i < classroomsArrayList.size(); i++) {
            System.out.println(classroomsArrayList.get(i).getNumber());
        }*/
        return new ArrayList<>(classroomList);
    }

    public void handleExcelFile(InputStream inputStream) {
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
//TODO start_week为null
// Split the string by '/' but ignore '/' inside brackets
        String[] parts = cellValue.split("/(?![^(]*\\))");

// Create new Course and Classroom objects
        Course course = new Course();
        Classroom classroom = new Classroom();

// Weekday and schooltime are set based on the arguments provided
        course.setWeekDay(col + 1);
        course.setSchooltime(row + 1);

// Process each part of the course information
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();

            if (i == 0) { // The first part is always the course name
                course.setName(parts[0].trim());
            } else if (i == 2) { // Match the week range pattern
                char[] m = part.toCharArray();
                //String[] weeks = part.split("-周");
                String[] stringArray = new String[m.length];

                for (int n = 0; n < m.length; n++) {
                    stringArray[n] = String.valueOf(m[i]);
                }

                course.setStartWeek(Integer.parseInt(stringArray[6]));
                String a = stringArray[8] + stringArray[9];
                course.setEndWeek(Integer.parseInt(a));
            } else if (i == 3) {
                if (part.startsWith("4-")) {
                    // If the course is taught in the 4th building
                    String roomNumber = part.split("-")[3].substring(3, 5); // Get the room number
                    classroom.setNumber(Integer.parseInt(roomNumber));
                }
            } else {
// Any other part is considered as detail
                String currentDetail = course.getDetail();
                if (currentDetail == null) {
                    course.setDetail(part);
                } else {
                    course.setDetail(currentDetail + " " + part);
                }
            }
        }

/*
    private void parseCourseAndClassroomInfo(String cellValue, int row, int col) {
        //TODO start_week为null
        // Split the string by '/' but ignore '/' inside brackets
        String[] parts = cellValue.split("/(?![^(]*\\))");

        // Create new Course and Classroom objects
        Course course = new Course();
        Classroom classroom = new Classroom();

        // Weekday and schooltime are set based on the arguments provided
        course.setWeekDay(col + 1);
        course.setSchooltime(row + 1);

        // Process each part of the course information
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();

            if (i == 0) { // The first part is always the course name
                course.setName(part);
            } else if (part.matches(".*\\d+-\\d+周")) { // Match the week range pattern
                String[] weeks = part.split("-|周");
                course.setStartWeek(Integer.parseInt(weeks[0]));
                course.setEndWeek(Integer.parseInt(weeks[1]));
            } else if (part.startsWith("4-")) { // If the course is taught in the 4th building
                String roomNumber = part.split("-")[1].substring(0, 3); // Get the room number
                classroom.setNumber(Integer.parseInt(roomNumber));
            } else {
                // Any other part is considered as detail
                String currentDetail = course.getDetail();
                if (currentDetail == null) {
                    course.setDetail(part);
                } else {
                    course.setDetail(currentDetail + " " + part);
                }
            }
        }

        // Add the created Course and Classroom objects to the arrays
        if (classroom.getNumber() != null) { // Only add if the course is in the 4th building
            courses[row][col] = course;
            classrooms[row][col] = classroom;
        }
    }
*/

/*    private void parseCourseAndClassroomInfo(String cellValue, int row, int col) {
        // Updated regular expression to match the special cases
        //Pattern pattern = Pattern.compile("(.+?)/(\\w+)/(\\(\\d+-\\d+节\\)\\d+-\\d+周)(.*?)(?:/(.*))?/(.*)?");
        //Pattern pattern = Pattern.compile("(.+?)/(\\w+)/(\\(\\d+-\\d+节\\)\\d+-\\d+周)/(.+?)/(.+?)/(.+)");
        Pattern pattern = Pattern.compile(("^([^\\/]+)\\/([A-Z0-9]+)[\\/ ]+([^\\/]+)[\\/ ]+([^\\/]+)\\/\\(([^)]+)\\)(\\d+)-(\\d+)周\\/(.*)$"));
        //Pattern pattern = Pattern.compile(("^([^\\/]+)\\/([A-Z0-9]+)[\\/ ]+([^\\/]+)[\\/ ]+([^\\/]+)\\/\\((?:[^)]+)\\)(\\d+)-(\\d+)周\\/(.*)$"));
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
    }*/

    }
}
