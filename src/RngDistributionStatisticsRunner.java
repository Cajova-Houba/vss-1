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
     * Count of numbers to generate.
     */
    private final int numberCount;

    /**
     * Generator to be used.
     */
    private final IDistributionGenerator generator;

    private Map<Integer, Integer> histogramMap;

    /**
     * Max number generated during the run().
     */
    private Integer maxNumber;

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
    }

    /**
     * Generates given count of random numbers and gathers statistics.
     */
    public void run() {
        initializeBeforeRun();

        for (int i = 0; i < numberCount; i++) {
            Double rand = generator.nextDouble();

            if (maxNumber == null || maxNumber < rand.intValue()) {
                maxNumber = rand.intValue();
            }

            int count;
            if (histogramMap.containsKey(rand.intValue())) {
                count = histogramMap.get(rand.intValue());
                count++;
                histogramMap.put(rand.intValue(),count);
            } else {
                count = 1;
                histogramMap.put(rand.intValue(), 1);
            }

            if (maxNumberCount == null || maxNumberCount < count) {
                maxNumberCount = count;
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
        for(Integer number : histogramMap.keySet()) {
            // histogram value / number count is basically probability of number occurring
            mean += number * histogramMap.get(number) / numberCount;
        }
    }

    private void calculateVariance() {
        double numberCount = getNumberCount();
        for(Integer number : histogramMap.keySet()) {
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
     * Returns histogram with following format:
     * [x][0] = number
     * [x][1] = count
     * @return
     */
    public Map<Integer, Integer> getHistogram() {
        return histogramMap;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public int getMaxNumber() {
        if (maxNumber == null) {
            return 0;
        }
        return maxNumber;
    }

    public int getMaxNumberCount() {
        return maxNumberCount;
    }
}
