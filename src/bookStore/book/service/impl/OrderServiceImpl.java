package bookStore.book.service.impl;

import bookStore.book.dao.OrderDAO;
import bookStore.book.dao.OrderItemDAO;
import bookStore.book.pojo.*;
import bookStore.book.service.BookService;
import bookStore.book.service.CartItemService;
import bookStore.book.service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO ;
    private OrderItemDAO orderItemDAO ;
    private CartItemService cartItemService ;
    private BookService bookService ;

    @Override
    public void addOrderBean(OrderBean orderBean) {
        //1) 订单表添加一条记录
        //2) 订单详情表添加7条记录
        //3) 购物车项表中需要删除对应的7条记录
        //第1步：
        orderDAO.addOrderBean(orderBean);   //执行完这一步，orderBean中的id是有值的
        //第2步：
        //orderBean中的orderItemList是空的，此处我们应该根据用户的购物车中的购物车项去转换成一个一个的订单项
        User currUser = orderBean.getOrderUser();
        Map<String, CartItem> cartItemMap = currUser.getCart().getCartItemMap();
        for(CartItem cartItem : cartItemMap.values()){
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setState(cartItem.getState());
            orderItem.setBuyCount(cartItem.getBuyCount());
            //给相应的书增加相应的销量
            bookService.addBuyCount(cartItem.getBook().getId(),cartItem.getState(),cartItem.getBuyCount());
            orderItem.setOrderBean(orderBean);
            orderItemDAO.addOrderItem(orderItem);
        }

        //第3步：

        for(CartItem cartItem : cartItemMap.values()){
            cartItemService.deleteCartItem(cartItem);
        }
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        List<OrderBean> orderBeanList = orderDAO.getOrderList(user);

        for (OrderBean orderBean: orderBeanList) {
            Integer totalBookCount = orderDAO.getOrderTotalBookCount(orderBean);
            orderBean.setTotalBookCount(totalBookCount);
        }

        return orderBeanList ;
    }

    @Override
    public List<OrderItem> getOrderItemList(Integer orderBean) {
        List<OrderItem> orderItemList = orderItemDAO.getOrderItemList(orderBean);

        for (OrderItem orderItem: orderItemList) {
            Integer bookId = orderItem.getBook().getId();
            Book book = bookService.getBook(bookId);
            orderItem.setBook(book);
        }

        return orderItemList ;
    }

    @Override
    public List<OrderBean> getAllOrder() {
        List<OrderBean> orderBeanList = orderDAO.getAllOrder();

        for (OrderBean orderBean: orderBeanList) {
            Integer totalBookCount = orderDAO.getOrderTotalBookCount(orderBean);
            orderBean.setTotalBookCount(totalBookCount);
        }

        return orderBeanList ;
    }

    @Override
    public List<OrderBean> getOrderListByKey(String keyWord, Integer pageNo) {
        return orderDAO.getOrderListByKey(keyWord,pageNo);
    }

    @Override
    public int deleteOrder(Integer orderId) {
        return orderDAO.deleteOrder(orderId);
    }
}
