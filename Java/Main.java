import java.io.*;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        int numTrainImages = 1000;
        int numTestImages = 100;

        long startTime = System.currentTimeMillis();
        double[][] trainImages = loadImages("data/train-images.idx3-ubyte", numTrainImages);
        int[] trainLabels = loadLabels("data/train-labels.idx1-ubyte", numTrainImages);
        double[][] testImages = loadImages("data/t10k-images.idx3-ubyte", numTestImages);
        int[] testLabels = loadLabels("data/t10k-labels.idx1-ubyte", numTestImages);
        long loadTime = System.currentTimeMillis() - startTime;
        System.out.printf("Tempo de carregamento dos dados: %.2f segundos%n", loadTime / 1000.0);

        NeuralNetwork nn = new NeuralNetwork(new int[]{784, 128, 10}, 0.1);
        long startTrainTime = System.currentTimeMillis();
        nn.train(trainImages, trainLabels, 5);
        long trainTime = System.currentTimeMillis() - startTrainTime;
        System.out.printf("Tempo de treinamento: %.2f segundos%n", trainTime / 1000.0);

        long startEvalTime = System.currentTimeMillis();
        double accuracy = nn.evaluate(testImages, testLabels);
        long evalTime = System.currentTimeMillis() - startEvalTime;
        System.out.printf("Tempo de avaliação: %.2f segundos%n", evalTime / 1000.0);
        System.out.printf("Precisão final (após treinamento): %.2f%%%n", accuracy * 100);
    }

    private static double[][] loadImages(String filename, int numImages) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            dis.skip(16);  // Ignora cabeçalho
            double[][] images = new double[numImages][28 * 28];
            for (int i = 0; i < numImages; i++) {
                for (int j = 0; j < 28 * 28; j++) {
                    images[i][j] = (dis.readUnsignedByte() & 0xFF) / 255.0;
                }
            }
            return images;
        }
    }

    private static int[] loadLabels(String filename, int numLabels) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            dis.skip(8);  // Ignora cabeçalho
            int[] labels = new int[numLabels];
            for (int i = 0; i < numLabels; i++) {
                labels[i] = dis.readUnsignedByte();
            }
            return labels;
        }
    }
}

