package connection.service;

import connection.util.WeightedQuickUnionUF;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Service
public class ConnectionServiceImpl implements ConnectionService
{
    private Set<String> hashSet = new HashSet<String>();
    private Map<String, Integer> hashMap = new HashMap<String, Integer>();
    private WeightedQuickUnionUF uf;
    private int count = 0;
    ArrayList<String> dataFromFile = new ArrayList<String>();

    // Read the file from the classpath, and store the data in memory
    @Override
    public void readFile(String filename) throws IOException, NullPointerException
    {
        String line;
        Resource resource = new ClassPathResource(filename);
        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null)
        {
            String[] parse = line.split(",");
            parse[0] = parse[0].trim();
            parse[1] = parse[1].trim();
            hashSet.add(parse[0]);
            hashSet.add(parse[1]);
            dataFromFile.add(line);
        }
        br.close();
    }

    /* Create connections from the data in memory, if it exists, then create a UnionFind object size n, where
     * n is the unique cities. The algo implemented integers only, so I created a quick hashing algorithm to interface
     * with the algorithm
    */
    @Override
    public void createConnections() throws IOException
    {
        if (hashSet.isEmpty())
        {
            this.readFile("city.txt");
        }
        hashSet.forEach(string -> {
            hashMap.put(string, count);
            ++count;
        });
        uf = new WeightedQuickUnionUF(count);
        dataFromFile.forEach(line -> {
            String[] parse = line.split(",");
            String origin = parse[0].trim();
            String destination = parse[1].trim();
            uf.union(lookUpHash(origin), lookUpHash(destination));
        });
    }

    //TODO - using transactions
//    @Override
//    public String payStore(String price) throws IOException {
//        //TODO - make call to api here
//        RestTemplate restTemplate = new RestTemplate();
//        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//        interceptors.add(new HeaderRequestInterceptor("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDQlAiLCJ0ZWFtX2lkIjoiYmQyM2RlMTItZmQ0YS0zOTJmLTkzMDEtMDVhZGFjOGQwOWViIiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1LCJhcHBfaWQiOiIzMGY4NjJkMi03NWMxLTQ2NjAtYWY0Yy0yZTliM2NmNzIzNWIifQ.SzDZU2TPzNfpxQ6iqVJNe-SRVz_4u3mQTn32CVP8EFE"));
//        restTemplate.setInterceptors(interceptors);
//
//        //make this initial call to get object
////        Result resultObj = restTemplate.getForObject("https://api.td-davinci.com/api/simulatedaccounts/30f862d2-75c1-4660-af4c-2e9b3cf7235b_2d8c2ae0-300c-436a-b164-f5e36374647c/simulatedtransactions", Result.class);
//        //for each value of result
//        //get transaction data
//        Map<String, Object> additionalProps = resultObj.getAdditionalProperties();
//        ArrayList<LinkedHashMap<String, String>>  transactions = (ArrayList<LinkedHashMap<String, String>>) additionalProps.get("result");
//        return transactions.toString();
//    }

    //TODO - using transfers api
    @Override
    public String payStore(String price) throws IOException {

        JSONParser parser = new JSONParser();
        String response = null;
        try {

            Object obj = parser.parse(new FileReader(
                    "/home/shah/IdeaProjects/cities/src/main/resources/POST"));
            //TODO - make call to api here
            JSONObject jsonObject = (JSONObject) obj;
            RestTemplate restTemplate = new RestTemplate();
//            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//            interceptors.add(new HeaderRequestInterceptor("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDQlAiLCJ0ZWFtX2lkIjoiYmQyM2RlMTItZmQ0YS0zOTJmLTkzMDEtMDVhZGFjOGQwOWViIiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1LCJhcHBfaWQiOiIzMGY4NjJkMi03NWMxLTQ2NjAtYWY0Yy0yZTliM2NmNzIzNWIifQ.SzDZU2TPzNfpxQ6iqVJNe-SRVz_4u3mQTn32CVP8EFE"));
//            interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json;charset=UTF-8"));
//
//            restTemplate.setInterceptors(interceptors);
            String url = "https://api.td-davinci.com/api/transfers";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDQlAiLCJ0ZWFtX2lkIjoiYmQyM2RlMTItZmQ0YS0zOTJmLTkzMDEtMDVhZGFjOGQwOWViIiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1LCJhcHBfaWQiOiIzMGY4NjJkMi03NWMxLTQ2NjAtYWY0Yy0yZTliM2NmNzIzNWIifQ.SzDZU2TPzNfpxQ6iqVJNe-SRVz_4u3mQTn32CVP8EFE");

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
            ResponseEntity<String> resultObj = restTemplate.postForEntity(url, entity, String.class);
            //for each value of result
            //get transaction data
//            Map<String, Object> additionalProps = resultObj;
//            ArrayList<LinkedHashMap<String, String>>  transactions = (ArrayList<LinkedHashMap<String, String>>) additionalProps.get("result");
//            return transactions.toString();
            response = resultObj.toString();
return response;

        }
        catch(Exception e){
            System.out.println(e);
        }
return response;
    }

    // Check if there is a connected road between two cities
    @Override
    public boolean isConnectedRoad(String origin, String destination)
    {
        int p = lookUpHash(origin);
        int q = lookUpHash(destination);
        if (p == -1 || q == -1)
        {
            return false;
        }
        return uf.connected(p, q);
    }

    @Override
    public int lookUpHash(String city)
    {
        if (hashSet.contains(city))
        {
            return hashMap.get(city);
        }
        return -1;
    }
}