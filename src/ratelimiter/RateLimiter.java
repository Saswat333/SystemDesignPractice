package ratelimiter;

public class RateLimiter {
    public static void main(String[] args) {
        RateLimiter obj = new RateLimiter();
        int request_count = 9;
        String[] request_input = {"www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com", "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com", "www.xyz.com"};
        for(int i=0;i<request_input.length;i++){
            int allowedRequest = 2;
            int timeFrame = 5;
            boolean rule1 = obj.isRateLimited(allowedRequest, timeFrame, request_input);
        }



    }

    public boolean isRateLimited(int allowedRequest, int windowSize, String[] arr){
        return true;
    }
}
