import java.util.Random;

/**
 * Class which takes random generator (with uniform distribution) as
 * a parameter and uses it to generate random numbers with triangular distribution.
 *
 * Parameters of standard triangular distribution are a,b,c. This distribution uses following
 * a = 0
 * b = param
 * c = 2*b
 */
public class SimpleTriangularDistribution implements IDistributionGenerator {

    /**
     * Generator to be used.
     */
    private final Random uniformGenerator;

    /**
     * Parameter for triangular distribution.
     */
    private final double b;

    /**
     * Rest of the parameters if triangular distribution.
     */
    private final double a,c;

    /**
     * Constructor.
     *
     * @param uniformGenerator RNG with uniform distribution used to generate
     *                         numbers with triangular distribution.
     * @param b Triangular distribution parameter.
     */
    public SimpleTriangularDistribution(Random uniformGenerator, double b) {
        this.uniformGenerator = uniformGenerator;
        this.a = 0;
        this.b = b;
        this.c = 2*b;
    }

    @Override
    public double nextDouble() {
        double r = uniformGenerator.nextDouble();
        double val = (b-a)/(c-a);

        if (r < val) {
            return a + Math.sqrt(r*(c-a)*(b-a));
        } else {
            return c - Math.sqrt((1-r)*(c-a)*(c-b));
        }
    }

    @Override
    public double expectedMean() {
        return b;
    }

    @Override
    public double expectedVariance() {
        return (b*b) / 6.0;
    }

    @Override
    public double maxNumber() {
        return c;
    }
}
