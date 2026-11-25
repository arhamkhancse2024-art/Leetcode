import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE1 = 100_003L;
    private static final long BASE2 = 100_019L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        long[] p1 = new long[n + 1];
        long[] p2 = new long[n + 1];
        long[] h1 = new long[n + 1];
        long[] h2 = new long[n + 1];

        p1[0] = 1;
        p2[0] = 1;

        for (int i = 0; i < n; i++) {
            p1[i + 1] = (p1[i] * BASE1) % MOD1;
            p2[i + 1] = (p2[i] * BASE2) % MOD2;
            h1[i + 1] = (h1[i] * BASE1 + nums[i]) % MOD1;
            h2[i + 1] = (h2[i] * BASE2 + nums[i]) % MOD2;
        }

        int low = 1, high = n, ans = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUniqueOfLen(mid, n, p1, p2, h1, h2)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUniqueOfLen(int len, int n, long[] p1, long[] p2, long[] h1, long[] h2) {
        Map<Long, Integer> counts = new HashMap<>(n - len + 1);
        for (int i = 0; i <= n - len; i++) {
            int j = i + len;
            long sub1 = (h1[j] - (h1[i] * p1[len]) % MOD1 + MOD1) % MOD1;
            long sub2 = (h2[j] - (h2[i] * p2[len]) % MOD2 + MOD2) % MOD2;
            
            // Combine both 32-bit hashes into a single 64-bit long key
            long combinedHash = (sub1 << 32) | sub2;
            counts.put(combinedHash, counts.getOrDefault(combinedHash, 0) + 1);
        }

        for (int count : counts.values()) {
            if (count == 1) {
                return true;
            }
        }
        return false;
    }
}
