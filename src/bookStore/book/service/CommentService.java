package bookStore.book.service;

import bookStore.book.pojo.Comment;

import java.util.Date;
import java.util.List;

public interface CommentService {
    List<Comment> getCommentList(Integer bookId,Integer pageNo);
    Integer getCommentCount(Integer bookId);

    int addComment(Integer userId, Integer bookId, String content);

    int deleteComment(Integer commentId);

    Integer updateLikeCommentNo(Integer commentId, Integer newLikeNum);
}
