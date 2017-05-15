package visSort;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class VisMain implements MenuActionReceiver {
    private Sorter currentSorter;
    private GUI ui;
    private Dimension uiSize;

    public VisMain(String sorter) {
        switchSorter(sorter);
        ui = new GUI();
    }

    public void switchSorter(String sorter) {
        switch (sorter.toLowerCase()) {
            case "testsort":
                currentSorter = new TestSorter();
                break;
            case "bubblesort":
            case "bubble":
                currentSorter = new BubbleSorter();
                break;
            default:
                currentSorter = new TestSorter();
                break;
        }
    }

    public int[] generateArray(int length) {
        int[] out = new int[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++)
            out[i] = rand.nextInt(30) + 1;

        return out;
    }

    public Image generateImg(int[] field) {
        int barWidth = 50; //Breite (Berechnung?)
        int barDist = 100; //Abstand (Berechnung?)
        int pos = 0; //Position
        int mult = (uiSize.height / Arrays.stream(field).max().getAsInt()) - (uiSize.height / 10) / Arrays.stream(field).max().getAsInt(); //Aus dem Maximum und der Hoehe Laengenmult errechnen mit Platz nach unten minus einzehntel Margin
        BufferedImage buffer = new BufferedImage(uiSize.width, uiSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.getGraphics();
        for (int el : field) {
            g.fillRect(pos, 0, barWidth, el * mult);
            pos += barDist;
        }
        return buffer;
    }

    public void pause(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void start(int arrayLength) {
        start(generateArray(arrayLength));
    }

    public void start(int[] array) {
        currentSorter.addArray(array);
        ui.setVisible(true);
        uiSize = ui.getContentSize();
        ui.draw(generateImg(currentSorter.getCurrentStatus()));
    }

    public void start() {
        start(12);
    }

    public void sort() {
        pause(1000);
        while (!currentSorter.isFinished()) {
            currentSorter.sortStep();
            ui.draw(generateImg(currentSorter.getCurrentStatus()));
            pause(100);
        }
        JOptionPane.showMessageDialog(ui,"Array wurde sotiert","Fertig",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        VisMain visSort = new VisMain("bubble");
        visSort.start();
        visSort.sort();
    }

    @Override
    public void useAlgorithm(String name) {
        switchSorter(name);
    }
}