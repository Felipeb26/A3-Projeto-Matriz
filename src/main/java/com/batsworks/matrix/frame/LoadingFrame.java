package com.batsworks.matrix.frame;

import com.batsworks.matrix.utils.Log;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LoadingFrame extends JFrame {

    private static final Log<LoadingFrame> log = new Log<>(LoadingFrame.class);
    private static final int BACKGROUND_COLOR_VALUE = 0xf5c1f3;
    private static final Color BACKGROUND_COLOR = new Color(BACKGROUND_COLOR_VALUE);
    private static final int FPS = 90;
    private final JLabel videoPanel;
    private Timer frameTimer;
    private static List<String> imagens;
    private int currentImageIndex = 0;
    private VolatileImage volatileImage; // VolatileImage para renderização eficiente

    public LoadingFrame() {
        setTitle("Loading Frame");
        setSize(new Dimension(625, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel principal
        videoPanel = new JLabel();
        videoPanel.setOpaque(true);
        videoPanel.setBackground(BACKGROUND_COLOR);
        videoPanel.setHorizontalAlignment(SwingConstants.CENTER);
        videoPanel.setVerticalAlignment(SwingConstants.CENTER);
        videoPanel.setDoubleBuffered(true);
        add(videoPanel);

        SwingWorker<List<String>, Void> imageLoader = new SwingWorker<>() {
            @Override
            protected List<String> doInBackground() {
                return carregarImagens(Paths.get(Objects.requireNonNull(LoadingFrame.class.getResource("/img")).getPath()));
            }

            @Override
            protected void done() {
                try {
                    imagens = get();
                    iniciarAnimacao();
                } catch (InterruptedException | ExecutionException e) {
                    log.error(e);
                }
            }
        };
        imageLoader.execute();
        setVisible(true);
    }

    private List<String> carregarImagens(Path dirPath) {
        try (Stream<Path> paths = Files.walk(dirPath)) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .sorted(Comparator.comparingInt(LoadingFrame::extractNumber))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    private static int extractNumber(String path) {
        String numberPart = path.replaceAll("^.*img/(\\d+)\\.png$", "$1");
        return Integer.parseInt(numberPart);
    }

    private void iniciarAnimacao() {
        int delay = 1000 / FPS;
        frameTimer = new Timer(delay, e -> exibirProximaImagem());
        frameTimer.setInitialDelay(0);
        frameTimer.start();
    }


    private void exibirProximaImagem() {
        if (currentImageIndex < imagens.size()) {
            String imagemPath = imagens.get(currentImageIndex++);
            ImageIcon icon = new ImageIcon(imagemPath);

            if (volatileImage == null || volatileImage.getWidth() != videoPanel.getWidth()
                    || volatileImage.getHeight() != videoPanel.getHeight()) {
                GraphicsConfiguration gc = getGraphicsConfiguration();
                if (volatileImage != null) {
                    volatileImage.flush();
                }
                volatileImage = gc.createCompatibleVolatileImage(videoPanel.getWidth(), videoPanel.getHeight());
            }

            Image img = icon.getImage();
            img.getWidth(videoPanel);
            Graphics g = volatileImage.getGraphics();
            g.clearRect(0, 0, volatileImage.getWidth(), volatileImage.getHeight());
            g.drawImage(img, 0, 0, videoPanel);
            g.dispose();

            videoPanel.getGraphics().drawImage(volatileImage, 0, 0, null);

            if (currentImageIndex % 100 == 0 && currentImageIndex > 100) {
                imagens.subList(0, 50).clear();
            }
        } else {
            frameTimer.stop();
            SwingUtilities.invokeLater(() -> {
                System.gc();
                new MatrixCalculator();
                dispose();
            });
        }
    }


}
