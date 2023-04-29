package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    void addUser(User user);

    int update(User user);

    void upgradeRole(User user);

    int delete(Integer id);

    User selectById(Integer id);

    @Select("select * from users where username = #{username};")
    @ResultMap("UserResultMap")
    User selectByUserName(String username);

    User selectByUserNamePassword(@Param("username") String username, @Param("password") String password);

    List<User> selectAll();
}
