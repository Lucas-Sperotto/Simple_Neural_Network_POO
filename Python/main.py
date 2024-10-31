
import numpy as np
from neural_network import NeuralNetwork
import time

# Função para carregar imagens MNIST
def load_images(filename, num_images):
    with open(filename, 'rb') as f:
        f.read(16)  # Ignora cabeçalho
        images = np.frombuffer(f.read(num_images * 28 * 28), dtype=np.uint8)
        images = images.reshape(num_images, 28 * 28) / 255.0  # Normaliza para [0,1]
    return images

# Função para carregar rótulos MNIST
def load_labels(filename, num_labels):
    with open(filename, 'rb') as f:
        f.read(8)  # Ignora cabeçalho
        labels = np.frombuffer(f.read(num_labels), dtype=np.uint8)
    return labels

def main():
    # Número de imagens de treino e teste
    num_train_images = 1000
    num_test_images = 100

    # Medição de tempo para carregamento dos dados
    start_time = time.time()
    train_images = load_images('data/train-images.idx3-ubyte', num_train_images)
    train_labels = load_labels('data/train-labels.idx1-ubyte', num_train_images)
    test_images = load_images('data/t10k-images.idx3-ubyte', num_test_images)
    test_labels = load_labels('data/t10k-labels.idx1-ubyte', num_test_images)
    load_time = time.time() - start_time
    print(f"Tempo de carregamento dos dados: {load_time:.2f} segundos")

    # Configurar e treinar a rede neural
    nn = NeuralNetwork([784, 128, 10], 0.1)
    start_train_time = time.time()
    nn.train(train_images, train_labels, 5)
    train_time = time.time() - start_train_time
    print(f"Tempo de treinamento: {train_time:.2f} segundos")

    # Avaliar a precisão no conjunto de teste
    start_eval_time = time.time()
    accuracy = nn.evaluate(test_images, test_labels)
    eval_time = time.time() - start_eval_time
    print(f"Tempo de avaliação: {eval_time:.2f} segundos")
    print(f"Precisão final (após treinamento): {accuracy * 100:.2f}%")

if __name__ == "__main__":
    main()
