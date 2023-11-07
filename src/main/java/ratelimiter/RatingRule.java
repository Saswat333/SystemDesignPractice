package ratelimiter;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class RatingRule {
    HashMap<String, Integer> mapRule;
    ArrayBlockingQueue<String> queRule1;
    int allowedRequest;
    int timeFrame;
    int currentRequestCount;

    public RatingRule(HashMap<String, Integer> mapRule, ArrayBlockingQueue<String> queRule1, int allowedRequest, int timeFrame, int currentRequestCount) {
        this.mapRule = mapRule;
        this.queRule1 = queRule1;
        this.allowedRequest = allowedRequest;
        this.timeFrame = timeFrame;
        this.currentRequestCount = currentRequestCount;
    }
}
