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

    //可以添加一个更新方法,对于需要管理员权限的用户为其role属性更新为管理员

}
