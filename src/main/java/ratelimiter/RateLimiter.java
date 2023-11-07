package ratelimiter;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class RateLimiter {
    public static void main(String[] args) {
        RateLimiter obj = new RateLimiter();
        String[] requestInput = {"www.abc.com", "www.hd.com", "www.abc.com", "www.abc.com", "www.abc.com", "www.pqr.com", "www.pqr.com"};
        ArrayList<String> result = new ArrayList<>();

        //Create a rule
        RatingRule rule1 = new RatingRule(new HashMap<>(), new ArrayBlockingQueue<>(5),2, 5);
        RatingRule rule2 = new RatingRule(new HashMap<>(), new ArrayBlockingQueue<>(30),5, 30);

        for(String input: requestInput){
            boolean rule1Obj = obj.isRateLimited(rule1, input);
            boolean rule2Obj = obj.isRateLimited(rule2, input);

            if(rule1Obj && rule2Obj){
                result.add("Success");
            }
            else{
                System.out.println(rule1Obj);
                System.out.println(rule2Obj);
                result.add("Failed");
            }
        }
        System.out.println(Arrays.toString(requestInput));
        System.out.println();
        System.out.println(result);
    }

    public boolean isRateLimited(RatingRule rule, String curReq){
        if(rule.queRule1.size() < rule.timeFrame){
            //for first 5 elem
            int curFreq = rule.mapRule.getOrDefault(curReq, 0);
            if(curFreq < rule.allowedRequest){
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
            int curFreq = rule.mapRule.getOrDefault(curReq, 0);
            if(curFreq<rule.allowedRequest){
                //push to queue, add to rule.mapRule update freq
                rule.mapRule.put(curReq, rule.mapRule.getOrDefault(curReq,0)+1);
                rule.queRule1.offer(curReq);
                return true;
            }
            else{
                return false;
            }
        }
    }
}