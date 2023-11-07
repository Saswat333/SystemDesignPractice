package ratelimiter;

import java.lang.reflect.Array;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class RateLimiter {
    public static void main(String[] args) {
        RateLimiter obj = new RateLimiter();
        String[] request_input = {"www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com", "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com", "www.xyz.com"};
        int request_count = request_input.length;
        ArrayList<String> result = new ArrayList<>();

        //Create a rule
        RatingRule rule1 = new RatingRule(new HashMap<>(), new ArrayBlockingQueue<>(5),2, 5, 0);

        for(int i=0;i<request_input.length;i++){

            int allowedRequest = 2; //number of requests
            int timeFrame = 5; // in sec
            boolean rule1Obj = obj.isRateLimited(rule1, request_input[i], i);

            if(rule1Obj){
                // request is passed successfully
                result.add("Success");
            }
            else{
                result.add("failed");
            }
        }
//        for(String s: request_input)
//            System.out.print(s);
        System.out.println(Arrays.toString(request_input));
        System.out.println();
        System.out.println(result);

        //map
        System.out.println(rule1.mapRule);
    }

    public boolean isRateLimited(RatingRule rule, String curReq, int curIndex){
        //if the queue size <= timeframe then push the elem to queue
        //check if the elem is in the hashmap if not there add and increase the currentRequestCount
        // if rule.mapRule already has the key means we have processed it already
        // check if the queue is full, if yes then remove the last elem, reduce its count, check the frq of current elem
        //in rule.mapRule and them push if under allowed request limit
        if(curIndex <= rule.queRule1.size()){
            //for first 5 elem
            int curFreq = rule.mapRule.getOrDefault(curReq, 0);
            if(curFreq<rule.allowedRequest){
                rule.mapRule.put(curReq, curFreq+1);
                rule.queRule1.offer(curReq);
                return true;
            }
            else{
                // when we have first 3 req from same domain
                return false;
            }
        }
        else {
            String elem = rule.queRule1.poll();
            rule.mapRule.put(elem, rule.mapRule.get(elem)-1);
            int curFreq = rule.mapRule.get(curReq);
            if(curFreq<rule.allowedRequest){
                //push to queue, add to rule.mapRule update freq
                rule.mapRule.put(curReq, rule.mapRule.getOrDefault(curReq,0)+1);
                return true;
            }
            else{
                return false;
            }
        }
    }
}