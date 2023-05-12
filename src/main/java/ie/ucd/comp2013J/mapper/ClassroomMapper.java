package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Classroom;

import java.util.List;

public interface ClassroomMapper {
    int insertClassroom(Classroom classroom);

    Classroom selectByNumber(Integer number);

    int update(Classroom classroom);

    int delete(Integer id);

    Classroom selectById(Integer id);

    List<Classroom> selectAll();
}
