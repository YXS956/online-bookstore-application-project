package bookStore.book.pojo;

import java.util.Comparator;
import java.util.Date;

public class Comment {
    private Integer commentId;
    private Integer userId;
    private String userName;
    private Integer bookId;
    private String commentWord;
    private Date commentTime;
    private Integer likeNum;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {        return userName;    }

    public void setUserName(String userName) {        this.userName = userName;    }

    public Integer getbookId() {
        return bookId;
    }

    public void setbookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getCommentWord() {
        return commentWord;
    }

    public void setCommentWord(String commentWord) {
        this.commentWord = commentWord;
    }
    public Date getCommentTime() {        return commentTime;    }

    public void setCommentTime(Date commTime) {        this.commentTime = commTime;    }

    public Integer getLikeNum() {        return likeNum;    }

    public void setLikeNum(Integer likeNum) {        this.likeNum = likeNum;    }

//    @Override
//    public int compare(Object o1, Object o2) {
//        Comment oComment1 = (Comment)o1;
//        Comment oComment2 = (Comment)o2;
//        if(oComment1.getCommentTime().before(oComment2.getCommentTime())){
//            return -1;
//        }else if(oComment1.getCommentTime().after(oComment2.getCommentTime())){
//            return 1;
//        }
//        return 0;
//    }
}
