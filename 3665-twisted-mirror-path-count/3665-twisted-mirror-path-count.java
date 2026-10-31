import java.util.Arrays;

class Solution {
    private static final int MOD = 1_000_000_007;
    private int[][] memo;
    private int m, n;

    public int uniquePaths(int[][] grid) {
        this.m = grid.length;
        this.n = grid[0].length;
        
        // memo[r][c] stores the number of unique valid paths from empty cell (r, c) to the destination
        memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        return solve(0, 0, grid);
    }

    private int solve(int r, int c, int[][] grid) {
        // Base case: successfully reached the bottom-right destination
        if (r == m - 1 && c == n - 1) {
            return 1;
        }

        // Return cached result if already computed
        if (memo[r][c] != -1) {
            return memo[r][c];
        }

        long totalPaths = 0;

        // Choice 1: Attempt to move RIGHT to the next cell (r, c + 1) with direction 0 (RIGHT)
        int[] rightLanding = getLandingCell(r, c + 1, 0, grid);
        if (rightLanding != null) {
            totalPaths = (totalPaths + solve(rightLanding[0], rightLanding[1], grid)) % MOD;
        }

        // Choice 2: Attempt to move DOWN to the next cell (r + 1, c) with direction 1 (DOWN)
        int[] downLanding = getLandingCell(r + 1, c, 1, grid);
        if (downLanding != null) {
            totalPaths = (totalPaths + solve(downLanding[0], downLanding[1], grid)) % MOD;
        }

        return memo[r][c] = (int) totalPaths;
    }

    /**
     * Simulates the path of the robot from the target cell until it lands on an empty cell (0)
     * or goes out of grid bounds.
     * @param dir 0 for moving RIGHT, 1 for moving DOWN
     * @return int[] {landing_row, landing_col} if valid, or null if it goes out of bounds.
     */
    private int[] getLandingCell(int nr, int nc, int dir, int[][] grid) {
        while (nr < m && nc < n && grid[nr][nc] == 1) {
            if (dir == 0) {
                // Moving RIGHT into a mirror -> reflected DOWN to the cell below
                nr++;
                dir = 1;
            } else {
                // Moving DOWN into a mirror -> reflected RIGHT to the cell to the right
                nc++;
                dir = 0;
            }
        }
        
        // If the reflection chain takes the robot out of bounds, the path is invalid
        if (nr >= m || nc >= n) {
            return null;
        }
        
        return new int[]{nr, nc};
    }
}
