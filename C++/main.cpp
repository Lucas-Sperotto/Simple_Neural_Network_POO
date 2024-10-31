#include "NeuralNetwork.h"
#include <iostream>
#include <chrono>
#include <vector>

// Funções de carregamento de dados omissas para brevidade
void load_mnist_images(const std::string& filepath, std::vector<std::vector<double>>& images, int num_images);
void load_mnist_labels(const std::string& filepath, std::vector<int>& labels, int num_labels);

int main() {
    int num_train_images = 1000;
    int num_test_images = 1000;

    // Carregar dados de treino e teste
    std::vector<std::vector<double>> train_images;
    std::vector<int> train_labels;
    std::vector<std::vector<double>> test_images;
    std::vector<int> test_labels;

    auto start = std::chrono::high_resolution_clock::now();
    load_mnist_images("train-images.idx3-ubyte", train_images, num_train_images);
    load_mnist_labels("train-labels.idx1-ubyte", train_labels, num_train_images);
    auto end = std::chrono::high_resolution_clock::now();
    std::cout << "Tempo de carregamento: " << std::chrono::duration_cast<std::chrono::seconds>(end - start).count() << " segundos" << std::endl;

    // Configuração da rede
    NeuralNetwork nn({784, 128, 64, 10}, 0.1);

    // Treinamento
    start = std::chrono::high_resolution_clock::now();
    nn.train(train_images, train_labels, 5);
    end = std::chrono::high_resolution_clock::now();
    std::cout << "Tempo de treinamento: " << std::chrono::duration_cast<std::chrono::seconds>(end - start).count() << " segundos" << std::endl;

    // Avaliação
    start = std::chrono::high_resolution_clock::now();
    double accuracy = nn.evaluate(test_images, test_labels);
    end = std::chrono::high_resolution_clock::now();
    std::cout << "Precisao: " << accuracy * 100 << "%" << std::endl;
    std::cout << "Tempo de avaliação: " << std::chrono::duration_cast<std::chrono::seconds>(end - start).count() << " segundos" << std::endl;

    return 0;
}
