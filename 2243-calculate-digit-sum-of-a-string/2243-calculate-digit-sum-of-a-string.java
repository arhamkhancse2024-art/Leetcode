class Solution {
    public String digitSum(String s, int k) {
        // Continue processing as long as the string length exceeds k
        while (s.length() > k) {
            StringBuilder nextString = new StringBuilder();
            
            // Traverse the string in chunks of size k
            for (int i = 0; i < s.length(); i += k) {
                int sum = 0;
                
                // Sum the digits in the current group of size k
                for (int j = i; j < Math.min(i + k, s.length()); j++) {
                    sum += s.charAt(j) - '0';
                }
                
                // Append the sum of the group to the next string representation
                nextString.append(sum);
            }
            
            // Update s with the newly constructed string for the next iteration
            s = nextString.toString();
        }
        
        return s;
    }
}
