package ie.ucd.comp2013J.service;

import ie.ucd.comp2013J.mapper.UserMapper;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

//pojo实体类
public class UserService { //在此实现针对Classroom的所有增删改查的方法
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public User login(String username, String password) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByUserNamePassword(username, password);
        sqlSession.close();
        return user;
    }

    public boolean register(User user) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User checkedUser = mapper.selectByUserName(user.getUsername());
        if (checkedUser == null) {
            mapper.addUser(user);
            sqlSession.commit(); //提交事务
        }
        sqlSession.close();
        return checkedUser == null;
    }

    public boolean upgradeRole(User user) { //可以将普通用户的身份升级为管理员
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User checkedUser = mapper.selectByUserName(user.getUsername()); //检查将要被授予管理员权限的用户是否存在
        if (checkedUser != null) {
            user.setRole("administrator");
            mapper.upgradeRole(user);
            sqlSession.commit(); //提交事务
        }
        sqlSession.close();
        return checkedUser != null;
    }

}
