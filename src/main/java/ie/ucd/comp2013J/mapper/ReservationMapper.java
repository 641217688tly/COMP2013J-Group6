package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.Reservation;

import java.util.List;

public interface ReservationMapper {


    //以下方法暂未用到:
    int insert(Reservation reservation);

    int update(Reservation reservation);

    int delete(Integer id);

    Reservation selectById(Integer id);

    List<Reservation> selectAll();
}
