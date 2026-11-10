class Solution {
    public int climbStairs(int n, int[] costs) {
        // Track dp[i-3], dp[i-2], and dp[i-1]
        // Use a large value to prevent integer overflow before steps 1 and 2 exist
        int prev3 = Integer.MAX_VALUE / 2;
        int prev2 = Integer.MAX_VALUE / 2;
        int prev1 = 0; // dp[0] = 0

        for (int i = 1; i <= n; i++) {
            int jump1 = prev1 + 1;
            int jump2 = prev2 + 4;
            int jump3 = prev3 + 9;

            int current = costs[i - 1] + Math.min(jump1, Math.min(jump2, jump3));

            // Shift states forward
            prev3 = prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}
