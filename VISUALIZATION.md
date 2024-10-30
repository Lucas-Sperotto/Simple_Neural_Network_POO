# Visualização de Imagens MNIST

Este diretório contém códigos para visualizar as imagens do dataset MNIST em diferentes linguagens de programação: **Python**, **Java** e **C++**. Cada código permite abrir um arquivo MNIST e exibir uma imagem específica em escala de cinza.

## Estrutura dos Arquivos MNIST

Cada arquivo de imagens MNIST possui a seguinte estrutura:

- **Cabeçalho**:
  - Bytes 0-3: Número mágico (identificador de arquivo, 0x00000803 para imagens).
  - Bytes 4-7: Número total de imagens.
  - Bytes 8-11: Número de linhas em cada imagem (28 para MNIST).
  - Bytes 12-15: Número de colunas em cada imagem (28 para MNIST).
  
- **Dados de Pixels**:
  - Cada imagem é armazenada como uma sequência de bytes. Cada byte representa a intensidade de um pixel (0 a 255), onde 0 é preto e 255 é branco.
  - Para MNIST, cada imagem é uma matriz 28x28 de pixels, ou seja, 784 bytes por imagem.

## Códigos de Visualização

### 1. Python

- **Arquivo**: `visualize_mnist.py`
- **Descrição**: Usa a biblioteca `matplotlib` para exibir uma imagem do dataset.
- **Execução**:
  ```bash
  python visualize_mnist.py
  ```

### 2. Java

- **Arquivo**: `MNISTVisualizer.java`
- **Descrição**: Usa `javax.swing` para exibir a imagem em uma janela.
- **Execução**:
  ```bash
  javac MNISTVisualizer.java
  java MNISTVisualizer
  ```

### 3. C++

- **Arquivo**: `mnist_visualizer.cpp`
- **Descrição**: Usa `OpenCV` para carregar e exibir uma imagem do dataset.
- **Execução**:
  ```bash
  g++ mnist_visualizer.cpp -o mnist_visualizer `pkg-config --cflags --libs opencv4`
  ./mnist_visualizer
  ```

## Modificações Sugeridas

1. **Alterar Índice da Imagem**: Tente visualizar imagens diferentes alterando o índice no código.
2. **Salvar Imagem**: Modifique o código para salvar a imagem em um arquivo.
3. **Explorar Formatos de Exibição**: Experimente exibir várias imagens em uma mesma janela para uma melhor visualização dos dados.

## Requisitos

- **Python**: `numpy`, `matplotlib`
- **Java**: JDK 8 ou superior
- **C++**: OpenCV (versão 4 ou superior)

## Referências

- [Dataset MNIST](http://yann.lecun.com/exdb/mnist/)
- [Documentação OpenCV](https://docs.opencv.org/)
- [Documentação Swing (Java)](https://docs.oracle.com/javase/tutorial/uiswing/)
