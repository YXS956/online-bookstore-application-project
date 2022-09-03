package bookStore.book.controller;

import bookStore.book.pojo.Book;
import bookStore.book.pojo.Cart;
import bookStore.book.pojo.CartItem;
import bookStore.book.pojo.User;
import bookStore.book.service.CartItemService;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;

public class CartController {

    private CartItemService cartItemService ;

    //index的含义为如果没有operate的情况下，就走index默认方法，见dispatcher里的if(StringUtil.isEmpty(operate)){
    //operate = "index" ;}
    public String index(HttpSession session){
        User user =(User)session.getAttribute("currUser");
        Cart cart = cartItemService.getCart(user);
        user.setCart(cart);
        session.setAttribute("currUser",user);
        return "cart/cart";
    }

    public String addCart(Integer bookId , HttpSession session){
        User user = (User)session.getAttribute("currUser");
        //把book封装成一个cartItem
        CartItem cartItem = new CartItem(new Book(bookId),1,user,"p");
        //将指定的图书添加到当前用户的购物车中
        cartItemService.addOrUpdateCartItem(cartItem,user.getCart(),"p");

        return "redirect:cart.do";
    }

    public String addECart(Integer bookId , HttpSession session){
        User user = (User)session.getAttribute("currUser");
        //把book封装成一个cartItem
        CartItem cartItem = new CartItem(new Book(bookId),1,user,"e");
        //将指定的图书添加到当前用户的购物车中
        cartItemService.addOrUpdateCartItem(cartItem,user.getCart(),"e");

        return "redirect:cart.do";
    }

    public String editCart(Integer cartItemId , Integer buyCount){
        //将上面两个局部参数封装成一个工具cartItem，DAO层获取即可，不用传两个
        if(buyCount == 0) {
            buyCount = 1;
        }
        cartItemService.updateCartItem(new CartItem(cartItemId , buyCount));
        return "";
    }

    public String deleteCart(Integer cartItemId){
        //实际是delete cart里面的item
        //封装同edit方法
        cartItemService.deleteCartItem(new CartItem(cartItemId));
        return "";
    }

    public String clearCart(HttpSession session){
        User user =(User)session.getAttribute("currUser");
        User userBean = new User(user.getId());
        cartItemService.clearCartItems(new CartItem(userBean));
        return "";
    }

    public String cartInfo(HttpSession session){
        User user =(User)session.getAttribute("currUser");
        Cart cart = cartItemService.getCart(user);

        //调用Cart中的三个属性的get方法，目的是在此处计算这三个属性的值，否则这三个属性为null，
        //导致的结果就是下一步的gson转化时，为null的属性会被忽略
        cart.getTotalBookCount();
        cart.getTotalCount();
        cart.getTotalMoney();

        Gson gson = new Gson();
        String cartJsonStr = gson.toJson(cart);
        return "json:"+cartJsonStr ;
    }
}
