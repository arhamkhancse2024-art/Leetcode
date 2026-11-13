import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        long ans = 0;
        long cost = 0;
        int n = nums.length;
        
        // Deque to store indices. It maintains values in non-increasing order.
        Deque<Integer> dq = new ArrayDeque<>();
        int j = n - 1; // Right pointer of the sliding window

        // Expand the window by moving the left pointer 'i' backward
        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];

            // Maintain monotonic property and accumulate cost
            while (!dq.isEmpty() && nums[dq.peekLast()] < num) {
                int l = dq.pollLast();
                int r = dq.isEmpty() ? j + 1 : dq.peekLast();
                cost += (long) (r - l) * (num - nums[l]);
            }
            dq.addLast(i);

            // If cost exceeds k, shrink the window from the right boundary 'j'
            while (cost > k) {
                cost -= (nums[dq.peekFirst()] - nums[j]);
                if (dq.peekFirst() == j) {
                    dq.pollFirst();
                }
                j--;
            }

            // All subarrays starting at 'i' and ending anywhere from 'i' to 'j' are valid
            ans += (j - i + 1);
        }

        return ans;
    }
}
