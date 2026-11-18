class Solution {
    public int minChanges(String s) {
        int changes = 0;
        
        // Loop through the string, moving in steps of 2 to check independent pairs
        for (int i = 0; i < s.length(); i += 2) {
            // If the two characters in the pair don't match, we must change one of them
            if (s.charAt(i) != s.charAt(i + 1)) {
                changes++;
            }
        }
        
        return changes;
    }
}
