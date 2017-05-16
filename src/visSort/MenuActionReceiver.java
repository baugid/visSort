package visSort;

/**
 * Created by Gideon on 15.05.2017.
 */
public interface MenuActionReceiver {
    void useAlgorithm(String name);

    void pauseSorting();

    void beginSorting();

    void stopSorting();

    void receiveNewArray(int[] array);
}
