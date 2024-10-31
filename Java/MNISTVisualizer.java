// Importa as classes da biblioteca Swing para criar interfaces gráficas.
import javax.swing.*;

// Importa classes da biblioteca AWT para layout e manipulação gráfica.
import java.awt.*;

// Importa BufferedImage para criação e manipulação de imagens em memória.
import java.awt.image.BufferedImage;

// Importa FileInputStream para ler dados binários do arquivo MNIST.
import java.io.FileInputStream;

// Importa IOException para tratar possíveis erros na leitura de arquivos.
import java.io.IOException;

// Importa DataInputStream para ler tipos de dados primitivos de forma binária.
import java.io.DataInputStream;


/**
 * A classe MNISTVisualizer permite carregar e visualizar imagens do dataset MNIST.
 * A interface exibe uma grade de 9 imagens, e o usuário pode navegar entre páginas
 * de imagens usando botões de navegação. Cada imagem é exibida em escala de cinza.
 * 
 * Este código explora como manipular dados binários e exibir imagens em Java usando Swing.
 */
public class MNISTVisualizer {

    // Define a quantidade de imagens por página (layout 3x3).
    private static final int IMAGES_PER_PAGE = 9;
    
    // Tamanho de cada imagem MNIST (28x28 pixels).
    private static final int IMAGE_SIZE = 28;
    
    // Variável para rastrear a página atual durante a navegação.
    private static int currentPage = 0;
    
    // Array para armazenar as imagens da página atual.
    private static BufferedImage[] images = new BufferedImage[IMAGES_PER_PAGE];
    
    // Array de JLabels para exibir cada imagem e seu índice.
    private static JLabel[] imageLabels = new JLabel[IMAGES_PER_PAGE];
    
    // Frame principal da aplicação.
    private static JFrame frame;
    
    // Painel que contém a grade de imagens.
    private static JPanel imagePanel;
    
    // Caminho do arquivo MNIST que contém as imagens.
    private static String filename = "data/train-images.idx3-ubyte";

    /**
     * Carrega uma imagem MNIST específica com base no índice.
     *
     * @param filename O caminho para o arquivo MNIST.
     * @param index    O índice da imagem que queremos carregar.
     * @return         A imagem BufferedImage em escala de cinza correspondente ao índice fornecido.
     * @throws IOException Se ocorrer um erro na leitura do arquivo.
     */
    public static BufferedImage loadImage(String filename, int index) throws IOException {
        // Usa DataInputStream para ler dados binários do arquivo MNIST.
        try (DataInputStream in = new DataInputStream(new FileInputStream(filename))) {
            // Pula os 16 bytes iniciais de cabeçalho e as imagens antes do índice desejado.
            in.skipBytes(16 + index * IMAGE_SIZE * IMAGE_SIZE);

            // Cria uma BufferedImage em escala de cinza para armazenar a imagem.
            BufferedImage img = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_BYTE_GRAY);
            
            // Carrega a imagem pixel por pixel.
            for (int i = 0; i < IMAGE_SIZE; i++) {
                for (int j = 0; j < IMAGE_SIZE; j++) {
                    int gray = in.readUnsignedByte(); // Lê a intensidade de cinza do pixel (0-255).
                    img.getRaster().setSample(j, i, 0, gray); // Define diretamente o valor de cinza no raster.
                }
            }
            return img; // Retorna a imagem carregada.
        }
    }

    /**
     * Carrega um conjunto de 9 imagens correspondente à página atual.
     * Atualiza os rótulos para exibir as novas imagens e seus índices.
     *
     * @param pageIndex O índice da página que queremos carregar (cada página contém 9 imagens).
     */
    public static void loadImagesForPage(int pageIndex) {
        int startIndex = pageIndex * IMAGES_PER_PAGE; // Calcula o índice inicial para a página.
        
        // Carrega cada uma das 9 imagens para a página.
        try {
            for (int i = 0; i < IMAGES_PER_PAGE; i++) {
                images[i] = loadImage(filename, startIndex + i); // Carrega a imagem correspondente.
                imageLabels[i].setIcon(new ImageIcon(images[i])); // Define a imagem no JLabel.
                imageLabels[i].setText("Index: " + (startIndex + i)); // Define o índice da imagem como texto.
            }
        } catch (IOException e) {
            // Imprime uma mensagem de erro se houver problema ao carregar as imagens.
            System.err.println("Erro ao carregar as imagens: " + e.getMessage());
        }
    }

    /**
     * Configura e exibe a interface gráfica para visualização das imagens.
     * Cria o frame, o painel de imagens e os botões para navegação.
     */
    public static void createAndShowGUI() {
        // Cria o frame principal da interface.
        frame = new JFrame("MNIST Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa ao fechar a janela.
        frame.setLayout(new BorderLayout()); // Define o layout do frame.

        // Painel para exibir uma grade de 3x3 imagens.
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(3, 3)); // Define o layout em grid 3x3.
        
        // Inicializa cada JLabel no painel de imagens.
        for (int i = 0; i < IMAGES_PER_PAGE; i++) {
            imageLabels[i] = new JLabel(); // Cria um novo JLabel para cada imagem.
            imageLabels[i].setHorizontalTextPosition(JLabel.CENTER); // Define o texto centralizado.
            imageLabels[i].setVerticalTextPosition(JLabel.BOTTOM); // Posiciona o texto abaixo da imagem.
            imageLabels[i].setHorizontalAlignment(JLabel.CENTER); // Centraliza a imagem horizontalmente.
            imagePanel.add(imageLabels[i]); // Adiciona o JLabel ao painel de imagens.
        }
        
        // Adiciona o painel de imagens ao centro do frame.
        frame.add(imagePanel, BorderLayout.CENTER);

        // Cria o painel de botões para navegação entre páginas.
        JPanel buttonPanel = new JPanel();
        JButton prevButton = new JButton("Previous"); // Botão para página anterior.
        JButton nextButton = new JButton("Next");     // Botão para próxima página.
        buttonPanel.add(prevButton); // Adiciona o botão "Previous" ao painel.
        buttonPanel.add(nextButton); // Adiciona o botão "Next" ao painel.

        // Define a ação para o botão "Previous".
        prevButton.addActionListener(e -> {
            if (currentPage > 0) { // Verifica se há páginas anteriores.
                currentPage--; // Decrementa o índice da página atual.
                loadImagesForPage(currentPage); // Carrega as imagens da nova página.
            }
        });

        // Define a ação para o botão "Next".
        nextButton.addActionListener(e -> {
            currentPage++; // Incrementa o índice da página atual.
            loadImagesForPage(currentPage); // Carrega as imagens da nova página.
        });

        // Adiciona o painel de botões à parte inferior do frame.
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        // Carrega as imagens da primeira página.
        loadImagesForPage(currentPage);

        // Ajusta o tamanho do frame e o exibe.
        frame.pack(); // Ajusta o tamanho do frame automaticamente.
        frame.setVisible(true); // Torna o frame visível.
    }

    /**
     * Método principal que inicia o programa, criando e exibindo a interface gráfica.
     *
     * @param args Argumentos de linha de comando (não utilizados neste programa).
     */
    public static void main(String[] args) {
        // Inicia a interface gráfica no thread do Event Dispatch para segurança.
        SwingUtilities.invokeLater(MNISTVisualizer::createAndShowGUI);
    }
}
