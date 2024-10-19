import javax.swing.*;

import Arquivo.ArquivoHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class Interface {
	static ArquivoHandler arquivoHandler = new ArquivoHandler();
	
	public static void main(String[] args) {
		mostrarInterfaceInicial();
	}
	
    public static void mostrarInterfaceInicial() {

    	JFrame interfaceInicial = new JFrame("Cata Frutas - Menu inicial");
    	interfaceInicial.setBounds(500, 50, 500, 600);
        interfaceInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaceInicial.setLayout(null);
        interfaceInicial.setResizable(false);

        // Botão Ler Configurações
        JButton botaoIniciar = new JButton("Iniciar jogo");
        botaoIniciar.setBounds(70, 100, 360, 80);
        interfaceInicial.add(botaoIniciar);

        JButton botaoEditar = new JButton("Editar Configurações");
        botaoEditar.setBounds(70, 200, 360, 80);
        interfaceInicial.add(botaoEditar);

        JButton botaoEscolher = new JButton("Escolher Configurações");
        botaoEscolher.setBounds(70, 300, 360, 80);
        interfaceInicial.add(botaoEscolher);
        
        JButton botaoBaixar = new JButton("Baixar Configurações");
        botaoBaixar.setBounds(70, 400, 360, 80);
        interfaceInicial.add(botaoBaixar);
        
        // Caminho do arquivo configs.txt
        String caminho = "arqs/configs.txt";

        // Ação do botão Ler Configs
        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (Interface.arquivoHandler.validarGeracaoMapa(20)){
            		interfaceInicial.dispose();
            		iniciarJogo();
            	}else {
            		JOptionPane.showMessageDialog(interfaceInicial, "Não é possível gerar um mapa com essas configurações!", "Erro", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });

        // Ação do botão Editar Configs
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioEdicao(caminho);
            }
        });
        
        botaoEscolher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre um JFileChooser para o usuário escolher o arquivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Escolher arquivo de configurações");
                
                int userSelection = fileChooser.showOpenDialog(null);
                
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    
                    // Verifica se o arquivo tem a extensão .txt
                    try {
                    	boolean resultado_arquivo = Interface.arquivoHandler.ler("arqs" + System.getProperty("file.separator") + selectedFile.getName()); 
                    	System.out.println(resultado_arquivo);

	                		if(resultado_arquivo) {
	                			// Copia o arquivo selecionado para o destino
	                			//Files.copy(selectedFile.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                			JOptionPane.showMessageDialog(interfaceInicial, "Arquivo de configurações substituído com sucesso!");
	                		} else{
	                			//que armengue absurdo mds do ceu
	                			Interface.arquivoHandler.reiniciarAtributos();
	                			JOptionPane.showMessageDialog(interfaceInicial, "Não é possível gerar um mapa com essas configurações!", "Erro", JOptionPane.ERROR_MESSAGE);
	                		}

                    } catch (Exception ex) {
                    	JOptionPane.showMessageDialog(interfaceInicial, "Por favor, escolha um arquivo .txt no formato correto!", "Erro", JOptionPane.ERROR_MESSAGE);
	                }
                }
            }
        });
        
        // Ação do botão "Editar Configs"
        botaoBaixar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Interface.arquivoHandler.escreverNoArquivo();
            }
        });


        // Exibe a janela principal
        interfaceInicial.setVisible(true);
    }
    
    public static void iniciarJogo() {
        JFrame telaJogo = new JFrame("Jogo");
        
        int matriz = (Interface.arquivoHandler.getDimensao());
        final int tamanhoImagem;

        if (matriz > 14) {
        	tamanhoImagem = 50;
        } else  if (matriz > 9) {
            tamanhoImagem = 70;
        } else {
            tamanhoImagem = 100;
        }

        int tamanhoTela = tamanhoImagem * matriz;

        telaJogo.setBounds(500, 20, tamanhoTela, tamanhoTela);
        telaJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaJogo.setResizable(false);

        // Cria um JPanel personalizado para desenhar a imagem
        JPanel painelJogo  = new JPanel() {
            private static final long serialVersionUID = 1L; // Adicionando serialVersionUID

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                gerarMapa(g, matriz, tamanhoImagem);
            }
        };

        
        // Define o tamanho do painel para preencher a janela
        painelJogo.setPreferredSize(new Dimension(tamanhoTela, tamanhoTela));
        painelJogo.setLayout(null); // Não usar layout para posicionar manualmente os componentes
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        
        JButton voltarInterfaceButton = new JButton("Voltar ao menu inicial");
        voltarInterfaceButton.addActionListener(e -> {
            telaJogo.dispose();  // Fecha a tela do jogo
            mostrarInterfaceInicial(); // Volta para o menu inicial
        });
        
        JButton gerarNovamenteButton = new JButton("Gerar nova imagem");
        gerarNovamenteButton.addActionListener(e -> painelJogo.repaint());
        
        painelBotoes.add(voltarInterfaceButton);
        painelBotoes.add(gerarNovamenteButton);
        
        telaJogo.add(painelJogo, BorderLayout.CENTER);
        telaJogo.add(painelBotoes, BorderLayout.SOUTH);
        
        telaJogo.pack();
        telaJogo.setVisible(true);
    }
    
    public static void gerarMapa(Graphics g, int matriz, int tamanhoImagem) {
    	// carrega as imagens
        ImageIcon abacateIcon = new ImageIcon(Interface.class.getResource("/resources/abacate.png"));
        ImageIcon abacateiroIcon = new ImageIcon(Interface.class.getResource("/resources/abacateiro.png"));
        ImageIcon acerolaIcon = new ImageIcon(Interface.class.getResource("/resources/acerola.png"));
        ImageIcon aceroleiroIcon = new ImageIcon(Interface.class.getResource("/resources/aceroleiro.png"));
        ImageIcon amoraIcon = new ImageIcon(Interface.class.getResource("/resources/amora.png"));
        ImageIcon amoreiroIcon = new ImageIcon(Interface.class.getResource("/resources/amoreiro.png"));
        ImageIcon cocoIcon = new ImageIcon(Interface.class.getResource("/resources/coco.png"));
        ImageIcon coqueiroIcon = new ImageIcon(Interface.class.getResource("/resources/coqueiro.png"));
        ImageIcon goiabaIcon = new ImageIcon(Interface.class.getResource("/resources/goiaba.png"));
        ImageIcon goiabeiraIcon = new ImageIcon(Interface.class.getResource("/resources/goiabeira.png"));
        ImageIcon gramaIcon = new ImageIcon(Interface.class.getResource("/resources/grama.png"));
        ImageIcon laranjaIcon = new ImageIcon(Interface.class.getResource("/resources/laranja.png"));
        ImageIcon laranjeiraIcon = new ImageIcon(Interface.class.getResource("/resources/laranjeira.png"));
        ImageIcon maracujaIcon = new ImageIcon(Interface.class.getResource("/resources/maracuja.png"));
    	ImageIcon pedraIcon = new ImageIcon(Interface.class.getResource("/resources/pedra.png"));
    	ImageIcon pedra2Icon = new ImageIcon(Interface.class.getResource("/resources/pedra2.png"));
        ImageIcon steveIcon = new ImageIcon(Interface.class.getResource("/resources/steve.png"));
        ImageIcon steve2Icon = new ImageIcon(Interface.class.getResource("/resources/steve2.png"));
    	
        Image abacateImg = abacateIcon.getImage();
        Image abacateiroImg = abacateiroIcon.getImage();
        Image acerolaImg = acerolaIcon.getImage();
        Image aceroleiroImg = aceroleiroIcon.getImage();
        Image amoraImg = amoraIcon.getImage();
        Image amoreiroImg = amoreiroIcon.getImage();
        Image cocoImg = cocoIcon.getImage();
        Image coqueiroImg = coqueiroIcon.getImage();
        Image goiabaImg = goiabaIcon.getImage();
        Image goiabeiraImg = goiabeiraIcon.getImage();
        Image gramaImg = gramaIcon.getImage();
        Image steveImg = steveIcon.getImage();
        Image steve2Img = steve2Icon.getImage();
        Image laranjaImg = laranjaIcon.getImage();
        Image laranjeiraImg = laranjeiraIcon.getImage();
        Image maracujaImg = maracujaIcon.getImage();
        Image pedraImg = pedraIcon.getImage();
        Image pedra2Img = pedra2Icon.getImage();

    	
        
        // Desenha o mapa todo de grama
        for (int i = 0; i < matriz; i++) {
            for (int j = 0; j < matriz; j++) {
                // Calcula a posição para desenhar a imagem
                int x = j * tamanhoImagem;
                int y = i * tamanhoImagem;
                
                g.drawImage(gramaImg, x, y, tamanhoImagem, tamanhoImagem, null);
            }
        }
        
        java.util.Set<Point> posicoesSet = new java.util.HashSet<>();
        for (int i = 0; i < matriz; i++) {
            for (int j = 0; j < matriz; j++) {
                posicoesSet.add(new Point(j, i)); // Adiciona as coordenadas (coluna, linha)
            }
        }
        

        List<Point> posicoes = new ArrayList<>(posicoesSet);
        
        Collections.shuffle((List<?>) posicoes);
        
        colocarElemento(g, steveImg, posicoes.remove(0), tamanhoImagem);
        colocarElemento(g, steve2Img, posicoes.remove(0), tamanhoImagem);


        for (int i = 0; i < (Interface.arquivoHandler.getAbacate()); i++) {
        	colocarElemento(g, abacateImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getAbacateiras()); i++) {
        	colocarElemento(g, abacateiroImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getAmora()); i++) {
        	colocarElemento(g, amoraImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getAmoreiro()); i++) {
        	colocarElemento(g, amoreiroImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getAcerola()); i++) {
        	colocarElemento(g, acerolaImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getAceroleiro()); i++) {
        	colocarElemento(g, aceroleiroImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getCoco()); i++) {
        	colocarElemento(g, cocoImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getCoqueiro()); i++) {
        	colocarElemento(g, coqueiroImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getGoiaba()); i++) {
        	colocarElemento(g, goiabaImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getGoiabeira()); i++) {
        	colocarElemento(g, goiabeiraImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getLaranja()); i++) {
        	colocarElemento(g, laranjaImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getLaranjeiras()); i++) {
        	colocarElemento(g, laranjeiraImg, posicoes.remove(0), tamanhoImagem);
        }
        
        for (int i = 0; i < (Interface.arquivoHandler.getMaracujaTotal()); i++) {
        	colocarElemento(g, maracujaImg, posicoes.remove(0), tamanhoImagem);
        }
        

        for (int i = 0; i < (Interface.arquivoHandler.getPedras()); i++) {
            // Alterna entre pedraImg e pedraImg2 com base no índice
            Image pedra = (i % 2 == 0) ? pedraImg : pedra2Img;

            colocarElemento(g, pedra, posicoes.remove(0), tamanhoImagem);
        }
    }
    
    
    
    public static void colocarElemento(Graphics g, Image imagem, Point posicao, int tamanho) {
        int x = posicao.x * tamanho;
        int y = posicao.y * tamanho;
        g.drawImage(imagem, x, y, tamanho, tamanho, null);
    }



    // Método para abrir o formulário de edição
    public static void abrirFormularioEdicao(String caminho) {
        JFrame telaForm = new JFrame("Editar Configurações");
        telaForm.setBounds(100, 100, 500, 500);
        telaForm.setLayout(null);
        
        String[] listaFrutas = new String[6];
        listaFrutas[0] = "Laranja";
        listaFrutas[1] = "Abacate";
        listaFrutas[2] = "Coco";
        listaFrutas[3] = "Acerola";
        listaFrutas[4] = "Amora";
        listaFrutas[5] = "Goiaba";

        // Labels e campos de texto para as configurações
        JLabel labelDimensao = new JLabel("Tamanho do mapa:");
        labelDimensao.setBounds(10, 10, 150, 20);
        JTextField fieldDimensao = new JTextField();
        fieldDimensao.setBounds(160, 10, 100, 20);

        JLabel labelPedras = new JLabel("Quantidade de pedras:");
        labelPedras.setBounds(10, 40, 150, 20);
        JTextField fieldPedras = new JTextField();
        fieldPedras.setBounds(160, 40, 100, 20);

        JLabel labelMaracuja = new JLabel("Início Maracujá:");
        labelMaracuja.setBounds(10, 70, 150, 20);
        JTextField fieldMaracujaInicio = new JTextField();
        fieldMaracujaInicio.setBounds(160, 70, 100, 20);
        JLabel labelMaracujaTotal = new JLabel("Total:");
        labelMaracujaTotal.setBounds(270, 70, 50, 20);
        JTextField fieldMaracujaTotal = new JTextField();
        fieldMaracujaTotal.setBounds(320, 70, 50, 20);

        // Laranja e frutas similares
        JLabel labelLaranja = new JLabel("Árvores Laranja:");
        labelLaranja.setBounds(10, 100, 150, 20);
        JTextField fieldLaranjaArvores = new JTextField();
        fieldLaranjaArvores.setBounds(160, 100, 100, 20);
        JLabel labelLaranjaInicio = new JLabel("Início:");
        labelLaranjaInicio.setBounds(270, 100, 50, 20);
        JTextField fieldLaranjaInicio = new JTextField();
        fieldLaranjaInicio.setBounds(320, 100, 50, 20);
        
        JLabel labelAbacate = new JLabel("Árvores Abacate:");
        labelAbacate.setBounds(10, 130, 150, 20);
        JTextField fieldAbacateArvores = new JTextField();
        fieldAbacateArvores.setBounds(160, 130, 100, 20);
        JLabel labelAbacateInicio = new JLabel("Início:");
        labelAbacateInicio.setBounds(270, 130, 50, 20);
        JTextField fieldAbacateInicio = new JTextField();
        fieldAbacateInicio.setBounds(320, 130, 50, 20);
        
        JLabel labelCoco = new JLabel("Árvores Coco:");
        labelCoco.setBounds(10, 160, 150, 20);
        JTextField fieldCocoArvores = new JTextField();
        fieldCocoArvores.setBounds(160, 160, 100, 20);
        JLabel labelCocoInicio = new JLabel("Início:");
        labelCocoInicio.setBounds(270, 160, 50, 20);
        JTextField fieldCocoInicio = new JTextField();
        fieldCocoInicio.setBounds(320, 160, 50, 20);
        
        JLabel labelAcerola = new JLabel("Árvores Acerola:");
        labelAcerola.setBounds(10, 190, 150, 20);
        JTextField fieldAcerolaArvores = new JTextField();
        fieldAcerolaArvores.setBounds(160, 190, 100, 20);
        JLabel labelAcerolaInicio = new JLabel("Início:");
        labelAcerolaInicio.setBounds(270, 190, 50, 20);
        JTextField fieldAcerolaInicio = new JTextField();
        fieldAcerolaInicio.setBounds(320, 190, 50, 20);
        
        JLabel labelAmora = new JLabel("Árvores Amora:");
        labelAmora.setBounds(10, 220, 150, 20);
        JTextField fieldAmoraArvores = new JTextField();
        fieldAmoraArvores.setBounds(160, 220, 100, 20);
        JLabel labelAmoraInicio = new JLabel("Início:");
        labelAmoraInicio.setBounds(270, 220, 50, 20);
        JTextField fieldAmoraInicio = new JTextField();
        fieldAmoraInicio.setBounds(320, 220, 50, 20);
        
        JLabel labelGoiaba = new JLabel("Árvores Goiaba:");
        labelGoiaba.setBounds(10, 250, 150, 20);
        JTextField fieldGoiabaArvores = new JTextField();
        fieldGoiabaArvores.setBounds(160, 250, 100, 20);
        JLabel labelGoiabaInicio = new JLabel("Início:");
        labelGoiabaInicio.setBounds(270, 250, 50, 20);
        JTextField fieldGoiabaInicio = new JTextField();
        fieldGoiabaInicio.setBounds(320, 250, 50, 20);

        JLabel labelBichadas = new JLabel("Porcentagem bichadas:");
        labelBichadas.setBounds(10, 280, 150, 20);
        JTextField fieldBichadas = new JTextField();
        fieldBichadas.setBounds(160, 280, 100, 20);

        JLabel labelMochila = new JLabel("Tamanho da mochila:");
        labelMochila.setBounds(10, 310, 150, 20);
        JTextField fieldMochila = new JTextField();
        fieldMochila.setBounds(160, 310, 100, 20);

        // Botão para salvar as configurações
        JButton buttonSalvar = new JButton("Salvar");
        buttonSalvar.setBounds(140, 350, 100, 30);
        

        // Ação do botão "Salvar"
        buttonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    // Verifica se os campos estão vazios e insere o valor padrão, se necessário
                    String acerolaArvores = fieldAcerolaArvores.getText().isEmpty() ? "1" : fieldAcerolaArvores.getText();
                    String acerolaInicio = fieldAcerolaInicio.getText().isEmpty() ? "2" : fieldAcerolaInicio.getText();
                    String amoraArvores = fieldAmoraArvores.getText().isEmpty() ? "1" : fieldAmoraArvores.getText();
                    String amoraInicio = fieldAmoraInicio.getText().isEmpty() ? "1" : fieldAmoraInicio.getText();
                    String goiabaArvores = fieldGoiabaArvores.getText().isEmpty() ? "1" : fieldGoiabaArvores.getText();
                    String goiabaInicio = fieldGoiabaInicio.getText().isEmpty() ? "1" : fieldGoiabaInicio.getText();
                    String bichadas = fieldBichadas.getText().isEmpty() ? "25" : fieldBichadas.getText();
                    String mochila = fieldMochila.getText().isEmpty() ? "10" : fieldMochila.getText();
                    String dimensao = fieldDimensao.getText().isEmpty() ? "6" : fieldDimensao.getText();
                    String pedras = fieldPedras.getText().isEmpty() ? "7" : fieldPedras.getText();
                    String maracujaInicio = fieldMaracujaInicio.getText().isEmpty() ? "3" : fieldMaracujaInicio.getText();
                    String maracujaTotal = fieldMaracujaTotal.getText().isEmpty() ? "1" : fieldMaracujaTotal.getText();
                    String laranjaArvores = fieldLaranjaArvores.getText().isEmpty() ? "2" : fieldLaranjaArvores.getText();
                    String laranjaInicio = fieldLaranjaInicio.getText().isEmpty() ? "1" : fieldLaranjaInicio.getText();
                    String abacateArvores = fieldAbacateArvores.getText().isEmpty() ? "2" : fieldAbacateArvores.getText();
                    String abacateInicio = fieldAbacateInicio.getText().isEmpty() ? "3" : fieldAbacateInicio.getText();
                    String cocoArvores = fieldCocoArvores.getText().isEmpty() ? "2" : fieldCocoArvores.getText();
                    String cocoInicio = fieldCocoInicio.getText().isEmpty() ? "1" : fieldCocoInicio.getText();

                    //colocar essa parte no local certo de salvar arquivo
                	LinkedHashMap<String, String[]> frutas = new LinkedHashMap<>();
                	frutas.put("dimensao", new String[] {dimensao});
                    frutas.put("laranja", new String[] {laranjaArvores, laranjaInicio});
                    frutas.put("abacate", new String[] {abacateArvores, abacateInicio});
                    frutas.put("coco", new String[] {cocoArvores, cocoInicio});
                    frutas.put("acerola", new String[] {acerolaArvores, acerolaInicio});
                    frutas.put("amora", new String[] {amoraArvores, amoraInicio});
                    frutas.put("goiaba", new String[] {goiabaArvores, goiabaInicio});
                    frutas.put("bichadas", new String[] {bichadas});
                    frutas.put("mochila", new String[] {mochila});
                    frutas.put("pedras", new String[] {pedras});
                    frutas.put("maracuja", new String[] {maracujaInicio, maracujaTotal});
                    
                    Interface.arquivoHandler.setElementos(frutas);
                    if (!Interface.arquivoHandler.validarGeracaoMapa(20)) {
                    	Interface.arquivoHandler.reiniciarAtributos();
                    	JOptionPane.showMessageDialog(telaForm, "Não é possível gerar um mapa com essas configurações!", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {                    	
                    	JOptionPane.showMessageDialog(telaForm, "Configurações salvas com sucesso!");
                    }
                    
                    // Mensagem de sucesso e fechar o formulário
                    telaForm.dispose(); // Fecha o formulário após salvar
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        

        // Adiciona os componentes ao formulário
        telaForm.add(labelCoco);
        telaForm.add(fieldCocoArvores);
        telaForm.add(labelCocoInicio);
        telaForm.add(fieldCocoInicio);
        telaForm.add(labelAcerola);
        telaForm.add(fieldAcerolaArvores);
        telaForm.add(labelAcerolaInicio);
        telaForm.add(fieldAcerolaInicio);
        telaForm.add(labelAmora);
        telaForm.add(fieldAmoraArvores);
        telaForm.add(labelAmoraInicio);
        telaForm.add(fieldAmoraInicio);
        telaForm.add(labelGoiaba);
        telaForm.add(fieldGoiabaArvores);
        telaForm.add(labelGoiabaInicio);
        telaForm.add(fieldGoiabaInicio);
        telaForm.add(labelBichadas);
        telaForm.add(fieldBichadas);
        telaForm.add(labelMochila);
        telaForm.add(fieldMochila);
        telaForm.add(buttonSalvar);
        telaForm.add(labelDimensao);
        telaForm.add(fieldDimensao);
        telaForm.add(labelPedras);
        telaForm.add(fieldPedras);
        telaForm.add(labelMaracuja);
        telaForm.add(fieldMaracujaInicio);
        telaForm.add(labelMaracujaTotal);
        telaForm.add(fieldMaracujaTotal);
        telaForm.add(labelLaranja);
        telaForm.add(fieldLaranjaArvores);
        telaForm.add(labelLaranjaInicio);
        telaForm.add(fieldLaranjaInicio);
        telaForm.add(labelAbacate);
        telaForm.add(fieldAbacateArvores);
        telaForm.add(labelAbacateInicio);
        telaForm.add(fieldAbacateInicio);
        

        // Torna o formulário visível
        telaForm.setVisible(true);
    }
}