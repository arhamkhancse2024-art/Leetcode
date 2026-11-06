import java.util.Arrays;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        int[] ans = new int[m];
        
        // Pair queries with their original index to preserve order after sorting
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }
        
        // Sort logs by time (ascending)
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));
        
        // Sort queries by time (ascending)
        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Frequency array to keep track of active servers in the window
        int[] serverCounts = new int[n + 1];
        int activeServers = 0;
        
        int i = 0; // Left pointer (to shrink the window)
        int j = 0; // Right pointer (to expand the window)
        
        for (int[] queryInfo : sortedQueries) {
            int queryTime = queryInfo[0];
            int originalIdx = queryInfo[1];
            
            // Expand window: Include logs that fall within time <= queryTime
            while (j < logs.length && logs[j][1] <= queryTime) {
                int serverId = logs[j][0];
                if (serverCounts[serverId] == 0) {
                    activeServers++;
                }
                serverCounts[serverId]++;
                j++;
            }
            
            // Shrink window: Exclude logs that fall outside time < queryTime - x
            while (i < logs.length && logs[i][1] < queryTime - x) {
                int serverId = logs[i][0];
                serverCounts[serverId]--;
                if (serverCounts[serverId] == 0) {
                    activeServers--;
                }
                i++;
            }
            
            // The number of servers with 0 requests is total minus active
            ans[originalIdx] = n - activeServers;
        }
        
        return ans;
    }
}
