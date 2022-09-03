package bookStore.book.controller;

import bookStore.book.pojo.*;
import bookStore.book.service.BookService;
import bookStore.book.service.CommentService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CommentController {
    private CommentService commentService;
    private BookService bookService;

    /**
     * 初加载页面时需要的操作，比如在主界面点击书图片会执行此getcomment方法
     **/
    public String getComment(HttpSession session, HttpServletRequest request){
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int pageNo = 1;
        //int pageNo = (int) session.getAttribute("pageNo");
        User user =(User)session.getAttribute("currUser");
        Integer userId = user.getId();

        List<Comment> commentList = commentService.getCommentList(bookId,pageNo);
        commentList.sort((o1,o2)->{
            if (o1.getCommentTime().before(o2.getCommentTime())) {
                return 1;
            } else if (o1.getCommentTime().after(o2.getCommentTime())) {
                return -1;
            }
            return 0;
        });
        List<Book> bookList = bookService.getBookListById(bookId);
        Book currentBook = bookList.get(0);

        session.setAttribute("bookList",bookList);
        session.setAttribute("currBook",currentBook);
        session.setAttribute("commentList",commentList);

        bookService.updateHistory(userId,bookId);

        return "book/detail";
        //此处返回的页面地址栏显示的是comment.do，因为没有采用redirect：uml
    }

    /**
     * get commentList through vue function
    **/
    public String jsGetComment(HttpSession session){
        Book currbook = (Book) session.getAttribute("currBook");
        int bookId = currbook.getId();
        int pageNo = 1;
        //int pageNo = (int) session.getAttribute("pageNo");

        List<Comment> commentList = commentService.getCommentList(bookId,pageNo);
        commentList.sort((o1,o2)->{
            if (o1.getCommentTime().before(o2.getCommentTime())) {
                return 1;
            } else if (o1.getCommentTime().after(o2.getCommentTime())) {
                return -1;
            }
            return 0;
        });

        Gson gson = new Gson();
        String commentJsonStr = gson.toJson(commentList);
        return "json:"+commentJsonStr;
        //different from the return value in method above
    }

    public String sortByTime(Integer bookId){
        List<Comment> commentList = commentService.getCommentList(bookId,1);
        commentList.sort((o1,o2)->{
            if (o1.getCommentTime().before(o2.getCommentTime())) {
                return 1;
            } else if (o1.getCommentTime().after(o2.getCommentTime())) {
                return -1;
            }
            return 0;
        });
        Gson gson = new Gson();
        String commentListJsonStr = gson.toJson(commentList);
        return "json:"+commentListJsonStr ;
    }

    public String sortByRank(Integer bookId){
        List<Comment> commentList = commentService.getCommentList(bookId,1);
        commentList.sort((o1,o2)->{
            if (o1.getLikeNum() >= o2.getLikeNum()) {
                return 1;
            } else if (o1.getLikeNum() < o2.getLikeNum()) {
                return -1;
            }
            return 0;
        });
        Gson gson = new Gson();
        String commentListJsonStr = gson.toJson(commentList);
        return "json:"+commentListJsonStr ;
    }

    public String updateCommentLikeNo(Integer commentId, Integer newLikeNum){
        commentService.updateLikeCommentNo(commentId,newLikeNum);
        return "";
    }

    public String addComment(String content,Integer bookId,HttpSession session) {
        User user =(User)session.getAttribute("currUser");
        Integer userId = user.getId();
        commentService.addComment(userId,bookId,content);
        return "redirect:comment.do?operate=getComment&bookId="+bookId;
    }

    public String deleteComment(Integer commentId){
        commentService.deleteComment(commentId);
        return "";
    }

}
