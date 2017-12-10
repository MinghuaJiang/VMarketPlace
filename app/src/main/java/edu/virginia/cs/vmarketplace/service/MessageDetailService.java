package edu.virginia.cs.vmarketplace.service;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.MessageDetailDO;
import edu.virginia.cs.vmarketplace.service.dao.MessageDetailDao;

/**
 * Created by cutehuazai on 12/9/17.
 */

public class MessageDetailService {
    private static MessageDetailService service = new MessageDetailService();
    private MessageDetailDao messageDetailDao;
    private MessageDetailService(){
        messageDetailDao = new MessageDetailDao();
    }

    public static MessageDetailService getInstance(){
        return service;
    }

    public void insertOrUpdate(MessageDetailDO messageDetailDO){
        messageDetailDao.insertOrUpdate(messageDetailDO);
    }

    public void delete(MessageDetailDO messageDetailDO){
        messageDetailDao.delete(messageDetailDO);
    }

    public List<MessageDetailDO> getMessages(String messageThreadId){
       return messageDetailDao.getMessages(messageThreadId);
    }
}
