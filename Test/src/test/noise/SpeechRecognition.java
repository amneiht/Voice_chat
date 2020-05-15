//package test.noise;
//
//import java.io.File;
//import java.util.Arrays;
//
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//
//public class SpeechRecognition {
//public static void main(String[] args) throws Exception {
//    // load File
//    byte[] audioBytes = loadFile("hallo.wav");
//
//    System.out.println(Arrays.toString(audioBytes));
//
//    // preEmphasis
//    audioBytes = preEmphasis(audioBytes);
//
//    System.out.println(Arrays.toString(audioBytes));
//
//    // windowing & overlap
//}
//
//// preEmphasis | s'N = sN - asN-1 | emph = 0.97
//public static byte[] preEmphasis(byte[] input) {
//    byte[] output = new byte[input.length];
//    float emph = 0.97f;
//
//    output[0] = input[0];
//    for (int i = 1; i < input.length; i++) {
//        output[i] = (byte) (input[i] - emph * input[i - 1]);
//    }
//
//    return output;
//}
//
//public static byte[] windowing(byte[] input) {
//
//}
//
//public static double[] calculateFFT(byte[] signal) {
//    final int mNumberOfFFTPoints = 1024;
//    double mMaxFFTSample;
//
//    @SuppressWarnings("unused")
//    int mPeakPos = 0;
//
//    double temp;
//    Complex[] y;
//    Complex[] complexSignal = new Complex[mNumberOfFFTPoints];
//    double[] absSignal = new double[mNumberOfFFTPoints / 2];
//
//    for (int i = 0; i < mNumberOfFFTPoints; i++) {
//        temp = (double) ((signal[2 * i] & 0xFF) | (signal[2 * i + 1] << 8)) / 32768.0F;
//        complexSignal[i] = new Complex(temp, 0.0);
//    }
//
//    y = FFT.fft(complexSignal); // --> Here I use FFT class
//
//    mMaxFFTSample = 0.0;
//    mPeakPos = 0;
//    for (int i = 0; i < (mNumberOfFFTPoints / 2); i++) {
//        absSignal[i] = Math.sqrt(Math.pow(y[i].re(), 2) + Math.pow(y[i].im(), 2));
//        if (absSignal[i] > mMaxFFTSample) {
//            mMaxFFTSample = absSignal[i];
//            mPeakPos = i;
//        }
//    }
//
//    return absSignal;
//
//}
//
//public static float melToFreq(float input) {
//    return (float) (700 * (Math.pow(10, input / 2595) - 1));
//}
//
//public static float freqToMel(float input) {
//    return (float) (2595 * Math.log10(1 + (input / 700)));
//}
//
//// load wav File
//public static byte[] loadFile(String name) throws Exception {
//    @SuppressWarnings("unused")
//    int totalFramesRead = 0;
//    File fileIn = new File(name);
//    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
//    int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
//    if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
//        bytesPerFrame = 1;
//    }
//    int numBytes = 1024 * bytesPerFrame;
//    byte[] audioBytes = new byte[numBytes];
//    int numBytesRead = 0;
//    int numFramesRead = 0;
//    while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
//        numFramesRead = numBytesRead / bytesPerFrame;
//        totalFramesRead += numFramesRead;
//    }
//
//    return audioBytes;
//}
//}