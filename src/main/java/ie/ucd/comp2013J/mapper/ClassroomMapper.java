package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Classroom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomMapper {
    int insertClassroom(Classroom classroom);

    Classroom selectByNumber(Integer number);

    Classroom selectById(Integer id);

    List<Classroom> selectClassroomsByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    int selectTotalClassrooms();


}
