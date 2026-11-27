class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;
        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];
            minPrice = Math.min(minPrice, price);
            int totalGain = 0;
            for (int j = 0; j < n; j++) {
                if (items[j][0] % factor == 0) {
                    totalGain++;
                }
            }

            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b], dp[b - price] + totalGain);
            }
        }
        int maxItems = 0;
        for (int b = 0; b <= budget; b++) {
            int extraCopies = (budget - b) / minPrice;
            maxItems = Math.max(maxItems, dp[b] + extraCopies);
        }

        return maxItems;
    }
}
