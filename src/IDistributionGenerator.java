/**
 * Interface for generator with custom distribution.
 */
public interface IDistributionGenerator {

    /**
     * Generates random number. Range depends on implementation.
     * @return Random number
     */
    double nextDouble();

    /**
     * Returns expected mean of given distribution.
     * @return Expected mean.
     */
    double expectedMean();

    /**
     * Returns expected variance of given distribution.
     * @return Expected distribution.
     */
    double expectedVariance();
}
