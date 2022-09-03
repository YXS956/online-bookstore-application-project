package bookStore.book.dao.impl;

import bookStore.mybatis.basedao.BaseDAO;
import bookStore.book.dao.OrderItemDAO;
import bookStore.book.pojo.OrderItem;

import java.util.List;

public class OrderItemDAOImpl extends BaseDAO<OrderItem> implements OrderItemDAO {
    @Override
    public void addOrderItem(OrderItem orderItem) {
        super.executeUpdate("insert into t_order_item values(0,?,?,?,?)",orderItem.getBook().getId(),orderItem.getState(),orderItem.getBuyCount(),orderItem.getOrderBean().getId()) ;
    }

    @Override
    public List<OrderItem> getOrderItemList(Integer orderBean) {
        return executeQuery("SELECT * FROM t_order_item WHERE orderBean = ?",orderBean);
    }
}
