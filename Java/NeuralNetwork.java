import java.util.Random;

class NeuralNetwork {
    private final int[] layerSizes;
    private final double learningRate;
    private final double[][][] weights;
    private final double[][] biases;
    private final Random random = new Random(42);

    public NeuralNetwork(int[] layers, double learningRate) {
        this.layerSizes = layers;
        this.learningRate = learningRate;
        this.weights = new double[layers.length - 1][][];
        this.biases = new double[layers.length - 1][];
        initializeWeightsAndBiases();
    }

    private void initializeWeightsAndBiases() {
        for (int i = 0; i < layerSizes.length - 1; i++) {
            weights[i] = new double[layerSizes[i]][layerSizes[i + 1]];
            biases[i] = new double[layerSizes[i + 1]];
            for (int j = 0; j < layerSizes[i]; j++) {
                for (int k = 0; k < layerSizes[i + 1]; k++) {
                    weights[i][j][k] = (random.nextDouble() * 2) - 1;  // Valores aleatórios entre -1 e 1
                }
            }
            for (int j = 0; j < layerSizes[i + 1]; j++) {
                biases[i][j] = (random.nextDouble() * 2) - 1;
            }
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    private double[] feedforward(double[] inputs) {
        double[] activations = inputs;
        for (int i = 0; i < weights.length; i++) {
            double[] nextActivations = new double[layerSizes[i + 1]];
            for (int j = 0; j < layerSizes[i + 1]; j++) {
                nextActivations[j] = biases[i][j];
                for (int k = 0; k < layerSizes[i]; k++) {
                    nextActivations[j] += activations[k] * weights[i][k][j];
                }
                nextActivations[j] = sigmoid(nextActivations[j]);
            }
            activations = nextActivations;
        }
        return activations;
    }

    public int predict(double[] inputs) {
        double[] outputs = feedforward(inputs);
        int maxIndex = 0;
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > outputs[maxIndex]) maxIndex = i;
        }
        return maxIndex;
    }

    public void train(double[][] trainImages, int[] trainLabels, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainImages.length; i++) {
                double[] inputs = trainImages[i];
                int label = trainLabels[i];

                // Passo de feedforward para obter todas as ativações de cada camada
                double[][] activations = new double[layerSizes.length][];
                activations[0] = inputs;
                for (int layer = 1; layer < layerSizes.length; layer++) {
                    activations[layer] = new double[layerSizes[layer]];
                    for (int j = 0; j < layerSizes[layer]; j++) {
                        double sum = biases[layer - 1][j];
                        for (int k = 0; k < layerSizes[layer - 1]; k++) {
                            sum += activations[layer - 1][k] * weights[layer - 1][k][j];
                        }
                        activations[layer][j] = sigmoid(sum);
                    }
                }

                // Backpropagation: calcula o erro e atualiza pesos e biases
                double[][] errors = new double[layerSizes.length][];
                errors[layerSizes.length - 1] = new double[layerSizes[layerSizes.length - 1]];
                
                // Erro na camada de saída
                for (int j = 0; j < layerSizes[layerSizes.length - 1]; j++) {
                    double target = (j == label) ? 1.0 : 0.0;
                    errors[layerSizes.length - 1][j] = (target - activations[layerSizes.length - 1][j]) 
                                                        * sigmoidDerivative(activations[layerSizes.length - 1][j]);
                }
                
                // Backpropagation nas camadas ocultas
                for (int layer = layerSizes.length - 2; layer > 0; layer--) {
                    errors[layer] = new double[layerSizes[layer]];
                    for (int j = 0; j < layerSizes[layer]; j++) {
                        double error = 0.0;
                        for (int k = 0; k < layerSizes[layer + 1]; k++) {
                            error += errors[layer + 1][k] * weights[layer][j][k];
                        }
                        errors[layer][j] = error * sigmoidDerivative(activations[layer][j]);
                    }
                }

                // Atualização dos pesos e biases
                for (int layer = 0; layer < weights.length; layer++) {
                    for (int j = 0; j < layerSizes[layer + 1]; j++) {
                        for (int k = 0; k < layerSizes[layer]; k++) {
                            weights[layer][k][j] += learningRate * errors[layer + 1][j] * activations[layer][k];
                        }
                        biases[layer][j] += learningRate * errors[layer + 1][j];
                    }
                }
            }
            System.out.printf("Epoch %d concluído.%n", epoch + 1);
        }
    }

    public double evaluate(double[][] testImages, int[] testLabels) {
        int correct = 0;
        for (int i = 0; i < testImages.length; i++) {
            if (predict(testImages[i]) == testLabels[i]) correct++;
        }
        return (double) correct / testImages.length;
    }
}
