public class GeneSequenceAlignment {

    public static double[][] calculateAlignmentScore(String x, String y, double[][] scoreMatrix) {
        int n = x.length();
        int m = y.length();
        double[][] dp = new double[n + 1][m + 1];

        // Initialize first row and column for gap penalties
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0] + scoreMatrix[charToIndex(x.charAt(i - 1))][4];
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = dp[0][j - 1] + scoreMatrix[4][charToIndex(y.charAt(j - 1))];
        }

        // Fill the scoring matrix
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double match = dp[i - 1][j - 1] + scoreMatrix[charToIndex(x.charAt(i - 1))][charToIndex(y.charAt(j - 1))];
                double delete = dp[i - 1][j] + scoreMatrix[charToIndex(x.charAt(i - 1))][4];
                double insert = dp[i][j - 1] + scoreMatrix[4][charToIndex(y.charAt(j - 1))];
                dp[i][j] = Math.max(Math.max(match, delete), insert);
            }
        }

        return dp;
    }

    private static int charToIndex(char c) {
        switch (c) {
            case 'A': return 0;
            case 'G': return 1;
            case 'T': return 2;
            case 'C': return 3;
            default: return 4; // Gap
        }
    }

    private static void printAlignment(String x, String y, double[][] dp, double[][] scoreMatrix) {
        StringBuilder alignX = new StringBuilder();
        StringBuilder alignY = new StringBuilder();

        int i = x.length();
        int j = y.length();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + scoreMatrix[charToIndex(x.charAt(i - 1))][charToIndex(y.charAt(j - 1))]) {
                alignX.insert(0, x.charAt(i - 1));
                alignY.insert(0, y.charAt(j - 1));
                i--;
                j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + scoreMatrix[charToIndex(x.charAt(i - 1))][4]) {
                alignX.insert(0, x.charAt(i - 1));
                alignY.insert(0, '-');
                i--;
            } else {
                alignX.insert(0, '-');
                alignY.insert(0, y.charAt(j - 1));
                j--;
            }
        }

        System.out.println(alignX);
        System.out.println(alignY);
    }

    public static void main(String[] args) {
        String x = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGAC";
        String y = "AATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";
        double[][] scoreMatrix = {
            {1, -0.8, -0.2, -2.3, -0.6},
            {-0.8, 1, -1.1, -0.7, -1.5},
            {-0.2, -1.1, 1, -0.5, -0.9},
            {-2.3, -0.7, -0.5, 1, -1},
            {-0.6, -1.5, -0.9, -1, 0} // Assuming gap vs gap score is 0
        };

        double[][] alignmentScoreMatrix = calculateAlignmentScore(x, y, scoreMatrix);
        System.out.println("Highest-scoring alignment: " + alignmentScoreMatrix[x.length()][y.length()]);
        printAlignment(x, y, alignmentScoreMatrix, scoreMatrix);
    }
}
