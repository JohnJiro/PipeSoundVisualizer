package util;

// References:
// - https://hkawabata.github.io/technical-note/note/Algorithm/fft.html
// - http://wwwa.pikara.ne.jp/okojisan/stockham/cooley-tukey.html
// - https://www.logical-arts.jp/archives/112

public class FastFourierTransformer {
    public static double[][] getSpectrogram(int[][] samples, int depth) {
        int ch = samples.length;
        int length = samples[0].length / 2;
        double[][] spectrogram = new double[ch][length];

        for (int c = 0; c < ch; c++) {
            Complex[] points = calc(normalizeSamples(samples[c], depth));

            for (int i = 0; i < length; i++)
                spectrogram[c][i] = SoundMath.calcAmplitude(points[i].abs()) / 50;
        }

        return spectrogram;
    }

    private static double[] normalizeSamples(int[] samples, int depth) {
        int length = samples.length;
        double[] newSamples = new double[length];

        for (int s = 0; s < length; s++)
            newSamples[s] = SoundMath.calcHanWindow((double) s / length) * samples[s] / depth;

        return newSamples;
    }

    private static Complex[] calc(double[] samples) {
        int length = samples.length;
        Complex[] points = new Complex[length];

        for (int p = 0; p < length; p++) points[p] = new Complex(samples[p], 0).con();

        calc(points, length, 1, 0, 0);

        return points;
    }

    private static void calc(Complex[] points, int n, int s, int q, int d) {
        int m = n / 2;
        double t = 2 * Math.PI / n;

        if (n > 1) {
            for (int p = 0; p < m; p++) {
                Complex w = new Complex(Math.cos(p * t), -Math.sin(p * t));
                Complex a = new Complex(points[q + p]);
                Complex b = new Complex(points[q + p + m]);

                points[q + p] = a.plus(b);
                points[q + p + m] = a.minus(b).multiply(w);
            }

            calc(points, m, s * 2, q, d);
            calc(points, m, s * 2, q + m, d + s);
        } else if (q > d) {
            Complex pq = points[q];
            Complex pd = points[d];

            points[q] = pd;
            points[d] = pq;
        }
    }
}
