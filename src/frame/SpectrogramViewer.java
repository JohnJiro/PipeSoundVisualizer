package frame;

import util.SoundMath;

import java.awt.Point;

public class SpectrogramViewer extends BaseFrame {
    private final byte[][] levelMatrix;
    private int seq;

    public SpectrogramViewer(Point size, Point pos, boolean show) {
        super(show ? "PSV | Spectrogram Viewer" : null, size, pos);

        levelMatrix = new byte[height][width];
        seq = 0;
    }

    public void set(double[][] spectrogram) {
        int ch = spectrogram.length;
        int length = spectrogram[0].length;
        int range = height / ch;

        for (int c = 0; c < ch; c++) {
            int shift = range * c;

            for (int i = 1; i < range; i++) {
                int s = (int) (SoundMath.calcInverseMelScale((double) i / range) * length);
                byte level = (byte) (spectrogram[c][s] * 255);
                int y = shift + range - i - 1;

                levelMatrix[y][seq] = level;
            }
        }

        seq = seq < width - 1 ? seq + 1 : 0;

        draw(levelMatrix);
    }
}
