import frame.*;

import java.awt.Point;

public class SubFrame {
    private Main main;
    private WaveViewer waveViewer;
    private AmplitudeViewer amplitudeViewer;
    private WaveBalanceViewer waveBalanceViewer;
    private FrequencyViewer frequencyViewer;
    private SpectrogramViewer spectrogramViewer;
    private FrequencyBalanceViewer frequencyBalanceViewer;

    public SubFrame(Main main) {
        this.main = main;

        Point size = new Point(main.frameWidth, main.frameHeight);
        Point pos = new Point(main.frameX, main.frameY);

        waveViewer = new WaveViewer(size, pos, true);

        pos = setNextPosition(pos, 1, waveViewer.outerWidth, waveViewer.outerHeight);

        amplitudeViewer = new AmplitudeViewer(size, pos, true);

        pos = setNextPosition(pos, 2, amplitudeViewer.outerWidth, amplitudeViewer.outerHeight);

        waveBalanceViewer = new WaveBalanceViewer(size, pos, true);

        pos = setNextPosition(pos, 3, waveBalanceViewer.outerWidth, waveBalanceViewer.outerHeight);

        frequencyViewer = new FrequencyViewer(size, pos, true);

        pos = setNextPosition(pos, 4, frequencyViewer.outerWidth, frequencyViewer.outerHeight);

        spectrogramViewer = new SpectrogramViewer(size, pos, true);

        pos = setNextPosition(pos, 5, spectrogramViewer.outerWidth, spectrogramViewer.outerHeight);

        frequencyBalanceViewer = new FrequencyBalanceViewer(size, pos, true);
    }

    private Point setNextPosition(
            Point prevPos, int index, int prevFrameWidth, int prevFrameHeight) {
        if (index % main.frameColumn != 0) {
            return new Point(prevPos.x + prevFrameWidth, prevPos.y);
        } else {
            return new Point(main.frameX, prevPos.y + prevFrameHeight);
        }
    }

    public void updateViewer() {
        waveViewer.set(main.samples, main.pcm.depth);
        waveViewer.repaint();

        amplitudeViewer.set(main.samples, main.pcm.depth);
        amplitudeViewer.repaint();

        waveBalanceViewer.set(main.samples, main.pcm.depth);
        waveBalanceViewer.repaint();

        frequencyViewer.set(main.spectrogram);
        frequencyViewer.repaint();

        spectrogramViewer.set(main.spectrogram);
        spectrogramViewer.repaint();

        frequencyBalanceViewer.set(main.spectrogram);
        frequencyBalanceViewer.repaint();
    }
}
