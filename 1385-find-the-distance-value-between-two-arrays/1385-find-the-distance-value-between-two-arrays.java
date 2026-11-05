import java.util.Arrays;

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int distanceValue = 0;
        
        for (int x : arr1) {
            // Search for the boundaries of the invalid range [x - d, x + d]
            int idx = Arrays.binarySearch(arr2, x - d);
            
            // If the exact number (x - d) is not found, binarySearch returns: 
            // -(insertion_point) - 1. We convert it back to the true insertion point.
            if (idx < 0) {
                idx = -(idx + 1);
            }
            
            // Check if the closest larger element at 'idx' falls within the range [x - d, x + d]
            if (idx == arr2.length || arr2[idx] > x + d) {
                distanceValue++;
            }
        }
        
        return distanceValue;
    }
}
