package edu.virginia.cs.vmarketplace.service;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.CommentsDO;
import edu.virginia.cs.vmarketplace.service.dao.CommentsDao;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class CommentService {
    private CommentsDao dao;
    private CommentService(){
        this.dao = new CommentsDao();
    }

    private static CommentService service = new CommentService();

    public static CommentService getInstance(){
        return service;
    }

    public List<CommentsDO> findCommentsByItemId(String itemId){
        return dao.findCommentsByItemId(itemId);
    }

    public void deleteComments(CommentsDO commentsDO){
        dao.deleteComment(commentsDO);
    }
}
