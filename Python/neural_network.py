import numpy as np

class NeuralNetwork:
    def __init__(self, layers, learning_rate):
        self.layer_sizes = layers          # Tamanho das camadas
        self.learning_rate = learning_rate # Taxa de aprendizado
        self.weights = []                  # Lista de pesos
        self.biases = []                   # Lista de biases
        self.initialize_weights_and_biases()

    # Inicialização de pesos e biases
    def initialize_weights_and_biases(self):
        np.random.seed(42)
        for i in range(len(self.layer_sizes) - 1):
            layer_weights = np.random.uniform(-1, 1, (self.layer_sizes[i], self.layer_sizes[i + 1]))
            layer_biases = np.random.uniform(-1, 1, self.layer_sizes[i + 1])
            self.weights.append(layer_weights)
            self.biases.append(layer_biases)

    # Função de ativação sigmoide e sua derivada
    def sigmoid(self, x):
        return 1 / (1 + np.exp(-x))

    def sigmoid_derivative(self, x):
        return x * (1 - x)

    # Feedforward: calcula a saída da rede e armazena ativações de cada camada
    def feedforward(self, inputs):
        activations = [inputs]  # Armazena ativações de cada camada
        for i in range(len(self.weights)):
            inputs = self.sigmoid(np.dot(inputs, self.weights[i]) + self.biases[i])
            activations.append(inputs)
        return activations

    # Previsão da classe para uma entrada
    def predict(self, inputs):
        outputs = self.feedforward(inputs)[-1]  # Pega a última ativação (saída final)
        return np.argmax(outputs)

    # Treinamento com backpropagation
    def train(self, train_images, train_labels, epochs):
        for epoch in range(epochs):
            for inputs, label in zip(train_images, train_labels):
                # Passo de feedforward para obter todas as ativações
                activations = self.feedforward(inputs)
                outputs = activations[-1]  # Saída final da rede

                # Calcula o erro da camada de saída
                output_errors = np.array([(1 if j == label else 0) - outputs[j] for j in range(self.layer_sizes[-1])])

                # Backpropagation para ajustar pesos e biases
                errors = [output_errors]
                for j in range(len(self.weights) - 1, 0, -1):
                    layer_errors = np.dot(errors[0], self.weights[j].T) * self.sigmoid_derivative(activations[j])
                    errors.insert(0, layer_errors)

                # Atualização dos pesos e biases com gradientes
                for j in range(len(self.weights)):
                    self.weights[j] += self.learning_rate * np.outer(activations[j], errors[j])
                    self.biases[j] += self.learning_rate * errors[j]

            print(f"Epoch {epoch + 1} concluído.")

    # Avaliação da rede
    def evaluate(self, test_images, test_labels):
        correct_predictions = sum(1 for inputs, label in zip(test_images, test_labels) if self.predict(inputs) == label)
        return correct_predictions / len(test_images)
