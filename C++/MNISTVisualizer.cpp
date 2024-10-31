#include <opencv2/opencv.hpp>  // Biblioteca OpenCV para manipulação de imagens
#include <fstream>             // Biblioteca para manipulação de arquivos
#include <iostream>            // Biblioteca para entrada e saída

using namespace cv;            // Facilita o uso de funções do OpenCV
using namespace std;

const int IMAGE_SIZE = 28;         // Tamanho da imagem MNIST (28x28 pixels)
const int IMAGES_PER_PAGE = 9;     // Número de imagens por página (3x3 layout)
int currentPage = 0;               // Índice da página atual
string filename = "data/train-images.idx3-ubyte"; // Caminho do arquivo MNIST

/**
 * Função para carregar uma imagem específica do dataset MNIST
 * 
 * @param index Índice da imagem a ser carregada
 * @return Mat Objeto de imagem em escala de cinza
 */
Mat loadImage(int index) {
    ifstream file(filename, ios::binary);
    if (!file.is_open()) {
        cerr << "Erro ao abrir o arquivo " << filename << endl;
        exit(1);
    }

    // Pula o cabeçalho do arquivo (16 bytes) e imagens anteriores
    file.seekg(16 + index * IMAGE_SIZE * IMAGE_SIZE, ios::beg);

    // Lê os pixels da imagem e cria uma matriz OpenCV de escala de cinza (28x28)
    Mat img(IMAGE_SIZE, IMAGE_SIZE, CV_8UC1);
    for (int i = 0; i < IMAGE_SIZE; i++) {
        for (int j = 0; j < IMAGE_SIZE; j++) {
            unsigned char pixel;
            file.read(reinterpret_cast<char*>(&pixel), sizeof(pixel));
            img.at<uchar>(i, j) = pixel; // Define o valor do pixel
        }
    }

    file.close();
    return img;
}

/**
 * Função para carregar uma página de imagens (9 imagens) e exibir em um layout 3x3
 * 
 * @param pageIndex Índice da página a ser carregada
 */
void loadImagesForPage(int pageIndex) {
    // Cria uma imagem de 3x3 para exibir todas as imagens da página
    Mat pageDisplay(IMAGE_SIZE * 3, IMAGE_SIZE * 3, CV_8UC1, Scalar(255));

    int startIndex = pageIndex * IMAGES_PER_PAGE;
    for (int i = 0; i < IMAGES_PER_PAGE; i++) {
        Mat img = loadImage(startIndex + i);
        
        // Calcula a posição onde a imagem será inserida na página
        int row = (i / 3) * IMAGE_SIZE;
        int col = (i % 3) * IMAGE_SIZE;
        img.copyTo(pageDisplay(Rect(col, row, IMAGE_SIZE, IMAGE_SIZE))); // Copia a imagem para a posição correta
        
        // Exibe o índice da imagem
        putText(pageDisplay, to_string(startIndex + i), Point(col + 2, row + 12), FONT_HERSHEY_SIMPLEX, 0.4, Scalar(0), 1);
    }

    // Exibe a página de imagens
    imshow("MNIST Image Viewer", pageDisplay);
}

int main() {
    cout << "Use as setas para a esquerda e direita para navegar entre páginas." << endl;
    cout << "Pressione ESC para sair." << endl;

    namedWindow("MNIST Image Viewer", WINDOW_AUTOSIZE);

    while (true) {
        loadImagesForPage(currentPage);

        // Espera pela entrada do usuário para navegação
        char key = waitKey(0);
        if (key == 27) {  // Pressiona ESC para sair
            break;
        } else if (key == 81) {  // Seta para a esquerda
            if (currentPage > 0) currentPage--;
        } else if (key == 83) {  // Seta para a direita
            currentPage++;
        }
    }

    destroyAllWindows();
    return 0;
}
