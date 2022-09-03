package bookStore.book.dao;

import bookStore.book.pojo.OrderBean;
import bookStore.book.pojo.User;

import java.util.List;

public interface OrderDAO {
    //添加订单
    void addOrderBean(OrderBean orderBean);
    //获取指定用户的订单列表
    List<OrderBean> getOrderList(User user);

    List<OrderBean> getOrderListByKey(String keyWord, Integer pageNo);

    List<OrderBean> getAllOrder();
    Integer getOrderTotalBookCount(OrderBean orderBean);
    int deleteOrder(Integer orderId);
}
