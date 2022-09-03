package bookStore.book.dao;

import bookStore.book.pojo.User;

public interface UserDAO {
    User getUser(String uname , String pwd );
    void addUser(User user);
    User getUser(String uname);

    int updateUser(Integer userId, String colName, String value);
}
