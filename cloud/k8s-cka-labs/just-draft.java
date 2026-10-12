import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;



class Result {

    /*
     * Complete the 'getTotalGoals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING team
     *  2. INTEGER year
     */
     private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static int getTotalGoals(String team, int year) {
         
         String encodedTeam = URLEncoder.encode(
                team, StandardCharsets.UTF_8
        );
         
         String url = String.format(
                "https://jsonmock.hackerrank.com/api/football_matches?year=%d&team1=%s&page=%d",
                year, encodedTeam, 1
        );   
        
        String url2 = String.format(
                "https://jsonmock.hackerrank.com/api/football_matches?year=%d&team2=%s&page=%d",
                year, encodedTeam, 1
        );
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
                
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(url2))
                .GET()
                .build();
        try{        
        HttpResponse<String> response = CLIENT.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        HttpResponse<String> response2 = CLIENT.send(
                request2,
                HttpResponse.BodyHandlers.ofString()
        );
        int totalHome=0;
        int totalVisit=0;
        String json = response.body();

        Pattern pattern = Pattern.compile(
                "\"total\"\\s*:\\s*(\\d+)"
        );
        

        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            totalHome= Integer.parseInt(matcher.group(1));
        }
        
        String json2 = response.body();
        matcher = pattern.matcher(json2);
        
        if(matcher.find()){
            totalVisit=Integer.parseInt(matcher.group(1)); 
        }
        int total = totalHome + totalVisit;
        return total;
        }catch(Exception e){
            return 0;
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String team = bufferedReader.readLine();

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result.getTotalGoals(team, year);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
