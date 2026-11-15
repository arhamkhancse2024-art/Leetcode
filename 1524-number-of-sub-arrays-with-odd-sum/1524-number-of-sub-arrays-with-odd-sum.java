class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1_000_000_007;
        long count = 0;
        int prefixSum = 0;
        
        // oddCount starts at 0, evenCount starts at 1 (for the empty prefix sum = 0)
        int oddCount = 0;
        int evenCount = 1;
        
        for (int num : arr) {
            prefixSum += num;
            
            if (prefixSum % 2 != 0) {
                // Current prefix sum is odd -> pairs with previous even prefix sums
                count += evenCount;
                oddCount++;
            } else {
                // Current prefix sum is even -> pairs with previous odd prefix sums
                count += oddCount;
                evenCount++;
            }
        }
        
        return (int) (count % MOD);
    }
}
