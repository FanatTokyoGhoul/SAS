import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Frame extends JFrame {

    private JPanel panel = new JPanel();
    private JPanel fftPanel = new JPanel();
    private JPanel signalsPanel = new JPanel();

    public Frame() throws NullPointerException, IOException {
        this.setTitle("Task 1");
        this.pack();
        panel.add(signalsPanel);
        panel.add(fftPanel);
        this.setContentPane(panel);

        signalsPanel.add(drawGraph("\n(Frequency = 1 HZ)", 1));
        signalsPanel.add(drawGraph("\n(Frequency = 2 HZ)", 2));
        signalsPanel.add(drawGraph("\n(Frequency = 4 HZ)", 4));
        signalsPanel.add(drawGraph("\n(Frequency = 8 HZ)", 8));

        fftPanel.add(drawFFT("(Frequency = 1 HZ)", new Float[]{1f}));
        fftPanel.add(drawFFT("(Frequency = 2 HZ)", new Float[]{2f}));
        fftPanel.add(drawFFT("(Frequency = 4 HZ)", new Float[]{4f}));
        fftPanel.add(drawFFT("(Frequency = 8 HZ)", new Float[]{8f}));

        panel.updateUI();
    }

    public static void main(String[] args) throws IOException {
        Frame f = new Frame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(1024, 900);
        f.setVisible(true);
    }

    public ChartPanel drawGraph(String frequencyString, int frequency) {
        XYSeries series = new XYSeries("A*cos(2*pi*t*frequency)");

        for (float i = 1; i < 3; i += 0.01) {
            series.add(i, Math.cos(((2 * Math.PI * i * frequency))));
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("Harmonic signal graph\n" + frequencyString, "t", "A",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        ChartPanel frame =
                new ChartPanel(chart);
        frame.setPreferredSize(new Dimension(450, 200));

        return frame;
    }

    public static ChartPanel drawFFT(String frequencyString, Float[] frequency) {
        Function<Float, Float> func = variable -> (float) Math.cos((variable[1] * 2 * Math.PI * variable[0]) / variable[2]);
        FastFourierTransformation fft = new FastFourierTransformation();
        Float[] res = fft.spectralAnalysis(0f, 10f, 0.01f, func, frequency);

        XYSeries series = new XYSeries("");

        for (int i = 1; i < 10; i += 1) {
            series.add(i, res[i]);
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("Graph of the harmonic signal spectrum" + frequencyString, "t", "A",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);

        ChartPanel frame =
                new ChartPanel(chart);
        frame.setPreferredSize(new Dimension(450, 200));

        return frame;

    }

}

