package bookStore.book.dao.impl;

import bookStore.mybatis.basedao.BaseDAO;
import bookStore.book.dao.OrderDAO;
import bookStore.book.pojo.OrderBean;
import bookStore.book.pojo.User;

import java.math.BigDecimal;
import java.util.List;

public class OrderDAOImpl extends BaseDAO<OrderBean> implements OrderDAO {
    @Override
    public void addOrderBean(OrderBean orderBean) {
        int orderBeanId = super.executeUpdate("insert into t_order values(0,?,?,?,?,?)",orderBean.getOrderNo(),orderBean.getOrderDate(),orderBean.getOrderUser().getId(),orderBean.getOrderMoney(),orderBean.getOrderStatus());
        orderBean.setId(orderBeanId);
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        return executeQuery("SELECT * FROM t_order WHERE orderUser = ?",user.getId());
    }

    @Override
    public List<OrderBean> getOrderListByKey(String keyWord, Integer pageNo) {
        return executeQuery(" SELECT o.id,orderNo,orderDate,orderUser,orderMoney,orderStatus FROM t_order o JOIN t_user u on o.orderUser = u.id WHERE (orderDate LIKE ? or uname LIKE ?) LIMIT ?,5","%"+keyWord+"%","%"+keyWord+"%",(pageNo-1)*5);
    }

    @Override
    public List<OrderBean> getAllOrder() {
        return executeQuery("SELECT * FROM t_order");
    }

    @Override
    public Integer getOrderTotalBookCount(OrderBean orderBean) {
        String sql = "SELECT SUM(t3.buyCount) AS totalBookCount , t3.orderBean FROM " +
                "(" +
                "SELECT t1.id , t2.buyCount , t2.orderBean FROM t_order t1 INNER JOIN t_order_item t2 " +
                "ON t1.id = t2.orderBean WHERE t1.orderUser = ? " +
                ") t3 WHERE t3.orderBean = ? GROUP BY t3.orderBean" ;
        return ((BigDecimal)executeComplexQuery(sql,orderBean.getOrderUser().getId(),orderBean.getId())[0]).intValue();
    }

    @Override
    public int deleteOrder(Integer orderId) {
        return executeUpdate("delete from t_order where id=?",orderId);
    }
}
