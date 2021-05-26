package app;

import app.client.Globals;
import app.communication.Codes;
import app.communication.Net;
import app.communication.Request;
import app.panel.GamePanel;
import app.panel.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class GameApplication extends JFrame {
    private JPanel activePanel = null;

    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public GameApplication(String title, int width, int height) {
        super();

        super.setTitle(title);
        super.setSize(new Dimension(width, height));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                var params = new HashMap<String, String>();
                if (Globals.valueExists("userID"))
                    params.put("userID", Globals.getValue("userID"));

                new Net().makeRequest(
                        new Request("127.0.0.1", 35000, params),
                        Codes.clientDisconnect,
                        (code, response) -> {
//                            System.out.println(code + " : " + response);
                        }
                );
            }
        });
        menuPanel = new MenuPanel();
        menuPanel.setGameFoundCallback(this::switchToGame);

        gamePanel = new GamePanel();

        this.switchToMenu ();
//        this.switchToGame();
    }

    public void switchToMenu () {
//        panel.setGameFoundCallback(this::switchToGame);

//        this.add(this.activePanel, BorderLayout.CENTER);
        this.setContentPane(this.menuPanel);
        gamePanel.setVisible(false);
        menuPanel.setVisible(true);
        this.revalidate();
    }

    public void switchToGame () {
//        this.
//        this.remove(this.activePanel);
//        this.add(this.activePanel, BorderLayout.CENTER);
        this.setContentPane(this.gamePanel);
        gamePanel.setVisible(true);
        menuPanel.setVisible(false);
        this.gamePanel.reinitialise();

        this.revalidate();
    }

    public static void main(String[] args) {
        new GameApplication("Backgammon", 1024, 768).setVisible(true);
    }
}
