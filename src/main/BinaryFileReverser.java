package main;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static java.lang.Math.min;

/**
 * A class that reverses and writes a binary file to another file
 * <p>
 *
 * @author Nypiaka
 */
public class BinaryFileReverser {
    private static final int BUFFER_SIZE = 1024 * 1024;

    private final Path input;

    private final Path output;

    /**
     * Creates a new BinaryFileReverser with the given input file name, output file name, and size of buffer.
     *
     * @param inputFile  name of input file
     * @param outputFile name of output file
     * @throws ReverseException when input file doesn't exist or output file can not be created
     */
    public BinaryFileReverser(String inputFile, String outputFile) throws ReverseException {
        input = getPath(inputFile, "incorrect input path");
        if (!Files.exists(input)) {
            throw new ReverseException("input file doesn't exists");
        }
        output = getPath(outputFile, "incorrect output path");
        createOutputFileDirIfNotExist(output);
    }

    /**
     * Performs a byte-by-byte reversal of the input binary file and writes the contents to the output file.
     *
     * @throws ReverseException if an I/O error occurs or no permissions to write to the output file
     */

    public void reverse() throws ReverseException {
        try (var input = new RandomAccessFile(this.input.toFile(), "r");
             var output = Files.newOutputStream(this.output)
        ) {
            var size = input.length();
            var iterator = 0;
            var buffer = new byte[(int) min(BUFFER_SIZE, size)];
            for (var i = size; i > 0; i -= BUFFER_SIZE) {
                if (i < buffer.length) {
                    buffer = new byte[(int) i];
                }
                input.seek(size - iterator - buffer.length);
                input.read(buffer);
                reverseBytes(buffer);
                iterator += buffer.length;
                output.write(buffer);
            }
        } catch (SecurityException | IOException e) {
            throw new ReverseException(e);
        }
    }

    private void reverseBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length / 2; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[bytes.length - 1 - i];
            bytes[bytes.length - 1 - i] = temp;
        }
    }

    private void createOutputFileDirIfNotExist(Path outputPath) throws ReverseException {
        try {
            Path outputPathParent = outputPath.getParent();
            if (outputPathParent != null) {
                Files.createDirectories(outputPathParent);
            }
        } catch (IOException | SecurityException e) {
            throw new ReverseException("failed create parent path for output file: " + e.getMessage());
        }
    }

    private Path getPath(String path, String errorMessage) throws ReverseException {
        try {
            return Path.of(path);
        } catch (InvalidPathException e) {
            throw new ReverseException(errorMessage, e);
        }
    }
}

