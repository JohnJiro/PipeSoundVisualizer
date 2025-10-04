package util;

public class Complex {
    public final double a;
    public final double b;

    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Complex(Complex c) {
        a = c.a;
        b = c.b;
    }

    public Complex plus(Complex c) {
        return new Complex(a + c.a, b + c.b);
    }

    public Complex minus(Complex c) {
        return new Complex(a - c.a, b - c.b);
    }

    public Complex multiply(Complex c) {
        return new Complex(a * c.a - b * c.b, a * c.b + b * c.a);
    }

    public Complex con() {
        return new Complex(a, -b);
    }

    public double abs() {
        return Math.sqrt(a * a + b * b);
    }

    public Complex exp() {
        double r = Math.exp(a);

        return new Complex(r * Math.cos(b), r * Math.sin(b));
    }
}
