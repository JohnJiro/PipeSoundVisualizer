package frame;

import java.awt.Point;

public class WaveViewer extends BaseFrame {
    public WaveViewer(Point size, Point pos, boolean show) {
        super(show ? "PSV | Wave Viewer" : null, size, pos);
    }

    public void set(int[][] samples, int depth) {
        byte[][] levelMatrix = new byte[height][width];
        int ch = samples.length;
        int length = samples[0].length;
        int range = height / ch;

        for (int c = 0; c < ch; c++) {
            int baseline = range / 2 + range * c;

            for (int x = 0; x < width; x++) {
                int s = (int) ((double) x / width * length);
                int level = (int) (range * ((double) samples[c][s] / depth));

                drawVerticalLine(levelMatrix, (byte) 255, x, baseline, baseline - level);
            }
        }

        draw(levelMatrix);
    }
}
