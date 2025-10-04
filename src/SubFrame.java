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

        Point p00 = new Point(main.frameX, main.frameY);
        waveViewer = new WaveViewer(size, p00, true);

        Point p01 = new Point(p00.x + waveViewer.outerWidth, p00.y);
        amplitudeViewer = new AmplitudeViewer(size, p01, true);

        Point p02 = new Point(p01.x + amplitudeViewer.outerWidth, p00.y);
        waveBalanceViewer = new WaveBalanceViewer(size, p02, true);

        Point p10 = new Point(p00.x, p00.y + waveViewer.outerHeight);
        frequencyViewer = new FrequencyViewer(size, p10, true);

        Point p11 = new Point(p10.x + frequencyViewer.outerWidth, p10.y);
        spectrogramViewer = new SpectrogramViewer(size, p11, true);

        Point p12 = new Point(p11.x + spectrogramViewer.outerWidth, p10.y);
        frequencyBalanceViewer = new FrequencyBalanceViewer(size, p12, true);
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
