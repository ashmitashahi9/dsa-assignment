// Question 1
// a)
// You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating each venue with a certain theme varies.
// The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example, costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering to the adjacency constraint.
// For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of 1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of 3 + 2 = 5.
// Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while satisfying the adjacency constraint.
// Please note that the costs are positive integers.
// Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7
// Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 + 6 + 1 = 7.
// [5 Marks]


public class question1a {

    public static int minCostToDecorateVenues(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length; // number of venues
        int k = costs[0].length; // number of themes

        // Initialize a table to store minimum costs
        int[][] dp = new int[n][k];

        // Initialize the first row with the costs of decorating the first venue
        System.arraycopy(costs[0], 0, dp[0], 0, k);

        // Iterate through the venues starting from the second one
        for (int i = 1; i < n; i++) {
            // Iterate through the themes for the current venue
            for (int j = 0; j < k; j++) {
                // Initialize the minimum cost to be the maximum possible integer value
                int minCost = Integer.MAX_VALUE;
                // Iterate through the themes for the previous venue
                for (int prevTheme = 0; prevTheme < k; prevTheme++) {
                    // Skip if the previous and current themes are the same
                    if (j != prevTheme) {
                        // Update the minimum cost considering the cost of the previous venue and its theme
                        minCost = Math.min(minCost, dp[i - 1][prevTheme]);
                    }
                }
                // Update the minimum cost for the current venue and theme
                dp[i][j] = costs[i][j] + minCost;
            }
        }

        // Find the minimum cost among the last row (all venues)
        int minCost = Integer.MAX_VALUE;
        for (int themeCost : dp[n - 1]) {
            minCost = Math.min(minCost, themeCost);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println(minCostToDecorateVenues(costs)); // Output: 7
    }
}
