package frame;

import util.SoundMath;

import java.awt.Point;

public class FrequencyViewer extends BaseFrame {
    public FrequencyViewer(Point size, Point pos, boolean show) {
        super(show ? "PSV | Frequency Analyzer" : null, size, pos);
    }

    public void set(double[][] spectrogram) {
        byte[][] levelMatrix = new byte[height][width];
        int ch = spectrogram.length;
        int length = spectrogram[0].length;
        int range = height / ch;

        for (int c = 0; c < ch; c++) {
            int shift = range * c;

            for (int i = 1; i < range; i++) {
                int s = (int) (SoundMath.calcInverseMelScale((double) i / range) * length);
                int start = (int) (width - spectrogram[c][s] * width);
                int end = width;
                int y = shift + range - i - 1;

                drawHorizontalLine(levelMatrix, (byte) 255, y, start, end);
            }
        }

        draw(levelMatrix);
    }
}
