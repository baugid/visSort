package visSort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class GUI extends JFrame {
    private BarField field;
    private JMenuBar menu;
    private JMenu settings;
    private JMenu chooseSorter;
    private JMenuItem[] sorterEntries;
    private JMenuItem randArray;
    private JMenu start;
    private JMenu pause;
    private JMenu stop;
    private JMenu step;
    private MenuActionReceiver rec;
    private final String[] sorter;

    public GUI(String[] sorter) {
        field = new BarField();
        menu = new JMenuBar();
        settings = new JMenu("Settings");
        chooseSorter = new JMenu("Algorithmus");
        sorterEntries = new JMenuItem[sorter.length];
        randArray = new JMenuItem("neues Zufallarray");
        start = new JMenu("Start");
        pause = new JMenu("Pause");
        stop = new JMenu("Stop");
        step = new JMenu("Schritt");
        this.sorter = sorter;

        menu.add(settings);
        settings.add(chooseSorter);
        settings.add(randArray);
        menu.add(start);
        menu.add(pause);
        menu.add(stop);
        menu.add(step);

        for (int i = 0; i < sorter.length; i++) {
            sorterEntries[i] = new JMenuItem(sorter[i]);
            chooseSorter.add(sorterEntries[i]);
        }

        field.setPreferredSize(new Dimension(1200, 700));

        super.setJMenuBar(menu);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setTitle("VisSort");
        super.add(field);

        setMouseListeners();

        super.pack();
        super.setLocationRelativeTo(null);
        super.requestFocus();
    }

    private void setMouseListeners() {
        for (int i = 0; i < sorter.length; i++) {
            final int finalI = i;
            sorterEntries[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (rec != null) {
                        rec.useAlgorithm(sorter[finalI]);
                    }
                }
            });
        }
        randArray.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.receiveNewArray(null);
                }
            }
        });
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.beginSorting();
                }
            }
        });
        pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.pauseSorting();
                }
            }
        });
        stop.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.stopSorting();
                }
            }
        });
        step.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.singleStep();
                }
            }
        });
    }

    public void addMenuActionReceiver(MenuActionReceiver rec) {
        this.rec = rec;
    }

    public void draw(Image img) {
        field.setImage(img);
        field.repaint();
    }


    public Dimension getContentSize() {
        return new Dimension(field.getWidth(), field.getHeight());
    }

    private class BarField extends JPanel {
        private Image img;

        public void setImage(Image img) {
            this.img = img;
        }

        @Override
        public void update(Graphics g) {
            if (img == null) {
                img = new BufferedImage(1200, 700, BufferedImage.TYPE_INT_RGB);
            }
            g.drawImage(img, 0, 0, this);
        }

        @Override
        public void paint(Graphics g) {
            update(g);
        }
    }
}
