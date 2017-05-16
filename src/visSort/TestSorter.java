package visSort;

import java.util.Arrays;

/**
 * Created by Gideon on 14.05.2017.
 */
public class TestSorter implements Sorter {
    private boolean finished = false;
    private int[] array;

    @Override
    public Sorter generateNewInstance() {
        return new TestSorter();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void addArray(int[] toBeSorted) {
        array = toBeSorted;
        finished = false;
    }

    @Override
    public void resetSorting() {

    }

    @Override
    public int[] getCurrentStatus() {
        return array;
    }

    @Override
    public void sortStep() {
        Arrays.sort(array);
        finished = true;
    }
}
