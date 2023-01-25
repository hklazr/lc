package com.project.lovecalculator.service;

import java.io.IOException;
import java.io.InputStream;

import java.net.http.HttpRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.lovecalculator.model.Person;


@Service
public class LoveService {

    private static final String LOVE_CALCULATOR_URL 
                        = "https://love-calculator.p.rapidapi.com/getPercentage";
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public Optional<Person> calcCompatibility(String person1, String person2) 
        throws IOException{

        // String apiKey = "42e96f8adfmshdb0fa985e21f81cp1ff8fdjsn287a8806ed4f";
     

        String loveCalURL = UriComponentsBuilder
                       .fromUriString(LOVE_CALCULATOR_URL)
                       .queryParam("sname", "person1")
                       .queryParam("fname", "person2")
                    //    .queryParam("X-RapidAPI-Key", "42e96f8adfmshdb0fa985e21f81cp1ff8fdjsn287a8806ed4f")
		            //    .queryParam("X-RapidAPI-Host", "love-calculator.p.rapidapi.com")
                       .toUriString();
                    //    RequestEntity<String> request = RequestEntity
                    //         .post(loveCalURL)
                    //         .contentType(MediaType.APPLICATION_JSON)
                    //         .headers("Accept", MediaType.APPLICATION_JSON)
                    //         .build();


                    System.out.println(loveCalURL);
                    
                    RestTemplate template = new RestTemplate();
                    ResponseEntity<String> resp = null;
                    
                    HttpHeaders headers = new HttpHeaders();
                    String loverApiKey = "42e96f8adfmshdb0fa985e21f81cp1ff8fdjsn287a8806ed4f";
                    // String loverApiKey = System.getenv("LOVER_API_KEY");
                    String loverApiHost = "love-calculator.p.rapidapi.com";
                    // String loverApiHost = System.getenv("LOVER_API_HOST");
                    
                    //entity must be passed in
                    headers.set("X-RapidAPI-Key", loverApiKey);
                    headers.set("X-RapidAPI-Host", loverApiHost);
                    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                    resp = template.exchange(loveCalURL,HttpMethod.GET, 
                                requestEntity, String.class);
                    System.out.println(resp);

                    Person p = Person.create(resp.getBody());
                    // p.id is still null
                    System.out.println(p); 

                    if(p != null){
                        redisTemplate.opsForValue().set(p.getId(), resp.getBody().toString());
                        return Optional.of(p); 
                    }
                                               
                    return Optional.empty();
                }

                public Person[] getAllMatchMaking() throws IOException {
                    Set<String> allMatchMakingdKeys = redisTemplate.keys("*");
                    List<Person> mArr = new LinkedList<Person>();
                    for (String matchMakeKey : allMatchMakingdKeys) {
                        String result = (String) redisTemplate.opsForValue().get(matchMakeKey);
            
                        mArr.add((Person) Person.create(result));
                    }
            
                    return mArr.toArray(new Person[mArr.size()]);
                }
            
            }
            
                    //    RestTemplate template = new RestTemplate();
                    //    ResponseEntity<String> response = template.exchange(request, String.class);
                    //    try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
                    //     JsonReader reader = Json.createReader();
                    //     JsonObject data = reader.readObject();
                    //     }

                
                //    RestTemplate template = new RestTemplate();
                //    ResponseEntity<String> resp = null;
                //    resp = template.getForEntity(weatherUrl,String.class);
                //    System.out.println(resp);
                //    Person p = Person.create(resp.getBody());
                //    System.out.println(w);
                //    if(w != null)
                //        return Optional.of(w);                        
                //    return Optional.empty();

    
    // public void testHeader(final RestTemplate restTemplate){
    //     //Set the headers you need send
    //     final HttpHeaders headers = new HttpHeaders();
    //     headers.set("User-Agent", "eltabo");

    //     //Create a new HttpEntity
    //     final HttpEntity<String> entity = new HttpEntity<String>();
        
    //     //Execute the method writing your HttpEntity to the request
    //     ResponseEntity<Map> response = restTemplate.exchange("https://httpbin.org/user-agent", HttpMethod.GET, entity, Map.class);        
    //     System.out.println(response.getBody());
    
    // }

