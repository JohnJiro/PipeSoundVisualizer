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
    private int contentWidth;
    private int contentHeight;

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
        contentWidth = width / main.frameColumn;
        contentHeight = height / ((main.FRAME_NUM - 1) / main.frameColumn + 1);

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
        Point size = new Point(contentWidth, contentHeight);
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
        paint(g, 0, waveViewer.canvas);
        paint(g, 1, amplitudeViewer.canvas);
        paint(g, 2, waveBalanceViewer.canvas);
        paint(g, 3, frequencyViewer.canvas);
        paint(g, 4, spectrogramViewer.canvas);
        paint(g, 5, frequencyBalanceViewer.canvas);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, outerWidth, outerHeight);

        super.paint(g);
    }

    public void paint(Graphics g, int index, BufferedImage canvas) {
        if (canvas == null) return;

        int x = insets.left + index % main.frameColumn * contentWidth;
        int y = insets.top + index / main.frameColumn * contentHeight;

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
            contentWidth = width / main.frameColumn;
            contentHeight = height / ((main.FRAME_NUM - 1) / main.frameColumn + 1);

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
