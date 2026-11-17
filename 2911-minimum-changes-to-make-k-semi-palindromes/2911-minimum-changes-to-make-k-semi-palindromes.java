import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        
        // Step 1: Precompute proper divisors for lengths up to n
        List<Integer>[] divisors = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            divisors[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 2 * i; j <= n; j += i) {
                divisors[j].add(i);
            }
        }
        
        // Step 2: Precompute the cost to make any substring s[i...j] a semi-palindrome
        int[][] cost = new int[n][n];
        
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                int minChanges = Integer.MAX_VALUE;
                
                // Try all positive proper divisors for this substring length
                for (int d : divisors[len]) {
                    int changes = 0;
                    // Check each of the d interleaved sub-sequences
                    for (int offset = 0; offset < d; offset++) {
                        int left = i + offset;
                        int right = j - (d - 1) + offset;
                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                            left += d;
                            right -= d;
                        }
                    }
                    minChanges = Math.min(minChanges, changes);
                }
                cost[i][j] = minChanges;
            }
        }
        
        // Step 3: DP to partition the string into k parts
        // dp[i][j] = min changes for prefix of length i partitioned into j parts
        int[][] dp = new int[n + 1][k + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dp[0][0] = 0; // Base case: 0 characters partitioned into 0 parts costs 0
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i / 2); j++) {
                // A valid semi-palindrome must have a length of at least 2
                for (int left = i - 2; left >= 0; left--) {
                    if (dp[left][j - 1] != Integer.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j], dp[left][j - 1] + cost[left][i - 1]);
                    }
                }
            }
        }
        
        return dp[n][k];
    }
}
