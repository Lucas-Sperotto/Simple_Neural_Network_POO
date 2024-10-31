#ifndef NEURAL_NETWORK_H
#define NEURAL_NETWORK_H

#include <vector>

/*
 * Classe NeuralNetwork
 * ---------------------
 * Esta classe implementa uma rede neural flexível, onde o número de camadas e neurônios 
 * pode ser definido pelo usuário ao instanciar o objeto.
 * 
 * A estrutura da rede é especificada como um vetor de inteiros, onde cada valor representa
 * o número de neurônios em uma camada. 
 */

class NeuralNetwork {
private:
    std::vector<int> layer_sizes; // Armazena o número de neurônios em cada camada
    std::vector<std::vector<std::vector<double>>> weights; // Pesos entre as camadas
    std::vector<std::vector<double>> biases;               // Biases para cada camada
    double learning_rate; // Taxa de aprendizado para ajuste de pesos e biases

    // Funções auxiliares
    double sigmoid(double x);                  // Função de ativação sigmoide
    double sigmoid_derivative(double x);       // Derivada da função sigmoide para backpropagation
    void initialize_weights_and_biases();      // Inicializa os pesos e biases com valores aleatórios

public:
    NeuralNetwork(const std::vector<int>& layers, double lr); // Construtor que aceita a estrutura e a taxa de aprendizado
    std::vector<double> feedforward(const std::vector<double>& inputs); // Calcula a saída da rede para entradas dadas
    int predict(const std::vector<double>& inputs);                     // Retorna a classe prevista para a entrada fornecida
    void train(const std::vector<std::vector<double>>& train_images, const std::vector<int>& train_labels, int epochs); // Treina a rede neural
    double evaluate(const std::vector<std::vector<double>>& test_images, const std::vector<int>& test_labels);          // Avalia a precisão da rede
};

#endif // NEURAL_NETWORK_H
