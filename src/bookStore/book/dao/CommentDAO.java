package bookStore.book.dao;

import bookStore.book.pojo.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDAO {
    List<Comment> getCommentList(Integer bookId,Integer pageNo);
    Integer getCommentCount(Integer bookId);

    int addComment(Integer userId, Integer bookId, String content);

    int deleteComment(Integer id);

    int updateLikeCommentNo(Integer commentId, Integer newLikeNum);

}
