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

    public RngDistributionStatisticsRunner(int numberCount, IDistributionGenerator generator) {
        this.numberCount = numberCount;
        this.generator = generator;
        histogramMap = new HashMap<>();
    }

    /**
     * Generates given count of random numbers and gathers statistics.
     */
    public void run() {
        for (int i = 0; i < numberCount; i++) {
            Double rand = generator.nextDouble();
            if (histogramMap.containsKey(rand.intValue())) {
                histogramMap.put(rand.intValue(),histogramMap.get(rand.intValue())+1);
            } else {
                histogramMap.put(rand.intValue(), 1);
            }
        }
    }


    public double getMean() {
        // todo: implement getMean method
        return 0.0;
    }

    public double getVariance() {
        // todo: implement getVariance method
        return 0.0;
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
}
