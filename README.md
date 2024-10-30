# Simple_Neural_Network_POO

Implementação educacional de uma rede neural simples para o ensino de Programação Orientada a Objetos (POO) em C++, Java e Python. Este repositório foi projetado para introduzir conceitos de redes neurais e permitir que os alunos experimentem com estruturas de classes, encapsulamento e modularidade.

## Objetivos

- Demonstrar como construir uma rede neural básica do zero usando POO.
- Aplicar conceitos de camadas e neurônios de redes neurais com classes e métodos.
- Implementar feedforward, backpropagation, treinamento e avaliação em uma rede neural.
- Comparar implementações em diferentes linguagens de programação orientadas a objetos.

## Estrutura do Repositório

Este repositório contém implementações para três linguagens:
- `C++`: Contém `NeuralNetwork.h`, `NeuralNetwork.cpp`, e `main.cpp`.
- `Java`: Contém `NeuralNetwork.java` e `Main.java`.
- `Python`: Contém `neural_network.py` e `main.py`.
  
Além disso, a pasta `data/` contém os arquivos de dados MNIST necessários para treinamento e teste da rede neural.

```
Simple_Neural_Network_POO/
├── C++/
│   ├── NeuralNetwork.h
│   ├── NeuralNetwork.cpp
│   ├── main.cpp
├── Java/
│   ├── NeuralNetwork.java
│   └── Main.java
├── Python/
│   ├── neural_network.py
│   └── main.py
├── README.md
└── data/
    ├── train-images.idx3-ubyte
    ├── train-labels.idx1-ubyte
    ├── t10k-images.idx3-ubyte
    └── t10k-labels.idx1-ubyte
```

## Configuração do Projeto

### Pré-requisitos

Para cada linguagem, você precisará de:

- **C++**: Compilador com suporte a C++11 ou superior.
- **Java**: Java Development Kit (JDK) versão 8 ou superior.
- **Python**: Python 3.x e a biblioteca `numpy`.

### Como Configurar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/SEU_USUARIO/Simple_Neural_Network_POO.git
   cd Simple_Neural_Network_POO
   ```

2. **Baixe o Dataset MNIST**  
   Coloque os arquivos `train-images.idx3-ubyte`, `train-labels.idx1-ubyte`, `t10k-images.idx3-ubyte`, e `t10k-labels.idx1-ubyte` na pasta `data/`. Você pode baixar os arquivos [neste link](http://yann.lecun.com/exdb/mnist/).

## Instruções de Execução

### C++

1. Compile o código:
   ```bash
   g++ -o neural_network C++/main.cpp C++/NeuralNetwork.cpp -std=c++11
   ```
2. Execute o programa:
   ```bash
   ./neural_network
   ```

### Java

1. Compile a classe `NeuralNetwork` e `Main`:
   ```bash
   javac Java/NeuralNetwork.java Java/Main.java
   ```
2. Execute o programa:
   ```bash
   java Java.Main
   ```

### Python

1. Instale o `numpy`:
   ```bash
   pip install numpy
   ```
2. Execute o script principal:
   ```bash
   python Python/main.py
   ```

## Estrutura de Código

### Arquivos Principais

#### 1. `NeuralNetwork.h` (C++) / `NeuralNetwork.java` (Java) / `neural_network.py` (Python)
   - Define a estrutura e os métodos da rede neural.
   - Métodos:
     - **Construtor**: Inicializa a rede com pesos e biases aleatórios.
     - **Feedforward**: Calcula a saída para uma entrada dada.
     - **Backpropagation**: Ajusta os pesos para minimizar o erro.
     - **Train**: Executa o treinamento da rede para múltiplas épocas.
     - **Evaluate**: Avalia a precisão da rede com o conjunto de teste.

#### 2. `main.cpp` (C++) / `Main.java` (Java) / `main.py` (Python)
   - Carrega os dados do MNIST.
   - Configura a rede neural com a estrutura escolhida.
   - Executa o treinamento e exibe a precisão final.

## Exercícios Propostos

Para alunos, tente fazer as seguintes modificações no código:

1. **Mude a Estrutura da Rede**:
   - Altere o número de camadas e neurônios na inicialização.
   - Exemplo: `{784, 128, 10}` (camada oculta com 128 neurônios) ou `{784, 64, 32, 10}` (duas camadas ocultas).

2. **Experimente com Diferentes Taxas de Aprendizado e Épocas**:
   - Veja como o desempenho muda ao variar a taxa de aprendizado e o número de épocas.

3. **Medição de Tempo**:
   - Use a função de medição de tempo para comparar o desempenho em C++, Java e Python. Tente medir separadamente o tempo de treinamento e avaliação.

4. **Adicione Novas Funções de Ativação**:
   - Implemente funções como `tanh` e `relu` e substitua a função sigmoide para comparar o desempenho.

5. **Compare Precisão entre Linguagens**:
   - Treine a mesma rede neural nas três linguagens e compare os resultados de precisão.

## Referências

- [Documentação da Biblioteca <chrono> (C++)](https://en.cppreference.com/w/cpp/chrono)
- [Numpy para Redes Neurais (Python)](https://numpy.org/doc/stable/)
- [Tutorial de Java](https://docs.oracle.com/javase/tutorial/)

## Contribuições

Sinta-se à vontade para contribuir com melhorias neste projeto. Sugestões são bem-vindas!

## Licença

Este projeto está licenciado sob a licença MIT.

### Observações

- O `README.md` orienta os alunos sobre como configurar o repositório, executar o código, e explorar o funcionamento da rede neural.
- A estrutura do repositório permite que os alunos trabalhem com cada linguagem de forma independente.
- Incluí exemplos de tarefas e experimentos para que eles possam aprofundar o entendimento sobre redes neurais e programação orientada a objetos.
