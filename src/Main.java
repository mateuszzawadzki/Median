import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static List<Integer> readCsv(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path).stream()
                .filter(line -> !line.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        List<Integer> data;
        int windowSize;
        if (args.length > 1) {
            data = readCsv(args[0]);
            windowSize = Integer.valueOf(args[1]);
        } else {
            int[] exampleData = { 100, 102, 101, 110, 120, 115 };
            data = Arrays.stream(exampleData).boxed().collect(Collectors.toList());
            windowSize = 3;
        }

        new MedianWithHeaps(data, windowSize).getMedianSlidingWindow();
    }
}
