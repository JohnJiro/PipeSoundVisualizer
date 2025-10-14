import frame.*;

import util.*;

import java.io.*;

public class Main {
    public static final String TITLE = "Pipe Sound Visualizer";
    public static final String VERSION = "1.1.0 [DEV]";

    public int freq = 48000; // DAT Standard
    public int bit = 16; // DAT Standard
    public int ch = 2; // DAT Standard
    public int bufferedSampleLength = 800; // = 24000 Hz / 30 Hz
    public int spectrogramLength = 1024;
    public int frameWidth = 400;
    public int frameHeight = 400;
    public int frameX = 10;
    public int frameY = 10;
    public boolean useMainFrame = false;

    public PulseCodeModulator pcm;
    private boolean isRichData;
    private int[][] bufferedSamples;
    public int[][] samples;
    public double[][] spectrogram;

    private MainFrame mainFrame;
    private SubFrame subFrame;

    public static void main(String[] args) {
        new Main(args).run();
    }

    public Main(String[] args) {
        Option.set(this, args);

        pcm = new PulseCodeModulator(freq, bit, ch);
        isRichData = bufferedSampleLength >= spectrogramLength;

        if (isRichData) {
            bufferedSamples = new int[ch][bufferedSampleLength];
            samples = bufferedSamples;
        } else {
            bufferedSamples = new int[ch][spectrogramLength];
            samples = new int[ch][bufferedSampleLength];
        }

        if (useMainFrame) {
            mainFrame = new MainFrame(this);
        } else {
            subFrame = new SubFrame(this);
        }
    }

    private void run() {
        byte[] data = new byte[ch * pcm.chunkLength * bufferedSampleLength];
        int dataLenght;

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(System.in);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(System.out);

            while ((dataLenght = bufferedInputStream.read(data)) != -1) {
                pushData(data, dataLenght);

                if (useMainFrame) {
                    mainFrame.updateViewer();
                } else {
                    subFrame.updateViewer();
                }

                bufferedOutputStream.write(data, 0, dataLenght);
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pushData(byte[] data, int dataLenght) {
        pcm.pushSamples(bufferedSamples, pcm.convertPCMDataToSamples(data, dataLenght));

        int[][] samplesOfSpectrogram;

        if (isRichData) {
            samplesOfSpectrogram = pcm.sliceLatestSamples(bufferedSamples, spectrogramLength);
        } else {
            samplesOfSpectrogram = bufferedSamples;
            samples = pcm.sliceLatestSamples(bufferedSamples, bufferedSampleLength);
        }

        spectrogram = FastFourierTransformer.getSpectrogram(samplesOfSpectrogram, pcm.depth);
    }
}
