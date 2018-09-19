import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/*
    This is the main class responsible for loading file and running algorithm.
 */

public class Main {

    static List<Integer> readCsv(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllLines(path).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read file " + filePath);
        }
    }

    public static void main(String[] args) {
        List<Integer> data;
        int windowSize;
        // if no arguments provided, use example data
        if (args.length > 1) {
            data = readCsv(args[0]);
            windowSize = Integer.valueOf(args[1]);
        } else {
            int[] exampleData = { 100, 102, 101, 110, 120, 115 };
            data = Arrays.stream(exampleData).boxed().collect(Collectors.toList());
            windowSize = 3;
        }

        MedianWithHeaps medianWithHeaps = new MedianWithHeaps();

        for (int i = 0; i < data.size(); i++) {
            medianWithHeaps.addDelay(data.get(i));
            if (i >= windowSize) {
                medianWithHeaps.removeDelay(data.get(i-windowSize));
            }
            System.out.println(medianWithHeaps.getMedian());
        }
    }
}
