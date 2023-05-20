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

    List<Classroom> selectAllClassrooms();

    List<Classroom> selectClassroomsByFilterAndSpecificNumber(@Param("floor") Integer floor, @Param("capacity") String capacity, @Param("status") Boolean status, @Param("specificNumber") Integer specificNumber);

    //暂未使用的语句:
    List<Classroom> selectClassroomsByFloorCapacityStatus(@Param("floor") Integer floor, @Param("capacity") String capacity, @Param("status") Boolean status);

    List<Classroom> selectBySpecificNumber(Integer specificNumber);


}
