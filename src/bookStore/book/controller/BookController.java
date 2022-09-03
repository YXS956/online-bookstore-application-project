package bookStore.book.controller;

import bookStore.book.pojo.Book;
import bookStore.book.pojo.Cart;
import bookStore.book.pojo.User;
import bookStore.book.service.BookService;
import bookStore.mybatis.util.StringUtil;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookController {

    private BookService bookService ;
    private BookController bookController;

    //index的含义为如果没有operate的情况下，就走index默认方法，见dispatcher里的if(StringUtil.isEmpty(operate)){
    //            operate = "index" ;
    //        }
    public String index(HttpSession session, HttpServletRequest request){
        List<Book> bookList;
        int bookCount = 0;

        String operate = request.getParameter("ope");
        //get bookList by language
        if(Objects.equals(operate, "language")){
            getLanguageBook(session, request);
            return "index";
        }
        String sessionkeyWord = (String) session.getAttribute("keyWord");
        String keyWord;
        keyWord = request.getParameter("keyWord");
        if(StringUtil.isEmpty(keyWord)){
            keyWord = "";
        }
        if(StringUtil.isEmpty(sessionkeyWord)){
        session.setAttribute("keyWord",keyWord);
        }
        //如果关键字不为空或为空,并且是通过点击查询按钮发来的
        if(StringUtil.isNotEmpty(operate) && "keySearch".equals(operate)){
            if("Bestselling".equals(keyWord)){
                bookList = bookService.getBestSellBookList(1);
            }else{
            bookList = bookService.getBookListByKey(keyWord, 1);
            bookCount = bookService.getBookCount(keyWord);
            session.setAttribute("keyWord",keyWord);
            }
        }else if(StringUtil.isNotEmpty(operate) && "priceSearch".equals(operate)){
            double maxPrice = Double.parseDouble(request.getParameter("maxPrice"));
            double minPrice = Double.parseDouble(request.getParameter("minPrice"));

            //get books based on price, keyword and pageNo
            int pageNo = getPageNo(request);
            bookList = bookService.getBookListByKeyPrice(keyWord,minPrice,maxPrice,pageNo);
            bookCount = bookService.getBookCount(keyWord,minPrice,maxPrice);

            //modify 5 current displaying books with price limits
            //bookList = (List<Book>) session.getAttribute("bookList");
            //for(int i=0;i<bookList.size();i++){
            //    Book bookObj = bookList.get(i);
            //    if(bookObj.getPrice()>maxPrice || bookObj.getPrice()<minPrice) {
            //        bookList.remove(bookObj);
            //        i--;
            //     }}
            session.setAttribute("minPrice",minPrice);
            session.setAttribute("maxPrice",maxPrice);
        }
        else{
            //click icon to refresh to initial state which equals to search with ""
            if(StringUtil.isNotEmpty(operate) && "refre".equals(operate)){
                session.setAttribute("keyWord","");
            }
            //此处为点击下一页或上一页或直接输入网址关键字获得。此时keyword应该通过session作用域获得
            Object keyWordObj = session.getAttribute("keyWord");
            if(keyWordObj!=null){
                keyWord = (String) keyWordObj;
            }else {
                keyWord = "";
            }
            if("Bestselling".equals(keyWord)){
                bookList = bookService.getBestSellBookList(getPageNo(request));
            }else{
                bookList = bookService.getBookListByKey(keyWord,getPageNo(request));
            }
            //书总数的页数
            bookCount = bookService.getBookCount(keyWord);

        }

        session.setAttribute("bookCount",bookCount);
        session.setAttribute("bookList",bookList);
        session.setAttribute("pageNo",getPageNo(request));

        int pageCount = (bookCount + 5 - 1) / 5;
        // 5 books in a list
        session.setAttribute("pageCount",pageCount);

        return "index";
    }

    public Integer getPageNo(HttpServletRequest request) {
        Integer pageNo;
        String pageNoStr = request.getParameter("pageNo");
        if(StringUtil.isNotEmpty(pageNoStr)){
            pageNo = Integer.parseInt(pageNoStr);
            return pageNo;
        }
        else{
            return 1;
        }
    }

    public void getLanguageBook(HttpSession session, HttpServletRequest request) {
        String language = request.getParameter("language");
        List<Book> bookList = bookService.getBookListByLan(language, getPageNo(request));
        Integer bookCount = bookService.getBookCount(language);
    }

    public String getBook(){

        List<Book> bookList = bookService.getAllBook();

        Gson gson = new Gson();
        String bookListJsonStr = gson.toJson(bookList);
        return "json:"+bookListJsonStr ;
    }

    public String getRandomBookList(){
        Random rd = new Random();
        Integer totalCount = bookService.getTotalBookCount();

        List<Integer> placeList = new ArrayList<>();
        while(placeList.size() < 10) {
            int num = rd.nextInt(totalCount);
            if (!placeList.contains(num) && num != 0) {
                placeList.add(num);
            }
        }

        List<Book> randomBookList = bookService.getRandomBookList(placeList);

        Gson gson = new Gson();
        String randomBookListJsonStr = gson.toJson(randomBookList);
        return "json:"+randomBookListJsonStr ;
    }

    public String getHistoryBookList(HttpSession session){
        List<Book> historyBookList;
        Object sessionUser = session.getAttribute("currUser");
        if(sessionUser == null) {
            historyBookList = null;
        }else{
            User user = (User) sessionUser;

            Integer userId = user.getId();

            historyBookList = bookService.getBookListHistory(userId);
        }

        Gson gson = new Gson();
        String historyBookListJsonStr = gson.toJson(historyBookList);
        return "json:"+historyBookListJsonStr;
    }

    public String updateHistory(HttpSession session, Integer bookId){

        Object sessionUser = session.getAttribute("currUser");
        if(sessionUser != null) {
            User user =(User)sessionUser;

            Integer userId = user.getId();

            if(bookId != null){
                bookService.updateHistory(userId,bookId);
            }
        }

        return "";
    }

    public String jsSearchBook(String keyword){
        List<Book> bookList = bookService.getBookListByKey(keyword,1);

        Gson gson = new Gson();
        String bookListJsonStr = gson.toJson(bookList);
        return "json:"+bookListJsonStr ;
    }

    public String deleteBook(Integer bookId){
        bookService.deleteBook(bookId);
        return "";
    }

    public String modifyBook(Integer bookId,String bookName,Integer price,Integer eprice,Integer paperdisPrice,Integer edisPrice,String author,Integer saleCount,Integer bookCount,String bookStatus, String description,String category,String format,String language,String dimensions,String editStat,String publicDate,String ISBN,String publisher,String publicCiCon) throws NoSuchMethodException, ParseException {
        Method[] methods = bookController.getClass().getDeclaredMethods();

        for(Method method : methods){
            if("modifyBook".equals(method.getName())){
                Parameter[] parameters = method.getParameters();
                String[] parameterValues = new String[parameters.length-1];
                parameterValues[0]=bookName;
                parameterValues[1]=nullTo(price).toString();
                parameterValues[2]=nullTo(eprice).toString();
                parameterValues[3]=nullTo(paperdisPrice).toString();
                parameterValues[4]=nullTo(edisPrice).toString();
                parameterValues[5]=author;
                parameterValues[6]=nullTo(saleCount).toString();
                parameterValues[7]=nullTo(bookCount).toString();
                parameterValues[8]=bookStatus;
                parameterValues[9]=description;
                parameterValues[10]=category;
                parameterValues[11]=format;
                parameterValues[12]=language;
                parameterValues[13]=dimensions;
                parameterValues[14]=editStat;
                parameterValues[15]=dateTransform(publicDate);
                parameterValues[16]=ISBN;
                parameterValues[17]=publisher;
                parameterValues[18]=publicCiCon;
                for (int i = 1; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String parameterName = parameter.getName();
                    if(!Objects.equals(parameterValues[i - 1], "") && !Objects.equals(parameterValues[i - 1], null)){
                        if(bookId == null){
                            bookId = bookService.createBook();
                        }
                        bookService.updateBookColumn(bookId,parameterName,parameterValues[i-1]);
                    }
                }
            }
        }
        return "redirect:page.do?operate=page&page=manager/book_manager";
    }
    public Object nullTo(Integer num){
        if(num == null){
            return "";
        }
        return num;
    }

    public String dateTransform(String timeString) throws ParseException {
        if(!Objects.equals(timeString, "") && timeString!=null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date d1= formatter.parse(timeString);
            String[] d2 = d1.toString().split(" ");
            return d2[2]+" "+d2[1]+" "+d2[5];
        }
        return "";
    }



//   public String changePage(HttpSession session, HttpServletRequest request) {
//        List<Book> bookList = bookService.getBookList(getPageNo(request));
//        session.setAttribute("bookList",bookList);
//        session.setAttribute("pageNo",getPageNo(request));
//        return "index";
//    }
}
