import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] windowCosts = getWindowCosts(nums, x);
        
        // dp[i][j] = min cost to pick j non-overlapping windows from suffix starting at i
        long[][] dp = new long[n + 1][k + 1];
        long INF = Long.MAX_VALUE / 2; // Avoid overflow during addition
        
        // Initialize DP table
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0; // 0 windows cost 0
        }
        
        // Fill DP from right to left
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; j <= k; j++) {
                // Option 1: Skip the current element
                dp[i][j] = dp[i + 1][j];
                
                // Option 2: Pick a window of size x starting at i (if valid)
                if (i + x <= n && windowCosts[i] != -1) {
                    dp[i][j] = Math.min(dp[i][j], windowCosts[i] + dp[i + x][j - 1]);
                }
            }
        }
        
        return dp[0][k] >= INF ? -1 : dp[0][k];
    }
    
    private long[] getWindowCosts(int[] nums, int x) {
        int n = nums.length;
        long[] costs = new long[n];
        Arrays.fill(costs, -1);
        
        // Two heaps to find the median dynamically
        PriorityQueue<Integer> leftHeap = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap
        PriorityQueue<Integer> rightHeap = new PriorityQueue<>(); // Min-heap
        
        // Frequency maps to handle lazy deletion from heaps
        Map<Integer, Integer> leftOut = new HashMap<>();
        Map<Integer, Integer> rightOut = new HashMap<>();
        
        int leftSize = 0, rightSize = 0;
        long leftSum = 0, rightSum = 0;
        
        for (int i = 0; i < n; i++) {
            // 1. Add current element into the heaps
            if (leftHeap.isEmpty() || nums[i] <= leftHeap.peek()) {
                leftHeap.add(nums[i]);
                leftSum += nums[i];
                leftSize++;
            } else {
                rightHeap.add(nums[i]);
                rightSum += nums[i];
                rightSize++;
            }
            
            // 2. Remove out-of-bounds element (Lazy deletion tracking)
            if (i >= x) {
                int out = nums[i - x];
                if (!leftHeap.isEmpty() && out <= leftHeap.peek()) {
                    leftOut.put(out, leftOut.getOrDefault(out, 0) + 1);
                    leftSum -= out;
                    leftSize--;
                } else {
                    rightOut.put(out, rightOut.getOrDefault(out, 0) + 1);
                    rightSum -= out;
                    rightSize--;
                }
            }
            
            // Prune invalid elements from top of heaps
            prune(leftHeap, leftOut);
            prune(rightHeap, rightOut);
            
            // 3. Rebalance heaps so leftHeap contains the median at its top
            // Target: leftSize == rightSize or leftSize == rightSize + 1
            while (leftSize > rightSize + 1) {
                int val = leftHeap.poll();
                leftSum -= val;
                leftSize--;
                
                rightHeap.add(val);
                rightSum += val;
                rightSize++;
                
                prune(leftHeap, leftOut);
            }
            
            while (leftSize < rightSize) {
                int val = rightHeap.poll();
                rightSum -= val;
                rightSize--;
                
                leftHeap.add(val);
                leftSum += val;
                leftSize++;
                
                prune(rightHeap, rightOut);
            }
            
            // 4. Calculate cost once window size reaches x
            if (i >= x - 1) {
                long median = leftHeap.peek();
                long currentCost = (median * leftSize - leftSum) + (rightSum - median * rightSize);
                costs[i - x + 1] = currentCost;
            }
        }
        
        return costs;
    }
    
    private void prune(PriorityQueue<Integer> heap, Map<Integer, Integer> outMap) {
        while (!heap.isEmpty() && outMap.getOrDefault(heap.peek(), 0) > 0) {
            int top = heap.poll();
            outMap.put(top, outMap.get(top) - 1);
        }
    }
}
