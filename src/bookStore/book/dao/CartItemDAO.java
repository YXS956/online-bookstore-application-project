package bookStore.book.dao;

import bookStore.book.pojo.CartItem;
import bookStore.book.pojo.User;

import java.util.List;

public interface CartItemDAO {
    //新增购物车项
    void addCartItem(CartItem cartItem,String state);
    //修改特定的购物车项
    void updateCartItem(CartItem cartItem);
    //删除特定购物车项
    void deleteCartItem(CartItem cartItem);
    //删除所有购物车项
    void clearCartItems(CartItem cartItem);
    //获取特定用户的所有购物车项
    List<CartItem> getPCartItemList(User user);

    List<CartItem> getECartItemList(User user);
}
