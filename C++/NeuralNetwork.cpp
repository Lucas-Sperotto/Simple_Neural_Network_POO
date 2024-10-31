#include "NeuralNetwork.h"
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <iostream>

/*
 * Construtor NeuralNetwork
 * ------------------------
 * Recebe o número de neurônios em cada camada (layers) e a taxa de aprendizado (lr).
 * Inicializa a estrutura da rede com os tamanhos definidos e chama a função de inicialização 
 * de pesos e biases.
 */
NeuralNetwork::NeuralNetwork(const std::vector<int>& layers, double lr) : layer_sizes(layers), learning_rate(lr) {
    srand(static_cast<unsigned int>(time(0))); // Semente para geração de números aleatórios
    initialize_weights_and_biases();           // Inicializa pesos e biases com valores aleatórios
}

/*
 * Função initialize_weights_and_biases
 * ------------------------------------
 * Inicializa pesos e biases de cada camada com valores aleatórios entre -1 e 1.
 * weights[i][j][k] representa o peso entre o neurônio j da camada i e o neurônio k da camada i+1.
 * biases[i][j] representa o bias do neurônio j na camada i.
 */
void NeuralNetwork::initialize_weights_and_biases() {
    weights.resize(layer_sizes.size() - 1); // Número de camadas com pesos é (total de camadas - 1)
    biases.resize(layer_sizes.size() - 1);

    for (size_t i = 0; i < weights.size(); i++) {
        int layer_size = layer_sizes[i];
        int next_layer_size = layer_sizes[i + 1];

        weights[i].resize(layer_size, std::vector<double>(next_layer_size));
        biases[i].resize(next_layer_size);

        for (int j = 0; j < layer_size; j++) {
            for (int k = 0; k < next_layer_size; k++) {
                weights[i][j][k] = ((double) rand() / RAND_MAX) * 2 - 1; // Pesos aleatórios entre -1 e 1
            }
        }
        for (int j = 0; j < next_layer_size; j++) {
            biases[i][j] = ((double) rand() / RAND_MAX) * 2 - 1; // Biases aleatórios entre -1 e 1
        }
    }
}

/*
 * Função sigmoid
 * --------------
 * Função de ativação sigmoide usada para normalizar os valores dos neurônios,
 * mapeando-os para um intervalo entre 0 e 1.
 */
double NeuralNetwork::sigmoid(double x) {
    return 1 / (1 + exp(-x));
}

/*
 * Função sigmoid_derivative
 * -------------------------
 * Calcula a derivada da função sigmoide para ajuste dos pesos durante o backpropagation.
 */
double NeuralNetwork::sigmoid_derivative(double x) {
    return x * (1 - x);
}

/*
 * Função feedforward
 * ------------------
 * Calcula a saída da rede para uma entrada fornecida. A função propaga a entrada através de 
 * cada camada, aplicando pesos, bias e a função de ativação sigmoide.
 */
std::vector<double> NeuralNetwork::feedforward(const std::vector<double>& inputs) {
    std::vector<double> activations = inputs;

    for (size_t i = 0; i < weights.size(); i++) {
        std::vector<double> next_activations(layer_sizes[i + 1]);

        for (int j = 0; j < layer_sizes[i + 1]; j++) {
            next_activations[j] = biases[i][j]; // Começa com o bias
            for (int k = 0; k < layer_sizes[i]; k++) {
                next_activations[j] += activations[k] * weights[i][k][j]; // Aplica o peso
            }
            next_activations[j] = sigmoid(next_activations[j]); // Ativação sigmoide
        }
        activations = next_activations; // Avança para a próxima camada
    }
    return activations; // Saída final da rede
}

/*
 * Função predict
 * --------------
 * Retorna a classe prevista para uma entrada dada, ou seja, a saída com o maior valor
 * após o feedforward.
 */
int NeuralNetwork::predict(const std::vector<double>& inputs) {
    std::vector<double> outputs = feedforward(inputs);
    int predicted_class = 0;
    double max_output = outputs[0];
    for (size_t i = 1; i < outputs.size(); i++) {
        if (outputs[i] > max_output) {
            max_output = outputs[i];
            predicted_class = i;
        }
    }
    return predicted_class;
}

/*
 * Função train
 * ------------
 * Realiza o treinamento da rede usando backpropagation. Para cada imagem, o feedforward é 
 * calculado e o erro é ajustado para atualizar os pesos e biases.
 */
void NeuralNetwork::train(const std::vector<std::vector<double>>& train_images, const std::vector<int>& train_labels, int epochs) {
    for (int epoch = 0; epoch < epochs; epoch++) {
        for (size_t i = 0; i < train_images.size(); i++) {
            // Feedforward
            std::vector<double> outputs = feedforward(train_images[i]);

            // Calcula erro da camada de saída
            std::vector<double> output_errors(layer_sizes.back());
            for (int j = 0; j < layer_sizes.back(); j++) {
                output_errors[j] = (j == train_labels[i] ? 1.0 : 0.0) - outputs[j];
            }

            // Backpropagation
            std::vector<std::vector<double>> errors(weights.size());
            errors.back() = output_errors;

            // Itera pela rede de trás para frente
            for (int j = weights.size() - 2; j >= 0; j--) {
                errors[j].resize(layer_sizes[j + 1], 0.0);
                for (int k = 0; k < layer_sizes[j + 1]; k++) {
                    for (int l = 0; l < layer_sizes[j + 2]; l++) {
                        errors[j][k] += errors[j + 1][l] * weights[j + 1][k][l];
                    }
                    errors[j][k] *= sigmoid_derivative(feedforward(train_images[i])[k]);
                }
            }
        }
    }
}

/*
 * Função evaluate
 * ---------------
 * Avalia a precisão da rede comparando as previsões com os rótulos reais.
 */
double NeuralNetwork::evaluate(const std::vector<std::vector<double>>& test_images, const std::vector<int>& test_labels) {
    int correct_predictions = 0;
    for (size_t i = 0; i < test_images.size(); i++) {
        if (predict(test_images[i]) == test_labels[i]) {
            correct_predictions++;
        }
    }
    return static_cast<double>(correct_predictions) / test_images.size();
}
