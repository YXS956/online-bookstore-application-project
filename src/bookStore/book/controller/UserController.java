package bookStore.book.controller;

import bookStore.book.pojo.Cart;
import bookStore.book.pojo.User;
import bookStore.book.service.CartItemService;
import bookStore.book.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.util.Objects;

public class UserController {

    private UserService userService ;
    private CartItemService cartItemService ;
    private UserController userController;

    public String login(String uname , String pwd , HttpSession session){

        User user = userService.login(uname, pwd);
        if(user!=null){
            Cart cart = cartItemService.getCart(user);
            user.setCart(cart);
            session.setAttribute("currUser",user);

            //session.setAttribute("currRole",user.getRole());

            session.setAttribute("login","Y");
            return "redirect:book.do";
        }
        session.setAttribute("login","N");
        return "user/login";
    }

    public String regist(String verifyCode , String uname , String pwd , String email , HttpSession session , HttpServletResponse response) throws IOException {
        Object kaptchaCodeObj = session.getAttribute("KAPTCHA_SESSION_KEY");
        if(kaptchaCodeObj==null || !verifyCode.equals(kaptchaCodeObj)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            //out.println("<script language='javascript'>alert('验证码不正确！');window.location.href='page.do?operate=page&page=user/regist';</script>");
            out.println("<script language='javascript'>alert('Verifycode is not correct！');</script>");
            //return "user/regist";
            return "user/regist";
        }else{
            if(verifyCode.equals(kaptchaCodeObj)){
                userService.regist(new User(uname , pwd , email,0));
                return "user/login";
            }
        }
        return "user/login";
    }

    public String ckUname(String uname){
        User user = userService.getUser(uname);
        if(user!=null){
            //not available for registration
            return "json:{'uname':'1'}";
            //return "ajax:1";
        }else{
            //available for registration
            return "json:{'uname':'0'}";
            //return "ajax:0";
        }
    }

    public String modifyUser(Integer userId,String uname,String pwd,String email) throws NoSuchMethodException, ParseException {
        Method[] methods = userController.getClass().getDeclaredMethods();

        for(Method method : methods){
            if("modifyUser".equals(method.getName())){
                Parameter[] parameters = method.getParameters();
                String[] parameterValues = new String[parameters.length-1];
                parameterValues[0]=uname;
                parameterValues[1]=pwd;
                parameterValues[2]=email;

                for (int i = 1; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String parameterName = parameter.getName();
                    if(!Objects.equals(parameterValues[i - 1], "")){
                        userService.updateUserColumn(userId,parameterName,parameterValues[i-1]);
                    }
                }
            }
        }
        return "redirect:book.do?";
    }
}
