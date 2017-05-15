package visSort;

/**
 * Created by Gideon on 14.05.2017.
 */
public interface Sorter {
    boolean isFinished();

    void addArray(int[] toBeSorted);

    int[] getCurrentStatus();

    void sortStep();
}
