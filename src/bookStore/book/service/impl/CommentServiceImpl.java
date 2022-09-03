package bookStore.book.service.impl;

import bookStore.book.dao.CommentDAO;
import bookStore.book.pojo.Comment;
import bookStore.book.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentDAO CommentDAO ;

    @Override
    public List<Comment> getCommentList(Integer bookId,Integer pageNo) {
        return CommentDAO.getCommentList(bookId,pageNo);
    }

    @Override
    public Integer getCommentCount(Integer bookId) {
        return CommentDAO.getCommentCount(bookId);
    }

    @Override
    public int addComment(Integer userId, Integer bookId, String content) {
        return CommentDAO.addComment(userId, bookId, content);
    }

    @Override
    public int deleteComment(Integer commentId) {
        return CommentDAO.deleteComment(commentId);
    }

    @Override
    public Integer updateLikeCommentNo(Integer commentId, Integer newLikeNum) {
        newLikeNum++;
        return CommentDAO.updateLikeCommentNo(commentId,newLikeNum);
    }
}
