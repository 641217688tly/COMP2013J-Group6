package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.ReservationMapper;
import ie.ucd.comp2013J.pojo.Reservation;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ReservationService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    // Get reservations by classroom ID, week, week day, and school time
    public List<Reservation> getByClassroomIdWeekSchooltimeWeekDay(Integer classroomId, Integer week, Integer weekDay, Integer schooltime) {
        try (SqlSession sqlSession = factory.openSession()) {
            ReservationMapper mapper = sqlSession.getMapper(ReservationMapper.class);
            return mapper.selectByClassroomIdWeekSchooltimeWeekDay(classroomId, week, weekDay, schooltime);
        }
    }

    // Make a reservation
    public boolean makeAppointment(Reservation reservation) {
        try (SqlSession sqlSession = factory.openSession()) {
            ReservationMapper mapper = sqlSession.getMapper(ReservationMapper.class);
            int i = mapper.insertReservation(reservation);
            if (i > 0) {
                sqlSession.commit();
                return true;
            } else {
                return false;
            }
        }
    }

    // Get reservations by classroom ID
    public List<Reservation> getReservationsByClassroomId(Integer classroomId) {
        try (SqlSession sqlSession = factory.openSession()) {
            ReservationMapper mapper = sqlSession.getMapper(ReservationMapper.class);
            return mapper.selectByClassroomId(classroomId);
        }
    }
}