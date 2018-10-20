import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Main class.
 *
 * Created by valesz on 16.10.2018.
 */
public class Main {

    // indexes to array of passed arguments
    public static final int NUM_OF_NUMBERS_PARAM = 0;
    public static final int DISTRIBUTION_PARAM = 1;

    /**
     * Set to true if no program arguments are provided and program should
     * generate it's own parameters and run twice.
     */
    public static boolean noArguments = false;

    /**
     * Count of numbers to generate.
     */
    private static int countToGenerate = 0;

    /**
     * 'b' parameter of triangular distribution.
     */
    private static double triangDistribParam = 0;

    public static void main(String[] args) {
        try {
            parseArgs(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ;
        }

        RngDistributionStatisticsRunner runner;
        Random uniformGenerator = new Random();
        if (noArguments) {

            // 1st set of prepared arguments
            generateArguments1();
            runner = prepareRunner(uniformGenerator, countToGenerate, triangDistribParam);
            runner.run();
            printResults(runner);

            // 2nd set of prepared arguments
            generateArguments2();
            runner = prepareRunner(uniformGenerator, countToGenerate, triangDistribParam);
            runner.run();
            printResults(runner);

        } else {
            // run with arguments
            runner = prepareRunner(uniformGenerator, countToGenerate, triangDistribParam);
            runner.run();
            printResults(runner);
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
            return;
        }

        try {
            countToGenerate = Integer.parseInt(args[NUM_OF_NUMBERS_PARAM]);
        } catch (Exception ex) {
            throw new Exception("Error while parsing the first parameter ("+args[NUM_OF_NUMBERS_PARAM]+"): ["+ex.getClass().getName()+"] "+ex.getMessage());
        }

        try {
            // use Locale.US form decimal dot
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            Number num = format.parse(args[DISTRIBUTION_PARAM]);
            triangDistribParam = num.doubleValue();
        } catch (Exception ex) {
            throw new Exception("Error while parsing the second parameter ("+args[DISTRIBUTION_PARAM]+"): ["+ex.getClass().getName()+"] "+ex.getMessage());
        }
    }

    /**
     * Generate first set of arguments to demonstrate functionality.
     * Used if no program parameters are provided.
     */
    private static void generateArguments1() {
        countToGenerate = 100000;
        triangDistribParam = 10;
    }

    /**
     * Generate second set of arguments to demonstrate functionality.
     * Used if no program parameters are provided.
     */
    private static void generateArguments2() {
        countToGenerate = 1000;
        triangDistribParam = 5;
    }

    /**
     * Creates runner with triangular distribution generator.
     * @param uniformGenerator Uniform generator.
     * @param numberCount How many number should be generated.
     * @param distribParam Triangular distribution parameter.
     */
    private static RngDistributionStatisticsRunner prepareRunner(Random uniformGenerator, int numberCount, double distribParam) {
        return new RngDistributionStatisticsRunner(numberCount, new TriangularDistribution(uniformGenerator, distribParam));
    }

    /**
     * Prints results from given runner.
     * @param runner
     */
    private static void printResults(RngDistributionStatisticsRunner runner) {
        System.out.printf("E_teorie=%f\n", runner.getExpectedMean());
        System.out.printf("D_teorie=%f\n", runner.getExpectedVariance());
        System.out.printf("E_vypocet=%f\n", runner.getMean());
        System.out.printf("D_vypocet=%f\n", runner.getVariance());
        System.out.println();

        int countModif = Math.max(1,runner.getNumberCount() / 100);
        for(Integer d : runner.getHistogram().keySet()) {
            int numOfChars = runner.getHistogram().get(d) / countModif;
            if (numOfChars > 0) {
                System.out.printf("%d: ", d);
            }
            for (int i = 0; i < numOfChars; i++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }



}
