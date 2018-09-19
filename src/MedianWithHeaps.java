import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class MedianWithHeaps {

    private List<Integer> data;
    private int windowSize;
    private Queue<Integer> lowerHalf = new PriorityQueue<>(Comparator.reverseOrder());
    private Queue<Integer> higherHalf = new PriorityQueue<>();

    public MedianWithHeaps(List<Integer> data, int windowSize) {
        this.data = data;
        this.windowSize = windowSize;
    }

    public void getMedianSlidingWindow() {
        for (int i = 0; i < data.size(); i++) {
            addToQueue(data.get(i));
            if (i >= windowSize) {
                removeFromQueue(data.get(i-windowSize));
            }
            printMedian();
        }
    }

    private void addToQueue(int value) {
        if (lowerHalf.isEmpty()) {
            lowerHalf.add(value);
            return;
        } else if (higherHalf.isEmpty()) {
            higherHalf.add(value);
            return;
        }

        if (value < lowerHalf.peek()) {
            lowerHalf.add(value);
        } else {
            higherHalf.add(value);
        }

        if (!higherHalf.isEmpty() && !lowerHalf.isEmpty()) {
            if (lowerHalf.peek() > higherHalf.peek()) {
                int lowerHalfMax = lowerHalf.poll();
                int higherHalfMin = higherHalf.poll();
                lowerHalf.add(higherHalfMin);
                higherHalf.add(lowerHalfMax);
            }
        }

        if (Math.abs(lowerHalf.size() - higherHalf.size()) == 2) {
            balanceQueues();
        }
    }

    private void removeFromQueue(int toRemove) {
        if (toRemove <= lowerHalf.peek()) {
            lowerHalf.remove(toRemove);
        } else {
            higherHalf.remove(toRemove);
        }
    }

    private void printMedian() {
        int lhSize = lowerHalf.size();
        int hhSize = higherHalf.size();
        if (lhSize + hhSize == 1) {
            System.out.println(-1);
            return;
        }
        if (lhSize > hhSize){
            System.out.println((double) lowerHalf.peek());
        } else if (lhSize == hhSize) {
            System.out.println((double) (lowerHalf.peek() + higherHalf.peek())/2);
        } else {
            System.out.println((double) higherHalf.peek());
        }
    }

    private void balanceQueues() {
        if (lowerHalf.size() > higherHalf.size()) {
            higherHalf.add(lowerHalf.poll());
        } else {
            lowerHalf.add(higherHalf.poll());
        }
    }
}
