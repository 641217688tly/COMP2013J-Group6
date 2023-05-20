package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.ClassroomCourseMapper;
import ie.ucd.comp2013J.mapper.ReservationMapper;
import ie.ucd.comp2013J.pojo.Reservation;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

//pojo实体类
public class ReservationService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public List<Reservation> getByClassroomIdWeekSchooltimeWeekDay(Integer classroomId, Integer week, Integer weekDay, Integer schooltime) {
        try (SqlSession sqlSession = factory.openSession()) {
            ReservationMapper mapper = sqlSession.getMapper(ReservationMapper.class);
            return mapper.selectByClassroomIdWeekSchooltimeWeekDay(classroomId, week, weekDay, schooltime);
        }
    }

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
}
