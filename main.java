import java.util.HashMap;
import java.util.Map;

public class DoSProtection {
    private static final int MAX_REQUESTS_PER_SECOND = 10;
    private static final int MAX_REQUESTS_PER_MINUTE = 100;

    private Map<Long, Integer> requestsPerSecond;
    private Map<Long, Integer> requestsPerMinute;

    public DoSProtection() {
        requestsPerSecond = new HashMap<>();
        requestsPerMinute = new HashMap<>();
    }

    public boolean isAllowed() {
        long currentTimeMillis = System.currentTimeMillis();
        int totalRequestsThisSecond = countTotalRequests(requestsPerSecond, currentTimeMillis, 1000);
        int totalRequestsThisMinute = countTotalRequests(requestsPerMinute, currentTimeMillis, 60000);

        if (totalRequestsThisSecond >= MAX_REQUESTS_PER_SECOND || totalRequestsThisMinute >= MAX_REQUESTS_PER_MINUTE) {
            return false;
        }

        requestsPerSecond.put(currentTimeMillis, totalRequestsThisSecond + 1);
        requestsPerMinute.put(currentTimeMillis, totalRequestsThisMinute + 1);
        return true;
    }

    private int countTotalRequests(Map<Long, Integer> requests, long currentTimeMillis, long timeWindow) {
        int totalRequests = 0;
        long timeThreshold = currentTimeMillis - timeWindow;

        for (Map.Entry<Long, Integer> entry : requests.entrySet()) {
            if (entry.getKey() >= timeThreshold) {
                totalRequests += entry.getValue();
            } else {
                requests.remove(entry.getKey());
            }
        }

        return totalRequests;
    }
                              }
