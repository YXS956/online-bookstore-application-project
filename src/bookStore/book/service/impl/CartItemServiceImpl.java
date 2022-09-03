package bookStore.book.service.impl;

import bookStore.book.dao.CartItemDAO;
import bookStore.book.pojo.Book;
import bookStore.book.pojo.Cart;
import bookStore.book.pojo.CartItem;
import bookStore.book.pojo.User;
import bookStore.book.service.BookService;
import bookStore.book.service.CartItemService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartItemServiceImpl implements CartItemService {

    private CartItemDAO cartItemDAO ;
    private BookService bookService ;

    @Override
    public void addCartItem(CartItem cartItem,String state) {
        cartItemDAO.addCartItem(cartItem,state);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.updateCartItem(cartItem);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) { cartItemDAO.deleteCartItem(cartItem); }

    @Override
    public void clearCartItems(CartItem cartItem) { cartItemDAO.clearCartItems(cartItem); }

    @Override
    public void addOrUpdateCartItem(CartItem cartItem , Cart cart, String state) {
        //1.如果当前用户的购物车中已经存在这个图书了，那么将购物车中这本图书的数量+1
        //2.否则，在我的购物车中新增一个这本图书的CartItem，数量是1
        //判断当前用户的购物车中是否有这本书的CartItem，有->update , 无->add
        if(cart!=null){
            Map<String, CartItem> cartItemMap = cart.getCartItemMap();
            if(cartItemMap==null){
                cartItemMap = new HashMap<>();
            }

            if(cartItemMap.containsKey(cartItem.getBook().getId().toString()+"p") || cartItemMap.containsKey(cartItem.getBook().getId().toString()+"e")){

                CartItem cartItemTemp;
                if (cartItemMap.containsKey(cartItem.getBook().getId().toString()+"p")){
                    cartItemTemp = cartItemMap.get(cartItem.getBook().getId().toString()+"p");
                }else{
                    cartItemTemp = cartItemMap.get(cartItem.getBook().getId().toString()+"e");}

                if(Objects.equals(cartItemTemp.getState(), state)){
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount()+1);
                updateCartItem(cartItemTemp);
                }else{
                    //have such book but state is different
                    addCartItem(cartItem,state);
                }
            }else{
                //not having such book no matter e or p
                addCartItem(cartItem,state);
            }
        }else{
            //cart is empty
            addCartItem(cartItem,state);
        }
    }


    @Override
    public List<CartItem> getCartItemList(User user) {
        List<CartItem> PCartItemList = cartItemDAO.getPCartItemList(user);
        for(CartItem cartItem : PCartItemList){
            Book book = bookService.getBook(cartItem.getBook().getId());
            cartItem.setBook(book);
            //此处需要调用getXj()，目的是执行getXj()内部的代码，让book的price乘以buyCount，从而计算出xj这个属性的值
            cartItem.getXj();
        }
        List<CartItem> ECartItemList = cartItemDAO.getECartItemList(user);
        for(CartItem cartItem : ECartItemList){
            Book book = bookService.getBook(cartItem.getBook().getId());
            cartItem.setBook(book);
            //此处需要调用getXj()，目的是执行getXj()内部的代码，让book的price乘以buyCount，从而计算出xj这个属性的值
            cartItem.getXj();
        }
        List<CartItem> cartItemList = Stream.of(PCartItemList, ECartItemList).flatMap(Collection::stream).collect(Collectors.toList());
        return cartItemList;
    }

    @Override
    public Cart getCart(User user) {
        //获取item的list
        List<CartItem> cartItemList = getCartItemList(user);
        Map<String,CartItem> cartItemMap = new HashMap<>();
        //相当于把list改为map
        for (CartItem cartItem : cartItemList){
            String cartItemKey = cartItem.getBook().getId().toString()+cartItem.getState();
            cartItemMap.put(cartItemKey,cartItem);
        }
        Cart cart = new Cart();
        cart.setCartItemMap(cartItemMap);
        return cart;
    }
}
