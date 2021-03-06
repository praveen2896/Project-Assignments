package com.pockemon.game.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResstUtility {
	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseEntity<String> getResponseApi(String url){
		//System.out.println("url to hit "+url);	
		HttpHeaders headers = new HttpHeaders();	
		ResponseEntity<String> finalResponse=null;
		ResponseEntity<String> response1=null;
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "spring rest api");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers); 
        try {
        response1 = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
        //System.out.println(response1.getStatusCodeValue());
        if(response1.getStatusCodeValue()==301)
        {
     	   finalResponse=restTemplate.exchange(response1.getHeaders().getLocation(), HttpMethod.GET,entity,String.class);
        }   else {
        	finalResponse=response1;
        }
        }
        catch(Exception e)
        {
        	System.out.println("Exception"+e.getMessage());
            System.out.println("Ex"+e.getCause());
        	finalResponse=null;
        }
        return finalResponse;
	}

}
