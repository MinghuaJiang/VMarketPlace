package edu.virginia.cs.vmarketplace.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.virginia.cs.vmarketplace.model.MessageDO;
import edu.virginia.cs.vmarketplace.service.dao.MessageDao;

/**
 * Created by cutehuazai on 12/9/17.
 */

public class MessageService {
    private static MessageService service = new MessageService();
    private MessageDao messageDao;
    private MessageService(){
        messageDao = new MessageDao();
    }

    public static MessageService getInstance(){
        return service;
    }

    public void insertOrUpdate(MessageDO messageDO){
        messageDao.insertOrUpdate(messageDO);
    }

    public void delete(MessageDO messageDO){
        messageDao.delete(messageDO);
    }

    public List<MessageDO> findMessagesOrderByLastUpdatedTime(String userId){
        List<MessageDO> buyerList = messageDao.getMessagesAsBuyer(userId);
        List<MessageDO> sellerList = messageDao.getMessagesAsSeller(userId);
        List<MessageDO> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while(i < buyerList.size() && j < sellerList.size()){
            if(buyerList.get(i).getLastUpdateTime().compareTo(sellerList.get(j).getLastUpdateTime()) > 0){
                result.add(buyerList.get(i));
                i++;
            }else{
                result.add(sellerList.get(j));
                j++;
            }
        }
        if(i == buyerList.size()){
            for(int index = j; index < sellerList.size();index++){
                result.add(sellerList.get(index));
            }
        }else{
            for(int index = i; index < buyerList.size();index++){
                result.add(buyerList.get(index));
            }
        }
        return result;
    }

    public List<MessageDO> getMessageThread(String[] ids){
        return messageDao.getMessageThreadByItemIdAndBuyerId(ids[0],ids[1]);
    }
}
