package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Reservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReservationMapper {
    List<Reservation> selectByClassroomIdWeekSchooltimeWeekDay(@Param("classroomId") Integer classroomId, @Param("week") Integer week, @Param("weekDay") Integer weekDay, @Param("schooltime") Integer schooltime);

    List<Reservation> selectByClassroomId(Integer classroomId);

    int insertReservation(Reservation reservation);

    // Unused methods:
    int update(Reservation reservation);

    int delete(Integer id);

    Reservation selectById(Integer id);

    List<Reservation> selectAll();
}
