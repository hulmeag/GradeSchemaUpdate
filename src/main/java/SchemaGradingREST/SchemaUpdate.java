package SchemaGradingREST;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableServer.CurrentOperations;abstract




public class SchemaUpdate {

	public static void main(String[] args) throws IOException, URISyntaxException {
		// TODO Auto-generated method stub

		
		System.out.println("Enter File name: ");
		Scanner in = new Scanner(System.in);
		String Filename = in.nextLine();
		System.out.println("Enter User: ");
		String User = in.nextLine();
		System.out.println("Enter Secret : ");
		String Pass = in.nextLine();
		System.out.println("Enter URL (https://ultra-magic.blackboard.com/):  ");
		String tokenurl = in.nextLine();
		System.out.println("Course to copy Grade Schema From:  ");
	    String origincourse = in.nextLine();
//			
//		
	
		//  Get Course List From file
		
		File csvFile = new File(Filename);
		
		
		//Get Orign Schema Object
		
		
		JSONObject  orignjson = GetShema(tokenurl, origincourse, GetToken(User, Pass, tokenurl));
		System.out.println("Origin Course JSON: "+orignjson.toString());
		
		
		
		//Test Replace
//		orignjson.put("id", "123");
//		
//		System.out.println("Modifed Course JSON: "+orignjson.toString());
		
		//Get the new courses Schema from file to get ther ID to update.  Then update with orginal schema. 
		
		
		
		if (csvFile.isFile()) {
		    // create BufferedReader and read data from csv
			String row = "";
			BufferedReader csvReader = new BufferedReader(new FileReader(Filename));
			while ((row = csvReader.readLine()) != null) {
			    String[] coursedata = row.split(",");
			    
			    // Course list loaded, Itterate though it
			    
			    for( int i = 0; i <= coursedata.length - 1; i++)
			    {
			    	
			    	//Get Token and do stuff
			        String Tokentext = GetToken(User, Pass, tokenurl);
					System.out.println("Using Token: "+Tokentext);
					
					//  Get the Schema for the course
					JSONObject currentSchema =  GetShema(tokenurl, coursedata[i], Tokentext);
					orignjson.put("id",currentSchema.get("id"));
					PatchSchema(coursedata[i], tokenurl, Tokentext, orignjson, currentSchema.get("id").toString());
					 
					 
					
					
			    	
			    	
			    }
			    
			    
			    
			    
			    
			    
			}
			csvReader.close();
			
		} else {
			//File name is not valid so quit
			System.out.println("File name or path is invalid, quiting: ");
			System.exit(0);
			
		}
	
		
		
		

		
		
		
		
		
		

	}
	
