package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.ClassroomCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomMapper {
    int insertClassroom(Classroom classroom);

    Classroom selectByNumber(Integer number);

    Classroom selectById(Integer id);
}
