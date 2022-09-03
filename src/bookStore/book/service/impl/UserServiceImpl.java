package bookStore.book.service.impl;

import bookStore.book.dao.UserDAO;
import bookStore.book.pojo.User;
import bookStore.book.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO ;

    @Override
    public User login(String uname, String pwd) {
        return userDAO.getUser(uname,pwd);
    }

    @Override
    public void regist(User user) {
        userDAO.addUser(user);
    }

    @Override
    public User getUser(String uname) {
        return userDAO.getUser(uname);
    }

    @Override
    public int updateUserColumn(Integer userId, String colName, String value) { return userDAO.updateUser(userId,colName,value);}
}
