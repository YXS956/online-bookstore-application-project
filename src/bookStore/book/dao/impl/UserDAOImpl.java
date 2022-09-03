package bookStore.book.dao.impl;

import bookStore.mybatis.basedao.BaseDAO;
import bookStore.book.dao.UserDAO;
import bookStore.book.pojo.User;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public User getUser(String uname, String pwd) {
        return load("select * from t_user where uname like ? and pwd like ? " , uname , pwd );
    }

    @Override
    public void addUser(User user) {
        executeUpdate("insert into t_user values(0,?,?,?,0)",user.getUname(),user.getPwd(),user.getEmail()) ;
    }

    @Override
    public User getUser(String uname) {
        return load("select * from t_user where uname = ?" , uname);
    }

    @Override
    public int updateUser(Integer userId, String colName, String value) {
        return executeUpdate("UPDATE t_user SET "+colName+" = ? WHERE id = ?",value,userId);
    }
}
