import frame.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainFrame extends Frame {
    private Insets insets;
    private int width;
    private int height;
    private int outerWidth;
    private int outerHeight;

    private Main main;
    private WaveViewer waveViewer;
    private AmplitudeViewer amplitudeViewer;
    private WaveBalanceViewer waveBalanceViewer;
    private FrequencyViewer frequencyViewer;
    private SpectrogramViewer spectrogramViewer;
    private FrequencyBalanceViewer frequencyBalanceViewer;

    public MainFrame(Main main) {
        super(main.TITLE);

        // pack();

        insets = new Insets(28, 1, 1, 1); // getInsets();
        width = main.frameWidth;
        height = main.frameHeight;
        outerWidth = width + insets.left + insets.right;
        outerHeight = height + insets.top + insets.bottom;

        setLocation(insets.left + main.frameX, insets.top + main.frameY);
        setSize(outerWidth, outerHeight);
        setResizable(true);
        addWindowListener(new MyWindowAdapter());
        addComponentListener(new MyComponentListener());
        setVisible(true);

        this.main = main;

        resetViewer();
    }

    public void resetViewer() {
        Point size = new Point(width / 3, height / 2);
        Point pos = new Point(0, 0);

        waveViewer = new WaveViewer(size, pos, false);
        amplitudeViewer = new AmplitudeViewer(size, pos, false);
        waveBalanceViewer = new WaveBalanceViewer(size, pos, false);
        frequencyViewer = new FrequencyViewer(size, pos, false);
        spectrogramViewer = new SpectrogramViewer(size, pos, false);
        frequencyBalanceViewer = new FrequencyBalanceViewer(size, pos, false);
    }

    public void updateViewer() {
        if (main.samples != null) {
            waveViewer.set(main.samples, main.pcm.depth);
            amplitudeViewer.set(main.samples, main.pcm.depth);
            waveBalanceViewer.set(main.samples, main.pcm.depth);
        }

        if (main.spectrogram != null) {
            frequencyViewer.set(main.spectrogram);
            spectrogramViewer.set(main.spectrogram);
            frequencyBalanceViewer.set(main.spectrogram);
        }

        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g, 0, 0, waveViewer.canvas);
        paint(g, 0, 1, amplitudeViewer.canvas);
        paint(g, 0, 2, waveBalanceViewer.canvas);
        paint(g, 1, 0, frequencyViewer.canvas);
        paint(g, 1, 1, spectrogramViewer.canvas);
        paint(g, 1, 2, frequencyBalanceViewer.canvas);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, outerWidth, outerHeight);

        super.paint(g);
    }

    public void paint(Graphics g, int row, int col, BufferedImage canvas) {
        if (canvas == null) return;

        int x = insets.left + col * (width / 3);
        int y = insets.top + row * (height / 2);

        g.drawImage(canvas, x, y, this);
    }

    class MyWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class MyComponentListener implements ComponentListener {
        @Override
        public void componentResized(ComponentEvent e) {
            Component component = e.getComponent();

            insets = getInsets();
            outerWidth = component.getWidth();
            outerHeight = component.getHeight();
            width = outerWidth - insets.left - insets.right;
            height = outerHeight - insets.top - insets.bottom;

            resetViewer();
            updateViewer();
        }

        @Override
        public void componentMoved(ComponentEvent e) {}

        @Override
        public void componentShown(ComponentEvent e) {}

        @Override
        public void componentHidden(ComponentEvent e) {}
    }
}
