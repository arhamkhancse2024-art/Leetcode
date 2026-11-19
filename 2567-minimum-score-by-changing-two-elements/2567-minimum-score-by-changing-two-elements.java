import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        int n = nums.length;
        
        // Sort the array to easily target the extreme values
        Arrays.sort(nums);
        
        // Strategy 1: Modify the two smallest elements
        int option1 = nums[n - 1] - nums[2];
        
        // Strategy 2: Modify the two largest elements
        int option2 = nums[n - 3] - nums[0];
        
        // Strategy 3: Modify one smallest and one largest element
        int option3 = nums[n - 2] - nums[1];
        
        // Return the minimum range among the three choices
        return Math.min(option1, Math.min(option2, option3));
    }
}
