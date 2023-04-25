package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Reservation;

import java.util.List;

public interface ReservationMapper {
    int insert(Reservation reservation);

    int update(Reservation reservation);

    int delete(Integer id);

    Reservation selectById(Integer id);

    List<Reservation> selectAll();
}
