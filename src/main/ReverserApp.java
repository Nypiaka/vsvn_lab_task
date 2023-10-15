package main;

/**
 * A class that accepts console arguments and performs a byte-by-byte reverse of a binary file and
 * then writes it to the output file
 * <p>
 *
 * @author Nypiaka
 */
public class ReverserApp {

    private static final String EXPECTED_INPUT = " Expected: <input file> <output file>";

    /**
     * The application itself, which validates input parameters and bytes rotates the file.
     *
     * @param args command line arguments in the format <input file> <output file>
     */
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Input format exception: no input and output files names" + EXPECTED_INPUT);
            return;
        }
        if (args.length == 1) {
            System.err.println("Input format exception: no output file name" + EXPECTED_INPUT);
            return;
        }
        if (args[0] == null) {
            System.err.println("Input format exception: no input file name" + EXPECTED_INPUT);
            return;
        }
        if (args[1] == null) {
            System.err.println("Input format exception: no output file name" + EXPECTED_INPUT);
            return;
        }
        try {
            BinaryFileReverser reverser = new BinaryFileReverser(args[0], args[1]);
            reverser.reverse();
        } catch (ReverseException e) {
            System.err.println("Reversing exception: " + e.getMessage());
        }
    }

}
