package bookStore.book.dao;

import bookStore.book.pojo.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    //添加订单项
    void addOrderItem(OrderItem orderItem);

    public List<OrderItem> getOrderItemList(Integer orderBean);
}
