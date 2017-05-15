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
    private JMenuItem bubbleSort;
    private JMenuItem testSort;
    private MenuActionReceiver rec;

    public GUI() {
        field = new BarField();
        menu = new JMenuBar();
        settings = new JMenu("Settings");
        bubbleSort = new JMenuItem("Bubblesort");
        testSort = new JMenuItem("Testsort");

        menu.add(settings);
        settings.add(bubbleSort);
        settings.add(testSort);

        field.setPreferredSize(new Dimension(1200, 700));

        super.setJMenuBar(menu);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setTitle("VisSort");
        super.add(field);

        bubbleSort.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.useAlgorithm("bubble");
                }
            }
        });
        testSort.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rec != null) {
                    rec.useAlgorithm("testsort");
                }
            }
        });
        super.pack();
        super.setLocationRelativeTo(null);
        super.requestFocus();
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
