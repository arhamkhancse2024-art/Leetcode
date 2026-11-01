class Solution {
    public int minMoves(int[] nums) {
        int min = Integer.MAX_VALUE;
        long sum = 0; // Use long to prevent integer overflow during summation
        
        // Step 1: Find the minimum value and the total sum of the array
        for (int num : nums) {
            min = Math.min(min, num);
            sum += num;
        }
        
        // Step 2: Apply the mathematical formula
        return (int) (sum - (long) min * nums.length);
    }
}
