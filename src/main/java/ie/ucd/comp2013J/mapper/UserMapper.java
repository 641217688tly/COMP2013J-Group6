package ie.ucd.comp2013J.mapper;

import ie.ucd.comp2013J.pojo.User;

import java.util.List;

public interface UserMapper {
    int insert(User user);

    int update(User user);

    int delete(Integer id);

    User selectById(Integer id);

    List<User> selectAll();
}
