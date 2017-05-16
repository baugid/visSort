package visSort;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class VisMain extends Thread implements MenuActionReceiver {
    private Sorter currentSorter;
    private GUI ui;
    private Dimension uiSize;
    private boolean isSorting = false;
    private volatile boolean shouldPause = false;
    private volatile boolean shouldStop = false;
    private HashMap<String, Sorter> sorterHashMap;

    private void addAllSorter() {
        sorterHashMap.put("Bubblesort", new BubbleSorter());
        sorterHashMap.put("Testsort", new TestSorter());
    }

    public VisMain(String sorter) {
        sorterHashMap = new HashMap<>();
        addAllSorter();
        switchSorter(sorter);
        ui = new GUI(sorterHashMap.keySet().stream().toArray(size -> new String[size]));
    }


    public void switchSorter(String sorter) {
        int[] array = null;
        if (currentSorter != null && currentSorter.getCurrentStatus() != null) {
            array = currentSorter.getCurrentStatus();
        }
        currentSorter = sorterHashMap.get(sorter);
        if (currentSorter == null) {
            currentSorter = new TestSorter();
        } else {
            currentSorter = currentSorter.generateNewInstance();
        }
        currentSorter.addArray(array);
    }

    public int[] generateArray(int length) {
        int[] out = new int[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++)
            out[i] = rand.nextInt(30) + 1;

        return out;
    }

    private Image generateImg() {
        int[] field = currentSorter.getCurrentStatus();
        int barWidth = 50; //Breite (Berechnung?)
        int barDist = 100; //Abstand (Berechnung?)
        int pos = 0; //Position
        int mult = (uiSize.height / Arrays.stream(field).max().getAsInt()) - (uiSize.height / 10) / Arrays.stream(field).max().getAsInt(); //Aus dem Maximum und der Hoehe Laengenmult errechnen mit Platz nach unten minus einzehntel Margin
        ArrayList<Integer> markedVals = currentSorter.selectedElements();
        if (markedVals == null) {
            markedVals = new ArrayList<>();
        }
        BufferedImage buffer = new BufferedImage(uiSize.width, uiSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.getGraphics();
        for (int i = 0; i < field.length; i++) {
            g.setColor(Color.WHITE);
            if (markedVals.indexOf(i) != -1) {
                g.setColor(Color.YELLOW);
            }
            g.fillRect(pos, 0, barWidth, field[i] * mult);
            pos += barDist;
        }
        return buffer;
    }

    private void pause(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void init(int arrayLength) {
        init(generateArray(arrayLength));
    }

    public void init(int[] array) {
        currentSorter.addArray(array);
        ui.addMenuActionReceiver(this);
        ui.setVisible(true);
        uiSize = ui.getContentSize();
        ui.draw(generateImg());
    }

    public void init() {
        init(12);
    }

    public synchronized void sort() {
        while (!currentSorter.isFinished()) {
            currentSorter.sortStep();
            ui.draw(generateImg());
            shouldBePaused();
            pause(100);
            shouldBePaused();
            if (shouldStop) {
                shouldStop = false;
                currentSorter.resetSorting();
                return;
            }
        }
        JOptionPane.showMessageDialog(ui, "Array wurde sortiert", "Fertig", JOptionPane.INFORMATION_MESSAGE);
    }

    public synchronized void shouldBePaused() {
        if (shouldPause) {
            shouldPause = false;
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void run() {
        init();
        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isSorting = true;
            sort();
            isSorting = false;
        }
    }

    public static void main(String[] args) {
        VisMain visSort = new VisMain("bubblesort");
        visSort.start();
    }

    @Override
    public void useAlgorithm(String name) {
        if (isSorting)
            return;
        switchSorter(name);
    }

    @Override
    public void pauseSorting() {
        shouldPause = true;
    }

    @Override
    public synchronized void beginSorting() {
        notifyAll();
    }

    @Override
    public void stopSorting() {
        shouldStop = true;
    }

    @Override
    public void receiveNewArray(int[] array) {
        if (isSorting)
            return;
        if (array == null) {
            array = generateArray(currentSorter.getCurrentStatus().length);
        }
        currentSorter.addArray(array);
        ui.draw(generateImg());
    }
}
