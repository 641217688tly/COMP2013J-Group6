package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.UserMapper;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    // User login method
    public User login(String username, String password) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByUserNamePassword(username, password);
        sqlSession.close();
        return user;
    }

    // User registration method
    public boolean register(User user) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User checkedUser = mapper.selectByUserName(user.getUsername());
        if (checkedUser == null) {
            mapper.addUser(user);
            sqlSession.commit();
        }
        sqlSession.close();
        return checkedUser == null;
    }

    // Upgrade user role method
    public boolean upgradeRole(User user) { // Can upgrade the status of ordinary users as an administrator
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User checkedUser = mapper.selectByUserName(user.getUsername()); // Check will be awarded the administrator privileges user exists
        if (checkedUser != null) {
            user.setRole("administrator");
            mapper.upgradeRole(user);
            sqlSession.commit(); // Commit transaction
        }
        sqlSession.close();
        return checkedUser != null;
    }
}