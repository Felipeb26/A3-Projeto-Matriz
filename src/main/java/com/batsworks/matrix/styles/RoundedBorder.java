package com.batsworks.matrix.styles;

import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RoundedBorder  extends LineBorder {
    private final int radius;

    public RoundedBorder() {
        super(Color.BLACK, 2, true); // Define a cor e a espessura da borda
        this.radius = 5;
    }

    public RoundedBorder(int radius) {
        super(Color.BLACK, 2, true); // Define a cor e a espessura da borda
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getLineColor());
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

}