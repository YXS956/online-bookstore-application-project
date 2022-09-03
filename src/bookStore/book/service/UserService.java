package bookStore.book.service;

import bookStore.book.pojo.User;

public interface UserService {
    User login(String uname , String pwd );
    void regist(User user);
    User getUser(String uname);

    int updateUserColumn(Integer userId, String colName, String value);
}
