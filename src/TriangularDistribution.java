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
public class TriangularDistribution implements IDistributionGenerator {

    /**
     * Generator to be used.
     */
    private final Random uniformGenerator;

    /**
     * Parameter for triangular distribution.
     */
    private final int b;

    /**
     * Rest of the parameters if triangular distribution.
     */
    private final int a,c;

    /**
     * Constructor.
     *
     * @param uniformGenerator RNG with uniform distribution used to generate
     *                         numbers with triangular distribution.
     * @param b Triangular distribution parameter.
     */
    public TriangularDistribution(Random uniformGenerator, int b) {
        this.uniformGenerator = uniformGenerator;
        this.b = b;
        this.a = 0;
        this.c = 2*b;
    }

    @Override
    public int nextInt() {
        // todo: implement triangular distribution
        return 0;
    }

    @Override
    public double expectedMean() {
        return (a+b+c)/3.0;
    }

    @Override
    public double expectedVariance() {
        return (a*a + b*b + c*c - a*b - a*c -b*c) / 18.0;
    }
}
