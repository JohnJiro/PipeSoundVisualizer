package frame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class BaseFrame extends Frame {
    private final Insets insets;
    public final int width;
    public final int height;
    public final int outerWidth;
    public final int outerHeight;
    public final BufferedImage canvas;

    protected BaseFrame(String title, Point size, Point pos) {
        super(title);

        if (title != null) {
            // pack();

            insets = new Insets(28, 1, 1, 1); // getInsets();
        } else {
            insets = new Insets(0, 0, 0, 0);
        }

        width = size.x;
        height = size.y;
        outerWidth = width + insets.left + insets.right;
        outerHeight = height + insets.top + insets.bottom;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        if (title != null) {
            setLocation(insets.left + pos.x, insets.top + pos.y);
            setSize(outerWidth, outerHeight);
            setResizable(false);
            addWindowListener(new MyWindowAdapter());
            setVisible(true);
        }
    }

    protected void draw(byte[][] levelMatrix) {
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int level = levelMatrix[y][x] & 0xff;

                pixels[width * y + x] = 0xff000000 | level | (level << 8) | (level << 16);
            }
        }

        canvas.setRGB(0, 0, width, height, pixels, 0, width);
    }

    protected static void drawHorizontalLine(
            byte[][] levelMatrix, byte level, int y, int x1, int x2) {
        if (x1 <= x2) {
            for (int x = x1; x < x2; x++) levelMatrix[y][x] = level;
        } else {
            for (int x = x2; x < x1; x++) levelMatrix[y][x] = level;
        }
    }

    protected static void drawVerticalLine(
            byte[][] levelMatrix, byte level, int x, int y1, int y2) {
        if (y1 <= y2) {
            for (int y = y1; y < y2; y++) levelMatrix[y][x] = level;
        } else {
            for (int y = y2; y < y1; y++) levelMatrix[y][x] = level;
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if (canvas != null) g.drawImage(canvas, insets.left, insets.top, this);
    }

    class MyWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
