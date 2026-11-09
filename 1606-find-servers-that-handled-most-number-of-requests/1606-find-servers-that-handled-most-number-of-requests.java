import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // Track the count of handled requests for each server
        int[] requestCounts = new int[k];
        
        // TreeSet tracks available servers sorted by their IDs
        TreeSet<Integer> availableServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            availableServers.add(i);
        }
        
        // Min-heap to track busy servers, ordered by their task completion time.
        // Elements are stored as pairs: [end_time, server_id]
        PriorityQueue<int[]> busyHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        
        for (int i = 0; i < arrival.length; i++) {
            int startTime = arrival[i];
            int duration = load[i];
            
            // 1. Free up all servers whose tasks completed by the current arrival time
            while (!busyHeap.isEmpty() && busyHeap.peek()[0] <= startTime) {
                int[] freeServer = busyHeap.poll();
                availableServers.add(freeServer[1]);
            }
            
            // If no servers are available, drop the request
            if (availableServers.isEmpty()) {
                continue;
            }
            
            // 2. Identify the preferred target server
            int preferredIdx = i % k;
            
            // 3. Find the first available server ID >= preferredIdx
            Integer chosenServer = availableServers.ceiling(preferredIdx);
            
            // 4. Wrap around if no server ID is found >= preferredIdx
            if (chosenServer == null) {
                chosenServer = availableServers.first();
            }
            
            // 5. Assign the request to the chosen server
            availableServers.remove(chosenServer);
            requestCounts[chosenServer]++;
            busyHeap.offer(new int[]{startTime + duration, chosenServer});
        }
        
        // Find the maximum number of requests handled by any server
        int maxRequests = 0;
        for (int count : requestCounts) {
            maxRequests = Math.max(maxRequests, count);
        }
        
        // Collect all server IDs matching the maximum count
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCounts[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}
