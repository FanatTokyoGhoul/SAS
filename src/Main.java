import SA.FastFourierTransformation;
import SA.Function;

import javax.swing.JFrame;

import jfree.chart.ChartFactory;
import jfree.chart.ChartPanel;
import jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
    public static void main(String[] args) {
        Function<Float, Float> func = new Function<Float, Float>() {
            @Override
            public Float apply(Float[] variable) {
                return (float)Math.cos((variable[1] * 2 * Math.PI  * variable[0] ) / variable[2]);
            }
        };
        FastFourierTransformation fft = new FastFourierTransformation(func);
        Float[] res = fft.spectralAnalysis(0f,10f,0.1f, new Float[] {2f});

        XYSeries series = new XYSeries("cos(a)");

        float[] a = fft.func();
        for (int i = 1; i < 10; i+=1) {
            series.add(i, a[i]);
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("y = sin(x)", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);


        JFrame frame =
                new JFrame("График гармонического сигнала");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));

        frame.setSize(800, 600);
        frame.show();


    }
}
