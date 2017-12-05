package edu.virginia.cs.vmarketplace.view.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.nosql.CommentsDO;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class CommentsDOLoader extends AsyncTaskLoader<List<CommentsDO>> {
    private DynamoDBMapper mapper;
    private String itemId;
    public CommentsDOLoader(Context context, String itemId){
        super(context);
        mapper = AWSClientFactory.getInstance().getDBMapper();
        this.itemId = itemId;
    }

    @Override
    public List<CommentsDO> loadInBackground() {
        CommentsDO commentsDO = new CommentsDO();
        commentsDO.setItemId(itemId);
        DynamoDBQueryExpression<CommentsDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<CommentsDO>()
                .withIndexName("TIME").withHashKeyValues(commentsDO).withScanIndexForward(false).
                 withLimit(200)
                .withConsistentRead(false);
        return mapper.query(CommentsDO.class,dynamoDBQueryExpression);
    }
}
