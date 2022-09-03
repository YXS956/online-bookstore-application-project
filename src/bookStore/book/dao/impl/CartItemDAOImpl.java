package bookStore.book.dao.impl;

import bookStore.mybatis.basedao.BaseDAO;
import bookStore.book.dao.CartItemDAO;
import bookStore.book.pojo.CartItem;
import bookStore.book.pojo.User;

import java.util.List;

public class CartItemDAOImpl extends BaseDAO<CartItem> implements CartItemDAO {
    @Override
    public void addCartItem(CartItem cartItem,String state) {
        executeUpdate("insert into t_cart_item values(0,?,?,?,?)",cartItem.getBook().getId(),state,cartItem.getBuyCount(),cartItem.getUserBean().getId());
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        executeUpdate("update t_cart_item set buyCount = ? where id = ?" , cartItem.getBuyCount(),cartItem.getId()) ;
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        executeUpdate("delete from t_cart_item where id = ? ",cartItem.getId());
    }

    @Override
    public void clearCartItems(CartItem cartItem) {
        executeUpdate("delete from t_cart_item where userBean = ?",cartItem.getUserBean().getId());
    }

    @Override
    public List<CartItem> getPCartItemList(User user) {
        return executeQuery("select * from t_cart_item where userBean = ? and state = 'p' " , user.getId());
    }

    @Override
    public List<CartItem> getECartItemList(User user) {
        return executeQuery("select * from t_cart_item where userBean = ? and state = 'e' " , user.getId());
    }
}
