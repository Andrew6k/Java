package app.client;

import java.awt.*;

public class Dice {
    private static int[] values = new int[2];

    public static void roll () {
        values[0] = (int)(Math.random() * 6) + 1;
        values[1] = (int)(Math.random() * 6) + 1;
    }

    public static int[] getValues() {
        return values;
    }

    public static void drawIn (Rectangle r, Graphics g) {
        var width = Math.min(r.width, r.height) / 6;

        var p1 = new Point((int)(Math.random() * (r.width - width)) + r.x,
                (int)(Math.random() * (r.height - width)) + r.y);

        var p2 = new Point((int)(Math.random() * (r.width - width)) + r.x,
                (int)(Math.random() * (r.height - width)) + r.y);
        while ( new Rectangle(p1.x, p1.y, width, width).intersects(new Rectangle(p2.x, p2.y, width, width)) )
            p2 = new Point((int)(Math.random() * (r.width - width)) + r.x,
                    (int)(Math.random() * (r.height - width)) + r.y);
        drawDice(p1, width, g, values[0]);
        drawDice(p2, width, g, values[1]);
    }

    public static final Color diceColorBorder = new Color(120, 120, 120);
    public static final Color diceColor = new Color(200, 200, 200);
    public static final Color diceDotColor = new Color(60, 60, 60);

    private static void drawDice (Point p, int width, Graphics g, int value) {
        g.setColor(diceColorBorder);
        g.fillRoundRect(p.x, p.y, width, width, width / 5, width / 5);
        g.setColor(diceColor);
        g.fillRoundRect(p.x + width / 10, p.y + width / 10, width - width / 5, width - width/5, width/6, width/6);

        g.setColor(diceDotColor);

        int dotWidth = width / 8 - 1;

        if ( value == 1 || value == 3 || value == 5 ) {
            g.fillRect(p.x + width / 2, p.y + width / 2, dotWidth, dotWidth);
        }

        if ( value == 3 || value == 5 || value == 2 || value == 4 || value == 6 ) {
            g.fillRect(p.x + width / 4, p.y + width / 4, dotWidth ,dotWidth);
            g.fillRect(p.x + width / 4 * 3, p.y + width / 4 * 3, dotWidth ,dotWidth);
        }

        if (value == 5 || value == 4 || value == 6) {
            g.fillRect(p.x + width / 4, p.y + width / 4 * 3, dotWidth, dotWidth);
            g.fillRect(p.x + width / 4 * 3, p.y + width/ 4, dotWidth, dotWidth);
        }

        if ( value == 6 ) {
            g.fillRect(p.x + width / 4, p.y + width / 2, dotWidth, dotWidth);
            g.fillRect(p.x + width / 4 * 3, p.y + width / 2, dotWidth, dotWidth);
        }
    }
}
