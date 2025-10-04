package frame;

import util.SoundMath;

import java.awt.Point;

public class FrequencyBalanceViewer extends BaseFrame {
    public FrequencyBalanceViewer(Point size, Point pos, boolean show) {
        super(show ? "PSV | Frequency Balance Viewer" : null, size, pos);
    }

    public void set(double[][] spectrogram) {
        byte[][] levelMatrix = new byte[height][width];
        int ch = spectrogram.length;
        int length = spectrogram[0].length;
        double[] spectrogramDiff = new double[length];

        for (int c = 0; c < ch; c++) {
            for (int i = 0; i < length; i++) {
                double levelAbs = spectrogram[c][i] >= 0 ? spectrogram[c][i] : -spectrogram[c][i];

                spectrogramDiff[i] += c % 2 == 0 ? levelAbs : -levelAbs;
            }
        }

        int baseline = width / 2;

        for (int i = 1; i < height; i++) {
            int s = (int) (SoundMath.calcInverseMelScale((double) i / height) * length);
            int level = (int) (spectrogramDiff[s] * width / 2);
            int y = height - i - 1;

            drawHorizontalLine(levelMatrix, (byte) 255, y, baseline, baseline - level);
        }

        draw(levelMatrix);
    }
}
