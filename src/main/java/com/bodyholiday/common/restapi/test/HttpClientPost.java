package com.bodyholiday.common.restapi.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/*
 Maven : 
 <dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.5.10</version>
</dependency>
 */

public class HttpClientPost {
	public static void main(String[] args) {

        try {
            String result;
			try {
				result = sendPOST("http://localhost:8080/sns/notification/publish");
				 System.out.println("Result : "+result);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String sendPOST(String url) throws IOException, URISyntaxException {

        String result = "";
      //add the http parameters you wish to pass
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("snstopic", "arn:aws:sns:us-east-1:150103037720:notification-bh-staging"));

        //Build the server URI together with the parameters you wish to pass
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameters(urlParameters);
        
        HttpPost post = new HttpPost(uriBuilder.build());
        
        String inputJson = "{\r\n" + 
        		"    \"event_name\": \"Suite Request\",\r\n" + 
        		"    \"isActive\": \"true\",\r\n" + 
        		"    \"lastUpdated_by\": \"SaumenD\",\r\n" + 
        		"    \"lastUpdated_date\": \"2020-07-17 20:18:32.0\",\r\n" + 
        		"    \"message_template\": {\r\n" + 
        		"        \"email_subject\": \"Email Notification template\",\r\n" + 
        		"        \"email_template\": \"Dear Mr Smith, Thank you for completing your WebRoom registration! You may now go ahead and schedule your activities and services. Thanks! BodyHoliday Team\"\r\n" + 
        		"    },\r\n" + 
        		"    \"notification_name\": \"Email\",\r\n" + 
        		"    \"notification_type\": \"Request Acknowledge\",\r\n" + 
        		"    \"propertyCode\": \"1001\",\r\n" + 
        		"    \"routing_details\": {\r\n" + 
        		"        \"delivery_mode\": \"Email\",\r\n" + 
        		"        \"delivery_type\": \"Immediate\",\r\n" + 
        		"        \"isActive\": \"true\",\r\n" + 
        		"        \"recipient_role\": \"Staff\",\r\n" + 
        		"        \"recipient_type\": \"Staff\"\r\n" + 
        		"    }\r\n" + 
        		"}";
        		 
		 StringEntity stringEntity = new StringEntity(inputJson);
		 post.setEntity(stringEntity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)){
            result = EntityUtils.toString(response.getEntity());
        }
        
        return result;
    }
}
