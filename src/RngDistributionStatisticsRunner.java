import java.util.HashMap;
import java.util.Map;

/**
 * This class generates numbers with provided generator and
 * creates histogram, mean value and variance.
 *
 * Class is immutable, so new instance must be created for
 * new set of input parameters.
 */
public class RngDistributionStatisticsRunner {

    /**
     * Size of the histogram.
     */
    public static final int HISTOGRAM_SIZE = 10;

    /**
     * Count of numbers to generate.
     */
    private final int numberCount;

    /**
     * Generator to be used.
     */
    private final IDistributionGenerator generator;

    /**
     * This map is used to calculate mean and variance.
     */
    private Map<Double, Integer> histogramMap;

    /**
     * Histogram array of length 10 used to print histogram.
     */
    private int[] histogramArray;

    /**
     * One step of a histogram. Possible range of values is divided into 10 steps.
     */
    private double histogramStep;

    /**
     * Max number generated during the run().
     */
    private Double maxNumber;

    /**
     * Max occasions of one number.
     */
    private Integer maxNumberCount;

    /**
     * Mean. Calculated after run().
     */
    private double mean;

    /**
     * Variance. Calculated after variance is calculated.
     */
    private double variance;

    public RngDistributionStatisticsRunner(int numberCount, IDistributionGenerator generator) {
        this.numberCount = numberCount;
        this.generator = generator;
    }

    private void initializeBeforeRun() {
        histogramMap = new HashMap<>();
        maxNumber = null;
        mean = 0;
        variance = 0;
        histogramArray = new int[HISTOGRAM_SIZE];
        histogramStep = generator.maxNumber() / ((double)HISTOGRAM_SIZE);
    }

    /**
     * Generates given count of random numbers and gathers statistics.
     */
    public void run() {
        initializeBeforeRun();

        for (int i = 0; i < numberCount; i++) {
            Double rand = generator.nextDouble();

            if (maxNumber == null || maxNumber < rand) {
                maxNumber = rand;
            }

            // add value to histogram map - so it can be later used to calculate mean and variance
            int count;
            if (histogramMap.containsKey(rand)) {
                count = histogramMap.get(rand);
                count++;
                histogramMap.put(rand,count);
            } else {
                count = 1;
                histogramMap.put(rand, count);
            }

            // add generated value to histogram
            boolean addedToHistogram = false;
            int counter = 1;
            while (!addedToHistogram) {
                if (counter*histogramStep >= rand) {
                    histogramArray[counter-1]++;
                    addedToHistogram = true;
                } else {
                    counter++;
                }

                // value is too big, put it to the end of histogram
                if ((counter-1) == histogramArray.length ) {
                    counter--;
                    histogramArray[counter-1]++;
                    addedToHistogram = true;
                }

                // maxNumberCount
                if (maxNumberCount == null || maxNumberCount < histogramArray[counter-1]) {
                    maxNumberCount = histogramArray[counter-1];
                }
            }

        }

        calculateMean();
        calculateVariance();
    }

    /**
     * Calculates mean from histogram.
     */
    private void calculateMean() {
        double numberCount = getNumberCount();
        mean = 0;
        for(Double number : histogramMap.keySet()) {
            // histogram value / number count is basically probability of number occurring
            mean += number * histogramMap.get(number) / numberCount;
        }
    }

    private void calculateVariance() {
        double numberCount = getNumberCount();
        for(Double number : histogramMap.keySet()) {
            // histogram value / number count is basically probability of number occurring
            variance += Math.pow(number - mean, 2) * (histogramMap.get(number) / numberCount);
        }
    }

    public double getMean() {
        return mean;
    }

    public double getVariance() {
        return variance;
    }

    public double getExpectedMean() {
        return generator.expectedMean();
    }

    public double getExpectedVariance() {
        return generator.expectedVariance();
    }

    /**
     * It is guaranteed that this array will be of size HISTOGRAM_SIZE after runnin
     * run() method.
     * @return
     */
    public int[] getHistogramArray() {
        return histogramArray;
    }

    public double getHistogramStep() {
        return histogramStep;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public double getMaxNumber() {
        if (maxNumber == null) {
            return 0;
        }
        return maxNumber;
    }

    public int getMaxNumberCount() {
        return maxNumberCount;
    }
}
