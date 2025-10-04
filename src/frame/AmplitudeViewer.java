package frame;

import util.SoundMath;

import java.awt.Point;

public class AmplitudeViewer extends BaseFrame {
    private byte[][] levelMatrix;
    private int seq;

    public AmplitudeViewer(Point size, Point pos, boolean show) {
        super(show ? "PSV | Amplitude Viewer" : null, size, pos);

        levelMatrix = new byte[height][width];
        seq = 0;
    }

    public void set(int[][] samples, int depth) {
        int ch = samples.length;
        int length = samples[0].length;
        int range = height / ch;

        if (seq == 0) levelMatrix = new byte[height][width];

        for (int c = 0; c < ch; c++) {
            double powerSum = 0;

            for (int i = 0; i < length; i++)
                powerSum += SoundMath.calcPower((double) samples[c][i] / depth);

            int end = (c + 1) * range;
            int start = end - (int) (powerSum / length * range);

            drawVerticalLine(levelMatrix, (byte) 255, seq, start, end);
        }

        seq = seq < width - 1 ? seq + 1 : 0;

        draw(levelMatrix);
    }
}
