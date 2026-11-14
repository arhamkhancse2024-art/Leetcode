class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int minSum = Integer.MAX_VALUE;

        // Iterate through all possible valid subarray lengths from l to r
        for (int size = l; size <= r; size++) {
            int currentWindowSum = 0;

            // Initialize the first window of the current size
            for (int i = 0; i < size; i++) {
                currentWindowSum += nums.get(i);
            }

            // Check if the initial window sum is strictly positive
            if (currentWindowSum > 0) {
                minSum = Math.min(minSum, currentWindowSum);
            }

            // Slide the window across the rest of the array
            for (int i = size; i < n; i++) {
                currentWindowSum += nums.get(i) - nums.get(i - size);
                
                if (currentWindowSum > 0) {
                    minSum = Math.min(minSum, currentWindowSum);
                }
            }
        }

        // Return -1 if no strictly positive sum subarray was found
        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }
}
