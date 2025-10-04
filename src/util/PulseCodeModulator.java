package util;

public class PulseCodeModulator {
    public final int freq;
    public final int bit;
    public final int ch;
    public final int chunkLength;
    public final int depth;

    public PulseCodeModulator(int freq, int bit, int ch) {
        this.freq = freq;
        this.bit = bit;
        this.ch = ch;
        chunkLength = bit / 8;
        depth = (int) Math.pow(2, bit);
    }

    public int[][] convertPCMDataToSamples(byte[] data, int dataLength) {
        int sampleLength = dataLength / ch / chunkLength;
        int[][] samples = new int[ch][sampleLength];

        for (int s = 0; s < sampleLength; s++) {
            for (int c = 0; c < ch; c++) {
                int offset = (s * ch + c) * chunkLength;
                int sample = (data[offset + chunkLength - 1] & 0x80) == 0 ? 0x00000000 : 0xffffffff;

                for (int i = 0; i < chunkLength; i++)
                    sample = (sample << 8) + (data[offset + chunkLength - 1 - i] & 0xff);

                samples[c][s] = sample;
            }
        }

        return samples;
    }

    public void pushSamples(int[][] currentSamples, int[][] newSamples) {
        int currentLength = currentSamples[0].length;
        int newLength = newSamples[0].length;
        int shift = currentLength - newLength;

        for (int c = 0; c < ch; c++) {
            if (shift > 0)
                for (int i = 0; i < shift; i++)
                    currentSamples[c][i] = currentSamples[c][i + newLength];

            for (int i = 0; i < currentLength && i < newLength; i++)
                currentSamples[c][currentLength - i - 1] = newSamples[c][newLength - i - 1];
        }
    }

    public int[][] sliceLatestSamples(int[][] samples, int length) {
        int[][] newSamples = new int[ch][length];
        int shift = samples[0].length - length;

        for (int c = 0; c < ch; c++)
            for (int i = 0; i < length; i++) newSamples[c][i] = samples[c][i + shift];

        return newSamples;
    }
}
