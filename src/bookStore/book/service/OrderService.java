package bookStore.book.service;

import bookStore.book.pojo.OrderBean;
import bookStore.book.pojo.OrderItem;
import bookStore.book.pojo.User;

import java.util.List;

public interface OrderService {
    void addOrderBean(OrderBean orderBean);
    List<OrderBean> getOrderList(User user);

    List<OrderItem> getOrderItemList(Integer orderBean);

    List<OrderBean> getAllOrder();

    List<OrderBean> getOrderListByKey(String keyWord, Integer pageNo);

    int deleteOrder(Integer orderId);
}
