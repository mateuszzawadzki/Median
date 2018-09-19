import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/*
    This is the implementation of sliding window with median.
    Window contains values stored in two binary heaps - lowerHalf and higherHalf.
    In my solution, I'm using PriorityQueue from Java Collection Framework which implements binary heap.
    lowerHalf heap stores values from lower half of the window and is sorted in reversed order,
    so the maximum value is on top.
    higherHalf heap stores values from higher half and has the minimum value on top.
    This allows me to always have access to the middle elements of the window, so the median can be calculated easily.

    Time complexity:
    n - size of the window
    Add, poll - O(log(n))
    Remove - O(n)
    Peek - O(1)

    In each iteration, values are added and removed from the heaps. This gives the algorithm O(n) complexity
    in both worst and best case scenarios.
 */

public class MedianWithHeaps implements Median {

    private Queue<Integer> lowerHalf = new PriorityQueue<>(Comparator.reverseOrder());
    private Queue<Integer> higherHalf = new PriorityQueue<>();


    @Override
    public void addDelay(int delay) {
        if (lowerHalf.isEmpty()) {
            lowerHalf.add(delay);
            return;
        } else if (higherHalf.isEmpty()) {
            higherHalf.add(delay);
            return;
        }

        if (delay < lowerHalf.peek()) {
            lowerHalf.add(delay);
        } else {
            higherHalf.add(delay);
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

    public void removeDelay(int toRemove) {
        if (toRemove <= lowerHalf.peek()) {
            lowerHalf.remove(toRemove);
        } else {
            higherHalf.remove(toRemove);
        }
    }

    @Override
    public double getMedian() {
        int lhSize = lowerHalf.size();
        int hhSize = higherHalf.size();
        double result;
        if (lhSize + hhSize == 1) {
            return -1.0;
        }
        if (lhSize > hhSize){
            result =  (double) lowerHalf.peek();
        } else if (lhSize == hhSize) {
            result =  (double) (lowerHalf.peek() + higherHalf.peek())/2;
        } else {
            result = (double) higherHalf.peek();
        }
        return result;
    }

    private void balanceQueues() {
        if (lowerHalf.size() > higherHalf.size()) {
            higherHalf.add(lowerHalf.poll());
        } else {
            lowerHalf.add(higherHalf.poll());
        }
    }
}
