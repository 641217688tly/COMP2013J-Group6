package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    void addUser(User user);

    void upgradeRole(User user);

    @Select("select * from users where username = #{username};")
    @ResultMap("UserResultMap")
    User selectByUserName(String username);

    User selectByUserNamePassword(@Param("username") String username, @Param("password") String password);

}
