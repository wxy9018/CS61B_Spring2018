package hw2;

public class PercolationTester {
    public static void main (String[] args) {
        int N = 30;
        int T = 100000;
        PercolationStats tester = new PercolationStats(N, T, new PercolationFactory());
        double upperTH = tester.confidenceHigh() / (N * N);
        double lowerTH = tester.confidenceLow() / (N * N);
        System.out.println("[" + lowerTH + ", " + upperTH + "]");
    }
}
