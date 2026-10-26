import jmarc.solving.MARCTemperature;

public class SimpleTemperature extends MARCTemperature {

    private double initialTemperature;
    private double alpha;

    public SimpleTemperature(double initialTemperature, double alpha) {
        this.initialTemperature = initialTemperature;
        this.alpha = alpha;
    }

    @Override
    public double temperature(int step) {
        return initialTemperature * Math.exp(-alpha * step);
    }
}