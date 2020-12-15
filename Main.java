import cs2030.simulator.Simulation;

/**
 * Pass in command line arguments into the Simulation.
 */
public class Main {

    /**
     * Main method to take in command line arguments and update the default
     * input array based on the number of command line arguments.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        String[] input = new String[] {"1", "1", "0", "1", "1", "1", "1", "0",
            "0", "0"};
        int len = args.length;
        switch (len) {
            case 5:
                input = handle5Input(args, input);
                break;

            case 6:
                input = handle6Input(args, input);
                break;

            case 8:
                input = handle8Input(args, input);
                break;

            case 9:
                input = handle9Input(args, input);
                break;

            case 10:
                input = args;
                break;

            default:
                break;
        }
        Simulation s = new Simulation(input);
        s.run();
    }

    /**
     * Update the input String array with the command line argument for: (i)
     * base seed of the RandomGenerator. (ii) number of servers. (iii) number
     * of customers. (iv) arrival rate. (v) service rate.
     * @param args inputs used to update the input array.
     * @param input the input array to be updated.
     * @return updated input array for the simulation.
     */
    private static String[] handle5Input(String[] args, String[] input) {
        input[0] = args[0];
        input[1] = args[1];
        input[4] = args[2];
        input[5] = args[3];
        input[6] = args[4];
        return input;
    }

    /**
     * Update the input String array with the command line argument for: (i)
     * base seed for RandomGenerator. (ii) number of servers. (iii) maximum
     * queue length. (iv) number of customers. (v) arrival rate. (vi) service
     * rate.
     * @param args inputs used to update the input array.
     * @param input the input array to be updated.
     * @return updated input array for the simulation.
     */
    private static String[] handle6Input(String[] args, String[] input) {
        input[0] = args[0];
        input[1] = args[1];
        input[3] = args[2];
        input[4] = args[3];
        input[5] = args[4];
        input[6] = args[5];
        return input;
    }

    /**
     * Update the input String array with the command line argument for: (i)
     * base seed for RandomGenerator. (ii) number of servers. (iii) maximum
     * queue length. (iv) number of customers. (v) arrival rate. (vi) service
     * rate. (vii) resting rate. (viii) probability of resting.
     * @param args inputs used to update the input array.
     * @param input the input array to be updated.
     * @return updated input array for the simulation.
     */
    private static String[] handle8Input(String[] args, String[] input) {
        input[0] = args[0];
        input[1] = args[1];
        input[3] = args[2];
        input[4] = args[3];
        input[5] = args[4];
        input[6] = args[5];
        input[7] = args[6];
        input[8] = args[7];
        return input;
    }

    /**
     * Update the input String array with the command line argument for: (i)
     * base seed for RandomGenerator. (ii) number of servers. (iii) number of
     * self-checkout counters. (iv) maximum queue length. (v) number of
     * customers. (vi) arrival rate. (vii) service rate. (viii) resting rate.
     * (ix) probability of resting.
     * @param args inputs used to update the input array.
     * @param input the input array to be updated.
     * @return updated input array for the simulation.
     */
    private static String[] handle9Input(String[] args, String[] input) {
        input[0] = args[0];
        input[1] = args[1];
        input[2] = args[2];
        input[3] = args[3];
        input[4] = args[4];
        input[5] = args[5];
        input[6] = args[6];
        input[7] = args[7];
        input[8] = args[8];
        return input;
    }
}
