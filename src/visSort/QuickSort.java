package visSort;

/**
 * Created by Gideon on 15.05.2017.
 */
//TODO Quicksort implementieren und einbinden
public class QuickSort implements Sorter {
    @Override
    public Sorter generateNewInstance() {
        return new QuickSort();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void addArray(int[] toBeSorted) {

    }

    @Override
    public void resetSorting() {

    }

    @Override
    public int[] getCurrentStatus() {
        return new int[0];
    }

    @Override
    public void sortStep() {

    }
}
