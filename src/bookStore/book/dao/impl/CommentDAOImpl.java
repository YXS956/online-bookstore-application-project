package bookStore.book.dao.impl;

import bookStore.book.dao.CommentDAO;
import bookStore.book.pojo.Comment;
import bookStore.mybatis.basedao.BaseDAO;

import java.util.List;

public class CommentDAOImpl extends BaseDAO<Comment> implements CommentDAO {
    @Override
    public List<Comment> getCommentList(Integer bookId,Integer pageNo) {
        return executeQuery("select commentId,userId,uname as 'userName',bookId,commentWord,commentTime,likeNum from t_comment join t_user where bookID = ? and t_comment.userId=t_user.id LIMIT ?,5",bookId,(pageNo-1)*5);
    }

    @Override
    public Integer getCommentCount(Integer bookId) {
        return ((Long) super.executeComplexQuery("select count(*) from t_comment where bookID = ? LIMIT ?,3",bookId)[0]).intValue();
    }

    @Override
    public int addComment(Integer userId, Integer bookId, String content) {
        return executeUpdate("INSERT INTO t_comment (userId,bookId,commentWord,commentTime,likeNum) VALUES (?,?,?,NOW(),0)",userId,bookId,content);
    }

    @Override
    public int deleteComment(Integer id){
        return executeUpdate("delete from t_comment where commentId=?",id);
    }

    @Override
    public int updateLikeCommentNo(Integer commentId, Integer newLikeNum) {
        return executeUpdate("update t_comment SET likeNum = ? where commentId = ?",newLikeNum,commentId);
    }

}
