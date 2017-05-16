package visSort;

import java.util.ArrayList;

/**
 * Created by Gideon on 14.05.2017.
 */
public interface Sorter {
    Sorter generateNewInstance();

    boolean isFinished();

    void addArray(int[] toBeSorted);

    void resetSorting();

    int[] getCurrentStatus();

    void sortStep();

    ArrayList<Integer> selectedElements();
}
