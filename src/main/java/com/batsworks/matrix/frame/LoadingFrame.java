package com.batsworks.matrix.frame;

import com.batsworks.matrix.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class LoadingFrame extends JFrame {
    private static final Log<LoadingFrame> log = new Log<>(LoadingFrame.class);
    private static final int BACKGROUND_COLOR_VALUE = 0xf5c1f3;
    private static final Color BACKGROUND_COLOR = new Color(BACKGROUND_COLOR_VALUE);
    private static final int FPS = 90;
    private static final int BUFFER_SIZE = 200; // Número de imagens em memória

    private final JLabel videoPanel;
    private Iterator<Path> imagePathIterator;
    private final Queue<ImageIcon> imageBuffer = new LinkedList<>();
    private ScheduledExecutorService scheduler;

    public LoadingFrame() {
        // Configurações de otimização
        setTitle("Loading Frame");
        setUndecorated(true);
        setSize(new Dimension(625, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Configuração do painel de vídeo
        videoPanel = new JLabel();
        videoPanel.setOpaque(true);
        videoPanel.setBackground(BACKGROUND_COLOR);
        videoPanel.setHorizontalAlignment(SwingConstants.CENTER);
        videoPanel.setVerticalAlignment(SwingConstants.CENTER);
        add(videoPanel);

        // Carregamento assíncrono de imagens
        SwingWorker<Void, Void> imageLoader = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    Path dirPath = Paths.get(Objects.requireNonNull(
                            LoadingFrame.class.getResource("/img")).getPath());
                    initializeImageLoading(dirPath);
                } catch (Exception e) {
                    log.error("Erro no carregamento de imagens", e);
                }
                return null;
            }

            @Override
            protected void done() {
                iniciarAnimacao();
            }
        };
        imageLoader.execute();
        setVisible(true);
    }

    private void initializeImageLoading(Path dirPath) throws IOException {
        // Configura iterator para carregamento sob demanda
        imagePathIterator = Files.walk(dirPath)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".png"))
                .sorted(Comparator.comparingInt(this::extractNumber))
                .iterator();

        // Pré-carrega o buffer inicial
        preloadNextImages();
    }

    private void preloadNextImages() {
        imageBuffer.clear();
        int count = 0;
        while (imagePathIterator.hasNext() && count < BUFFER_SIZE) {
            Path imagePath = imagePathIterator.next();
            ImageIcon icon = new ImageIcon(imagePath.toString());
            imageBuffer.offer(icon);
            count++;
        }
    }

    private int extractNumber(Path path) {
        String numberPart = path.getFileName().toString().replaceAll("^(\\d+)\\.png$", "$1");
        return Integer.parseInt(numberPart);
    }

    private void iniciarAnimacao() {
        // Usa ScheduledExecutorService para melhor controle de threads
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                this::exibirProximaImagem,
                0,
                1000 / FPS,
                TimeUnit.MILLISECONDS
        );
    }

    private void exibirProximaImagem() {
        try {
            SwingUtilities.invokeLater(() -> {
                if (!imageBuffer.isEmpty()) {
                    ImageIcon icon = imageBuffer.poll();

                    // Redimensiona a imagem de forma eficiente
                    Image scaledImage = icon.getImage().getScaledInstance(
                            videoPanel.getWidth(),
                            videoPanel.getHeight(),
                            Image.SCALE_FAST
                    );

                    videoPanel.setIcon(new ImageIcon(scaledImage));

                    // Libera memória da imagem antiga
                    icon.getImage().flush();

                    // Recarrega o buffer quando estiver quase vazio
                    if (imageBuffer.size() < BUFFER_SIZE / 4) {
                        preloadNextImages();
                    }

                } else {
                    // Fim da animação
                    liberarRecursos();
                }
            });
        } catch (Exception e) {
            log.error("Erro na exibição de imagem", e);
            liberarRecursos();
        }
    }

    private void liberarRecursos() {
        // Para a animação e libera recursos
        if (scheduler != null) {
            scheduler.shutdownNow();
        }

        // Libera memória das imagens
        imageBuffer.forEach(icon -> {
            if (icon != null && icon.getImage() != null) {
                icon.getImage().flush();
            }
        });
        imageBuffer.clear();

        // Chama próxima tela
        SwingUtilities.invokeLater(() -> {
            System.gc(); // Sugestão de coleta de lixo
            new MatrixCalculator();
            dispose();
        });
    }

    // Sobrescreve método de dispose para garantir liberação de recursos
    @Override
    public void dispose() {
        liberarRecursos();
        super.dispose();
    }
}