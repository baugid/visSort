package visSort;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class GUI extends JFrame {
    BarField field;

    public GUI() {
        field = new BarField();
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.requestFocus();
        super.setSize(new Dimension(1200, 700));
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setTitle("VisSort");
        super.add(field);
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
