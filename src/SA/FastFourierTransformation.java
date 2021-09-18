package SA;

import java.util.ArrayList;

public class FastFourierTransformation {

    Function<Float, Float> func;

    public FastFourierTransformation(Function<Float, Float> func) {
        this.func = func;
    }

    public Float[] createSomeFunc(Float start, Float end, Float samplingFREQ, Float[] variable){
        ArrayList<Float> arrayList = new ArrayList<>();
        int index = 0;
        for (Float i = start; i <= end; i+=samplingFREQ){
            arrayList.add(func.apply(new Float[]{(float)index, variable[0], (end-start)/samplingFREQ}));
            index++;
        }
        return arrayList.toArray(new Float[0]);
    }

    private float cos(int index, int frequency, int sampleRate) {
        return (float)Math.cos((2 * Math.PI * frequency * index) / sampleRate);
    }
    private float sin(int index, int frequency, int sampleRate) {
        return (float)Math.sin((2 * Math.PI * frequency * index) / sampleRate);
    }

    private Float[] dft(Float[] frame) {
        int sampleRate = frame.length;
        final int resultSize = sampleRate / 2;
        Float[] result = createZeroArray(sampleRate);
        for (int i = 0; i < result.length / 2; i++) {
            for (int j = 0; j < frame.length; j++) {
                result[2*i] += frame[j] * cos(j, i, sampleRate);
                result[2*i + 1] +=frame[j] * sin(j, i, sampleRate);
            }
            result[2*i] =result[2*i] / resultSize;
            result[2*i + 1] = result[2*i + 1] / resultSize;
        }

        return result;
    }

    public Float[] spectralAnalysis(Float start, Float end, Float samplingFREQ, Float[] variable){
        Float[] result = createSomeFunc(start, end, samplingFREQ, variable);
        result = dft(result);
        Float[] amplitude = new Float[result.length/2];
        for (int i = 0; i < result.length / 2; i++) {
            amplitude[i] = (float)Math.sqrt(result[2*i]*result[2*i] + result[2*i+1]*result[2*i+1]);
            System.out.println(i + ": " + "Projection on cos: " + result[2*i] + " Projection on sin: " + result[2*i + 1]
                    + " amplitude: "+ amplitude[i] + "\n");
        }
        return amplitude;
    }

    public static Float[] createZeroArray(int size){
        Float[] arr = new Float[size];
        for(int i = 0; i < size; i++){
            arr[i] = 0f;
        }
        return arr;
    }
}
