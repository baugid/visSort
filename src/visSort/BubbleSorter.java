package visSort;

/**
 * Created by Gideon on 15.05.2017.
 */
public class BubbleSorter implements Sorter {
    private boolean finished = false;
    private int[] array;
    private int step1;
    private int step2;

    @Override
    public boolean isFinished() {
        if(step2==array.length){
            finished=true;
        }
        return finished;
    }

    @Override
    public void addArray(int[] toBeSorted) {
        array = toBeSorted;
        step1=0;
        step2=1;
    }

    @Override
    public int[] getCurrentStatus() {
        return array;
    }

    @Override
    public void sortStep() {
        if(array==null){
            return;
        }
        int buf;
        if(array[step1]>array[step1+1]) {
            buf=array[step1];
            array[step1]=array[step1+1];
            array[step1+1]=buf;
        }
        step1++;
        if(step1==array.length-step2){
            step2++;
            step1=0;
        }
        if(step2==array.length){
            finished=true;
        }
    }
}
