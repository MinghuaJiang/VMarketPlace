package edu.virginia.cs.vmarketplace.service.client;

import android.content.Context;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfigurable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import edu.virginia.cs.vmarketplace.view.login.CustomizedSignInUI;


/**
 * Created by cutehuazai on 11/27/17.
 */

public class AWSClientFactory {
    private static AWSClientFactory factory = new AWSClientFactory();

    private AWSClientFactory(){

    }

    public static AWSClientFactory getInstance(){
        return factory;
    }

    public AWSConfigurable getSignInUI(Context context){
        return AWSMobileClient.getInstance().getClient(context, CustomizedSignInUI.class);
    }

    public DynamoDBMapper getDBMapper(){
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        return DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
    }

    public TransferUtility getTransferUtility(Context context){
        AmazonS3 s3 = new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider());
        TransferUtility transferUtility =
                TransferUtility.builder().s3Client(s3)
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .build();

        return transferUtility;
    }

    public PinpointManager getPinpointManager(Context context){
        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                context,
                AWSMobileClient.getInstance().getCredentialsProvider(),
                AWSMobileClient.getInstance().getConfiguration());

        return new PinpointManager(pinpointConfig);
    }


}
