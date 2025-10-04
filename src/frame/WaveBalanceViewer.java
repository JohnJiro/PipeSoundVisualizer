package frame;

import java.awt.Point;

public class WaveBalanceViewer extends BaseFrame {
    public WaveBalanceViewer(Point size, Point pos, boolean show) {
        super(show ? "PSV | Wave Balance Viewer" : null, size, pos);
    }

    public void set(int[][] samples, int depth) {
        byte[][] levelMatrix = new byte[height][width];
        int ch = samples.length;
        int length = samples[0].length;
        int[] samplesDiff = new int[length];

        for (int c = 0; c < ch; c++) {
            for (int i = 0; i < length; i++) {
                int level = (int) (height * ((double) samples[c][i] / depth));
                int levelAbs = level >= 0 ? level : -level;

                samplesDiff[i] += c % 2 == 0 ? levelAbs : -levelAbs;
            }
        }

        int baseline = height / 2;

        for (int x = 0; x < width; x++) {
            int s = (int) ((double) x / width * length);
            int level = samplesDiff[s];

            drawVerticalLine(levelMatrix, (byte) 255, x, baseline, baseline - level);
        }

        draw(levelMatrix);
    }
}
