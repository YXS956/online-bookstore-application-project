package bookStore.book.controller;

import bookStore.book.pojo.Book;
import bookStore.book.pojo.OrderBean;
import bookStore.book.pojo.OrderItem;
import bookStore.book.pojo.User;
import bookStore.book.service.OrderService;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController {

    private OrderService orderService ;

    //结账
    public String checkout(HttpSession session){
        OrderBean orderBean = new OrderBean() ;
        Date now = new Date();
        int year = now.getYear();
        int month = now.getMonth() ;
        int day = now.getDate();
        int hour = now.getHours();
        int min = now.getMinutes() ;
        int sec = now.getSeconds() ;
        orderBean.setOrderNo(UUID.randomUUID().toString()+"_"+year+month+day+hour+min+sec);
        orderBean.setOrderDate(now);

        User user =(User)session.getAttribute("currUser");
        orderBean.setOrderUser(user);
        orderBean.setOrderMoney(user.getCart().getTotalMoney());
        orderBean.setOrderStatus(0);

        orderService.addOrderBean(orderBean);

        return "index" ;
    }

    //查看订单列表
    public String getOrderList(HttpSession session){
        User user =(User)session.getAttribute("currUser");

        List<OrderBean> orderList = orderService.getOrderList(user);
        user.setOrderList(orderList);

        session.setAttribute("currUser",user);

        return "order/order" ;
    }

    public String getAllOrder(HttpSession session){
        User user =(User)session.getAttribute("currUser");

        List<OrderBean> orderList = orderService.getAllOrder();
        user.setOrderList(orderList);

        session.setAttribute("currUser",user);

        return "manager/order_manager" ;
    }

    public String jsOrderList (HttpSession session){

        User user =(User)session.getAttribute("currUser");
        List<OrderBean> orderList = orderService.getAllOrder();
        user.setOrderList(orderList);

        session.setAttribute("currUser",user);

        Gson gson = new Gson();
        String orderListJsonStr = gson.toJson(orderList);
        return "json:"+orderListJsonStr ;
    }

    public String jsSearchOrder(String keyword){
        List<OrderBean> orderList = orderService.getOrderListByKey(keyword,1);

        Gson gson = new Gson();
        String bookListJsonStr = gson.toJson(orderList);
        return "json:"+bookListJsonStr ;
    }

    public String deleteOrder(Integer orderId){
        orderService.deleteOrder(orderId);
        return "";
    }

    public String jsOrderDetail(HttpSession session,Integer orderNo){
        User user =(User)session.getAttribute("currUser");
        List<OrderBean> orderList = user.getOrderList();

        OrderBean orderBean = orderList.get(orderNo);
        Integer orderId = orderBean.getId();

        List<OrderItem> orderItemList = orderService.getOrderItemList(orderId);

        Gson gson = new Gson();
        String orderItemListJsonStr = gson.toJson(orderItemList);
        return "json:"+orderItemListJsonStr ;
    }
}
