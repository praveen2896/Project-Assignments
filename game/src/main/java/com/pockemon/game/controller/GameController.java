package com.pockemon.game.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pockemon.game.utility.ResstUtility;

@RestController
public class GameController {
	
	@Autowired
	private ResstUtility resstUtility;
	
	@GetMapping(value="/getMoves/{name}")
	public String getPowerfulMoves(@PathVariable String name){
		String response=getApi(name);
		return response;		
	}

	private String getApi(String name) {
		String url="http://pokeapi.co/api/v2/pokemon/"+name;		
		try {
			ResponseEntity<String> finalResponse = null;
			Map<String,Integer> finalMap=new HashMap<>();
			List<Integer> list=new ArrayList<>();
			finalResponse = resstUtility.getResponseApi(url);
			if(finalResponse==null)
				return "Invalid Pokemon Character";
			JsonObject jobj = new Gson().fromJson(finalResponse.getBody(), JsonObject.class);
			//System.out.println(jobj.getAsJsonArray("moves"));
			JsonArray jsArr=jobj.getAsJsonArray("moves");
			System.out.println("number of moves : "+jsArr.size());
			finalMap=getMoves(jsArr);
			if(finalMap.size()==0)
				return "no moves for the pokemon "+name;			
			list = new ArrayList<Integer>(finalMap.values());
			//System.out.println("list-->"+list);
			//To remove null values from the List
			list.removeAll(Collections.singleton(null));
			Collections.sort(list);	
			//System.out.println("list-->"+list);	
			String powerMove=getPowerMove(list,finalMap);			
			System.out.println("final Response-->" + powerMove);
			return powerMove;
			
		} catch (Exception ex) {           
           System.out.println(ex.getStackTrace()[0].getLineNumber());
           return "Exception occurs";
        }
	}

	private String getPowerMove(List<Integer> list, Map<String, Integer> finalMap) {
		if(list.size()==0)
		{			
			return finalMap.keySet().toArray()[0].toString();
		}
		else if(list.size()>=1)
		{
			for (Object k : finalMap.keySet()) {						
				  if(finalMap.get(k)==list.get(list.size()-1)) {
					  System.out.println("powerMove among all powers : "+k);
					  System.out.println("power "+list.get(list.size()-1));
					  return k.toString();
			    }
			}
		}
		return null;
	}

	private Map<String, Integer> getMoves(JsonArray jsArr) {
		Map<String,Integer> finalMap=new HashMap<>();
		for(int i = 0; i < jsArr.size(); i++)
		{			
			JsonObject jobj1 = new Gson().fromJson(jsArr.get(i), JsonObject.class);
			ResponseEntity<String> res1=resstUtility.getResponseApi(jobj1.getAsJsonObject("move").get("url").getAsString());
			JsonObject jobj2 = new Gson().fromJson(res1.getBody(), JsonObject.class);				
			finalMap.put(jobj1.getAsJsonObject("move").get("name").getAsString(),jobj2.get("power").isJsonNull()?null:jobj2.get("power").getAsInt());			
		}
		System.out.println("Total number of powers : "+finalMap.size());
		return finalMap;	
	}
	
	
	

}
