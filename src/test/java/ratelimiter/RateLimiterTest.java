package ratelimiter;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {
    // Note: To add a new testcase just change the requestInput and expectedOutput, keep rest of the code as it is.
    @Test
    void successInput01Test(){
        String[] requestInput = {"www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com", "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com", "www.xyz.com"};
        boolean[] expectedOutput = {true, true, true, true,true,true, false,true, true};
        int inputLen = requestInput.length;
        int allowedRequest = 2;
        int timeFrame = 5;
        HashMap<String, Integer> map = new HashMap<>();
        ArrayBlockingQueue<String> queRule = new ArrayBlockingQueue<>(timeFrame);
        RatingRule rule = new RatingRule(map,queRule, allowedRequest, timeFrame);

        RateLimiter obj = new RateLimiter();
        boolean[] result = new boolean[inputLen];
        for(int i=0;i<inputLen;i++) {
            result[i] = obj.isRateLimited(rule, requestInput[i]);
        }

        assertArrayEquals(expectedOutput, result);
    }

    @Test
    void successInput02Test(){
        String[] requestInput = {"www.abc.com", "www.hd.com", "www.abc.com", "www.pqr.com", "www.abc.com", "www.pqr.com", "www.pqr.com"};
        boolean[] expectedOutput = {true, true, true, true,false,true, false};
        int inputLen = requestInput.length;
        int allowedRequest = 2;
        int timeFrame = 5;
        HashMap<String, Integer> map = new HashMap<>();
        ArrayBlockingQueue<String> queRule = new ArrayBlockingQueue<>(timeFrame);
        RatingRule rule = new RatingRule(map,queRule, allowedRequest, timeFrame);

        RateLimiter obj = new RateLimiter();
        boolean[] result = new boolean[inputLen];
        for(int i=0;i<inputLen;i++) {
            result[i] = obj.isRateLimited(rule, requestInput[i]);
        }

        assertArrayEquals(expectedOutput, result);
    }

    @Test
    void successInput03Test(){
        String[] requestInput = {"www.abc.com", "www.hd.com", "www.abc.com", "www.abc.com", "www.abc.com", "www.pqr.com", "www.pqr.com"};
        boolean[] expectedOutput = {true, true, true, false, false, true, true};
        int inputLen = requestInput.length;
        int allowedRequest = 2;
        int timeFrame = 5;
        HashMap<String, Integer> map = new HashMap<>();
        ArrayBlockingQueue<String> queRule = new ArrayBlockingQueue<>(timeFrame);
        RatingRule rule = new RatingRule(map,queRule, allowedRequest, timeFrame);

        RateLimiter obj = new RateLimiter();
        boolean[] result = new boolean[inputLen];
        for(int i=0;i<inputLen;i++) {
            result[i] = obj.isRateLimited(rule, requestInput[i]);
        }

        assertArrayEquals(expectedOutput, result);
    }

    // rule2: 5 req in 30 sec
    @Test
    void successInput04Test(){
        String[] requestInput = {"www.a.com", "www.b.com", "www.a.com", "www.c.com", "www.b.com", "www.a.com", "www.c.com", "www.b.com", "www.a.com", "www.d.com", "www.a.com", "www.d.com", "www.b.com","www.a.com"};
        boolean[] expectedOutput = {true, true, true, true,true,true, true, true, true, true,true,true, true ,false};
        int inputLen = requestInput.length;
        int allowedRequest = 5;
        int timeFrame = 30;
        HashMap<String, Integer> map = new HashMap<>();
        ArrayBlockingQueue<String> queRule = new ArrayBlockingQueue<>(timeFrame);
        RatingRule rule = new RatingRule(map,queRule, allowedRequest, timeFrame);

        RateLimiter obj = new RateLimiter();
        boolean[] result = new boolean[inputLen];
        for(int i=0;i<inputLen;i++) {
            result[i] = obj.isRateLimited(rule, requestInput[i]);
        }

        assertArrayEquals(expectedOutput, result);
    }

    // The below testcase checks both the rules
    // Note: To add a new testcase just change the requestInput and expectedOutput, keep rest of the code as it is.
    @Test
    void successInputWithBothRules05Test(){
        String[] requestInput = {"www.a.com", "www.b.com", "www.a.com", "www.c.com", "www.b.com", "www.a.com", "www.c.com", "www.b.com", "www.a.com", "www.d.com", "www.a.com", "www.d.com", "www.b.com","www.a.com"};
        boolean[] expectedOutput = {true, true, true, true,true,true, true, true, true, true,true,true, true ,false};
        int inputLen = requestInput.length;
        int allowedRequest1 = 2;
        int timeFrame1 = 5;
        int allowedRequest2 = 5;
        int timeFrame2 = 30;
        // rule-1
        HashMap<String, Integer> map1 = new HashMap<>();
        ArrayBlockingQueue<String> queRule1 = new ArrayBlockingQueue<>(timeFrame1);
        RatingRule rule1 = new RatingRule(map1,queRule1, allowedRequest1, timeFrame1);
        //rule-2
        HashMap<String, Integer> map2 = new HashMap<>();
        ArrayBlockingQueue<String> queRule2 = new ArrayBlockingQueue<>(timeFrame1);
        RatingRule rule2 = new RatingRule(map2,queRule2, allowedRequest2, timeFrame2);

        RateLimiter obj = new RateLimiter();

        boolean[] result = new boolean[inputLen];
        for(int i=0;i<inputLen;i++) {
            boolean temp1 = obj.isRateLimited(rule1, requestInput[i]);
            boolean temp2 = obj.isRateLimited(rule2, requestInput[i]);
            result[i] = temp1 && temp2;
        }

        assertArrayEquals(expectedOutput, result);
    }
}