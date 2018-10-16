import java.util.Random;

/**
 * Main class.
 *
 * Created by valesz on 16.10.2018.
 */
public class Main {

    /**
     * At least parameters are expected - number of generated numbers and distribution type.
     */
    public static final int MIN_PARAM_COUNT = 2;

    // indexes to array of passed arguments
    public static final int NUM_OF_NUMBERS_PARAM = 0;
    public static final int DISTRIBUTION_TYPE_PARAM = 1;

    // types of distribution
    public static final int ERLANG_DISTRIBUTION = 1;
    public static final int GEOMETRIC_DISTRIBUTION = 2;


    /**
     * Set to true if no program arguments are provided and program should
     * generate it's own parameters and run twice.
     */
    public static boolean noArguments = false;

    public static void main(String[] args) {
        try {
            parseArgs(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ;
        }

        if (noArguments) {
            // generate arguments

            // run

            // do it again
        } else {
            // run with arguments
        }

    }

    /**
     * Parses program arguments and throws exception if the arguments are incorrect.
     * Message of the thrown exception can be used as error message.
     *
     * If no arguments are provided, noArguments flag is set to indicate that program should generate some parameters
     * and use those.
     *
     * @param args Array of arguments to be parsed.
     */
    private static void parseArgs(String[] args) throws Exception{
        if (args == null || args.length == 0) {
            noArguments = true;
        }
    }

    /**
     * Generates random program arguments - number of generated numbers, type of distribution, distribution parameters...
     */
    private static void generateRandomArguments() {

    }

    /**
     * Generates n numbers with uniform distribution in range from 0 to 1 (exclusive).
     * @param n Count of generated numbers.
     * @return Generated numbers.
     */
    private static double[] generateRandomNumbers(int n) {
        double[] nums = new double[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            nums[i] = r.nextDouble();
        }

        return nums;
    }
}