	//Patch the Schema to update it
	
	
	public static void PatchSchema(String courseId, String VIP, String token, JSONObject  orignjson, String Schmeaid) throws URISyntaxException {
		
		
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		
		
	
		URI uri = null;
		uri = new URI(VIP+"learn/api/public/v1/courses/courseId:"+courseId+"/gradebook/schemas/"+Schmeaid );
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "merge-patch+json");
		headers.setContentType(mediaType);
		headers.add("Authorization", "Bearer " + token);
		headers.set("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity <String> httpEntity = new HttpEntity <String> (orignjson.toString(), headers);
		
		
		ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.PATCH, httpEntity, String.class);
		System.out.println("Patch Course "+courseId+" Updating JSON to: "+res.getBody());
		
	}
	
	public static JSONObject GetShema(String VIP, String courseId, String token) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = null;
				
		uri = new URI(VIP+"learn/api/public/v1/courses/courseId:"+courseId+"/gradebook/schemas" );
		System.out.println(uri.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.set("Accept", "application/json");
		
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		JSONObject jsonObj = new JSONObject(res.getBody());
		JSONObject orignjson = new JSONObject();
		//System.out.println(res.getBody());
		
		
        JSONArray jsonArray = (JSONArray) jsonObj.get("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = (JSONObject) jsonArray.get(i);
            //System.out.println("DEBUG " + jsonobject.get("scaleType"));
            if (jsonobject.get("scaleType").toString().equals("Tabular") ) {
            	System.out.println("Getting current Schema for course: "+courseId+" JSON Record " + jsonobject.toString());
            	orignjson = jsonobject;
            	
            }
          
        }
		
		return orignjson;
		

		
	}
	
	
	public static String exmapleJSON() {
		
		
		return "{\\r\\n\\t\\\"id\\\": \\\"_271039_1\\\",\\r\\n\\t\\\"title\\\": \\\"CGS Display\\\",\\r\\n\\t\\\"scaleType\\\": \\\"Tabular\\\",\\r\\n\\t\\\"symbols\\\": [{\\r\\n\\t\\t\\t\\\"text\\\": \\\"A1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 99.98900,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 99.97728,\\r\\n\\t\\t\\t\\\"upperBound\\\": 100.00000\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"A2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 95.45500,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 95.43182,\\r\\n\\t\\t\\t\\\"upperBound\\\": 99.97728\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"A3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 90.90900,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 90.88637,\\r\\n\\t\\t\\t\\\"upperBound\\\": 95.43182\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"A4\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 86.36400,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 86.34091,\\r\\n\\t\\t\\t\\\"upperBound\\\": 90.88637\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"A5\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 81.81800,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 81.79546,\\r\\n\\t\\t\\t\\\"upperBound\\\": 86.34091\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"B1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 77.27300,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 77.25000,\\r\\n\\t\\t\\t\\\"upperBound\\\": 81.79546\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"B2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 72.72700,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 72.70455,\\r\\n\\t\\t\\t\\\"upperBound\\\": 77.25000\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"B3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 68.18200,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 68.15910,\\r\\n\\t\\t\\t\\\"upperBound\\\": 72.70455\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"C1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 63.63600,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 63.61364,\\r\\n\\t\\t\\t\\\"upperBound\\\": 68.15910\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"C2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 59.09100,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 59.06819,\\r\\n\\t\\t\\t\\\"upperBound\\\": 63.61364\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"C3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 55.65900,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 54.52273,\\r\\n\\t\\t\\t\\\"upperBound\\\": 59.06819\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"D1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 50.00000,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 49.97728,\\r\\n\\t\\t\\t\\\"upperBound\\\": 54.52273\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"D2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 45.45500,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 45.43182,\\r\\n\\t\\t\\t\\\"upperBound\\\": 49.97728\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"D3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 43.15900,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 40.88637,\\r\\n\\t\\t\\t\\\"upperBound\\\": 45.43182\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"E1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 36.36400,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 36.34091,\\r\\n\\t\\t\\t\\\"upperBound\\\": 40.88637\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"E2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 31.81800,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 31.79546,\\r\\n\\t\\t\\t\\\"upperBound\\\": 36.34091\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"E3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 27.27300,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 27.25000,\\r\\n\\t\\t\\t\\\"upperBound\\\": 31.79546\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"F1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 22.72700,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 22.70455,\\r\\n\\t\\t\\t\\\"upperBound\\\": 27.25000\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"F2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 18.18200,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 18.15910,\\r\\n\\t\\t\\t\\\"upperBound\\\": 22.70455\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"F3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 13.63600,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 13.61364,\\r\\n\\t\\t\\t\\\"upperBound\\\": 18.15910\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"G1\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 11.34100,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 9.06819,\\r\\n\\t\\t\\t\\\"upperBound\\\": 13.61364\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"G2\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 4.54500,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 4.52273,\\r\\n\\t\\t\\t\\\"upperBound\\\": 9.06819\\r\\n\\t\\t},\\r\\n\\t\\t{\\r\\n\\t\\t\\t\\\"text\\\": \\\"G3\\\",\\r\\n\\t\\t\\t\\\"absoluteValue\\\": 0.00000,\\r\\n\\t\\t\\t\\\"lowerBound\\\": 0.00000,\\r\\n\\t\\t\\t\\\"upperBound\\\": 4.52273\\r\\n\\t\\t}\\r\\n\\t]\\r\\n}";
		
	}
	
	public static String GetToken(String user, String pass, String VIP){
		
		
		RestTemplate restTemplate = new RestTemplate();
        
        URI uri = null;
		try {
			uri = new URI(VIP+"learn/api/public/v1/oauth2/token" );
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		HttpHeaders headers = new HttpHeaders();
		
		//Hash key and password
		String hashable = user+":"+pass;
		String hash = Base64.getEncoder().encodeToString(hashable.getBytes());
		//Add headers
		headers.add("Authorization", "Basic " + hash);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<String> request = new HttpEntity<String>("grant_type=client_credentials",headers);
		//Send responce to token class to sort	
		//System.out.println(uri+request.toString());
		ResponseEntity<Token> response = restTemplate.exchange(uri, HttpMethod.POST, request, Token.class);
		Token token = response.getBody();
		       

		
        return token.getToken();
                

		
	}

}

//Parse responce JSON to get the Token
@JsonIgnoreProperties(ignoreUnknown = true)
class Token {

@JsonProperty("access_token")
private String access_token;
@JsonProperty("token_type")
private String token_type;
@JsonProperty("expires_in")
private String expires_in;
@JsonProperty("id")
private String id;

public Token() {
}

public String getToken() {
return access_token;
}

public void setToken(String access_token) {
this.access_token = access_token;
}




@Override
public String toString() {
return "Token{" +
        "access_token=" + access_token  +'}';
}	
	

}
