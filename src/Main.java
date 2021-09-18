import SA.FastFourierTransformation;
import SA.Function;

import javax.swing.JFrame;



public class Main {
    public static void main(String[] args) {
        Function<Float, Float> func = new Function<Float, Float>() {
            @Override
            public Float apply(Float[] variable) {
                return (float)Math.cos((variable[1] * 2 * Math.PI  * variable[0] ) / variable[2]);
            }
        };
        FastFourierTransformation fft = new FastFourierTransformation(func);
        Float[] res = fft.spectralAnalysis(0f,10f,0.01f, new Float[] {8f});


    }
}
