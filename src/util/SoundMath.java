package util;

public class SoundMath {
    public static double calcHanWindow(double x) {
        return 0.5 + 0.5 * Math.cos(2 * Math.PI * x);
    }

    public static double calcAmplitude(double x) {
        return 20 * Math.log10(x + 1);
    }

    public static double calcPower(double x) {
        return 10 * Math.log10(x * x + 1);
    }

    public static double calcMelScale(double x) {
        return calcMelScale(x * 24000, 700) / 4016.0773;
    }

    public static double calcMelScale(double f, double f0) {
        double m0 = 1000 / Math.log(1000 / f0 + 1);

        return m0 * Math.log(f / f0 + 1);
    }

    public static double calcInverseMelScale(double x) {
        return calcInverseMelScale(x * 4016.0773, 700) / 24000;
    }

    public static double calcInverseMelScale(double m, double f0) {
        double m0 = 1000 / Math.log(1000 / f0 + 1);

        return f0 * (Math.exp(m / m0) - 1);
    }
}
