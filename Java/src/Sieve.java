/**
 * Created by Devin on 3/12/2017.
 */
import java.lang.*;
import java.util.Arrays;

public class Sieve {
    private static double generatePrimes(int max) {
        double performance_msec;
        int primeIndex = 0;

        final float start;
        //start = System.currentTimeMillis();
        start = System.nanoTime();

        boolean[] isComposite = new boolean[max + 1];
        int[] prime = new int[max + 1];
        Arrays.fill(isComposite, true);

        for (int i = 2; i * i <= Math.sqrt(max); i++) {
            if (isComposite[i]) {
                for (int j = 2 * i; j <= max; j += i) {
                    isComposite[j] = false;
                }
            }
        }

        for (int i = 2; i <= max; i++)
            if (isComposite[i]) prime[primeIndex++] = i;

        float now;
        //now = System.currentTimeMillis();
        now = System.nanoTime();
        performance_msec = (now-start) / 100000.0;

        Arrays.fill(isComposite, false);
        return performance_msec;
    }

    private static void printStatistics(double data[], int n, int loops){
        double sum = 0.0;
        double mean;
        double standardDeviation = 0.0;

        for(int i = 0; i < loops; ++i){
            sum += data[i];
        }
        mean = sum / loops;

        for(int i = 0; i < loops; ++i) standardDeviation += Math.pow(data[i] - mean, 2);
        System.out.printf("The mean size for n = %d is %f milliseconds and the standard deviation is %f milliseconds\n", n, mean, standardDeviation);
    }

    public static void main(String[] args){
        final int nValsNumber = 7;
        final int numberLoops = 100;

        int nVals[] = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000};
        double performance[] = new double[numberLoops];

        for(int i = 0; i < nValsNumber; i++){
            for(int j = 0; j < numberLoops; j++){
                performance[j] = generatePrimes(nVals[i]);
            }
            printStatistics(performance, nVals[i], numberLoops);
        }
    }
}
