# Importa 'struct' para leitura de dados binários, necessário para o formato IDX das imagens MNIST.
import struct

# Importa 'tkinter' para criar a interface gráfica. 'tk' é um apelido comum para tkinter.
import tkinter as tk

# Importa componentes específicos do tkinter:
# 'Label' exibe imagens e texto, 'Button' permite navegação entre páginas.
from tkinter import Label, Button

# Importa 'Pillow' para manipulação de imagens:
# 'Image' cria uma imagem, e 'ImageTk' converte a imagem para o formato tkinter.
from PIL import Image, ImageTk

class MNISTVisualizer:
    """
    A classe MNISTVisualizer permite carregar e visualizar imagens do dataset MNIST.
    A interface exibe uma grade de 9 imagens, e o usuário pode navegar entre páginas
    usando botões de navegação. Cada imagem é exibida em escala de cinza.
    Este código é similar ao exemplo Java, explorando manipulação de dados binários
    e exibição gráfica em Python.
    """
    # Configuração inicial das constantes para compatibilidade com o código Java.
    IMAGES_PER_PAGE = 9   # Quantidade de imagens por página (3x3 layout)
    IMAGE_SIZE = 28       # Tamanho de cada imagem MNIST (28x28 pixels)
    current_page = 0      # Variável que rastreia a página atual na navegação
    filename = "data/train-images.idx3-ubyte"  # Caminho do arquivo MNIST

    def __init__(self, master):
        """
        Inicializa a interface gráfica no tkinter com um layout similar ao do Java Swing.
        Configura uma grade de 3x3 imagens e cria botões de navegação.
        
        Args:
            master: Janela principal onde os componentes serão exibidos.
        """
        # Configura a janela principal com o título "MNIST Image Viewer"
        self.master = master
        master.title("MNIST Image Viewer")

        # Cria uma lista de 9 'Label' widgets para exibir as imagens
        # (Equivalente a JLabels em Java).
        self.image_labels = [Label(master) for _ in range(self.IMAGES_PER_PAGE)]
        
        # Posiciona cada 'Label' em um layout de grade 3x3 na janela
        for i, label in enumerate(self.image_labels):
            label.grid(row=i // 3, column=i % 3, padx=5, pady=5)

        # Painel de navegação com botões "Previous" e "Next"
        self.prev_button = Button(master, text="Previous", command=self.prev_page)
        self.next_button = Button(master, text="Next", command=self.next_page)
        
        # Posiciona os botões no layout da janela
        self.prev_button.grid(row=3, column=0, columnspan=1)
        self.next_button.grid(row=3, column=2, columnspan=1)

        # Carrega as imagens da primeira página para inicializar a interface.
        self.load_images_for_page(self.current_page)

    def load_image(self, index):
        """
        Carrega uma imagem MNIST específica com base no índice fornecido.
        Lê os pixels diretamente do arquivo binário IDX para criar uma imagem de 28x28.
        
        Args:
            index (int): Índice da imagem no arquivo MNIST.
        
        Returns:
            Uma imagem em escala de cinza (28x28) no formato Pillow (Image).
        """
        with open(self.filename, 'rb') as f:
            # Pula o cabeçalho e as imagens anteriores (16 bytes + (index * tamanho da imagem)).
            f.seek(16 + index * self.IMAGE_SIZE * self.IMAGE_SIZE)

            # Lê a imagem de 28x28 pixels (784 bytes) como uma sequência de intensidade de cinza.
            pixels = f.read(self.IMAGE_SIZE * self.IMAGE_SIZE)
            
            # Cria uma imagem em escala de cinza a partir dos pixels.
            img = Image.frombytes('L', (self.IMAGE_SIZE, self.IMAGE_SIZE), pixels)
            return img

    def load_images_for_page(self, page_index):
        """
        Carrega e exibe um conjunto de 9 imagens para a página especificada.
        Cada imagem é redimensionada para facilitar a visualização na tela.
        
        Args:
            page_index (int): Índice da página a ser carregada (cada página contém 9 imagens).
        """
        # Calcula o índice inicial para a página atual.
        start_index = page_index * self.IMAGES_PER_PAGE

        # Carrega as 9 imagens e as exibe na interface
        for i in range(self.IMAGES_PER_PAGE):
            try:
                # Carrega a imagem MNIST no índice especificado
                img = self.load_image(start_index + i)
                
                # Redimensiona a imagem para 100x100 pixels para melhor visualização
                img_resized = img.resize((100, 100), Image.NEAREST)

                # Converte a imagem para o formato que o tkinter consegue exibir
                photo = ImageTk.PhotoImage(img_resized)
                
                # Configura o Label para exibir a imagem e o índice como texto
                self.image_labels[i].config(image=photo, text=f"Index: {start_index + i}", compound='bottom')
                
                # Salva a imagem no label para evitar que seja descartada
                self.image_labels[i].image = photo
            except Exception as e:
                # Exibe uma mensagem de erro no terminal se houver problema ao carregar a imagem
                print(f"Erro ao carregar a imagem no índice {start_index + i}: {e}")

    def prev_page(self):
        """
        Navega para a página anterior de imagens, se houver uma disponível.
        Decrementa o índice da página e carrega as novas imagens.
        """
        if self.current_page > 0:
            # Decrementa o número da página atual
            self.current_page -= 1
            
            # Carrega as imagens para a página atualizada
            self.load_images_for_page(self.current_page)

    def next_page(self):
        """
        Navega para a próxima página de imagens.
        Incrementa o índice da página e carrega as novas imagens.
        """
        # Incrementa o número da página atual
        self.current_page += 1
        
        # Carrega as imagens para a página atualizada
        self.load_images_for_page(self.current_page)


# Configuração e execução da interface gráfica
if __name__ == "__main__":
    # Cria a janela principal do tkinter
    root = tk.Tk()
    
    # Instancia o visualizador MNIST e o associa à janela principal
    app = MNISTVisualizer(root)
    
    # Executa o loop principal da interface gráfica
    root.mainloop()
