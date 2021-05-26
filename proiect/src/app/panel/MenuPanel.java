package app.panel;

import app.client.Globals;
import app.communication.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuPanel extends JPanel {
    private GroupLayout mainLayout = new GroupLayout(this);

    private JLabel titleLabel = new JLabel("Backgammon");

    private JLabel serverAddressLabel = new JLabel("Server Address : ");
    private JLabel usernameLabel      = new JLabel("Username : ");

    private JTextField serverAddressInput = new JTextField("127.0.0.1");
    private JTextField usernameInput = new JTextField();

    private JButton connectButton = new JButton("Search for opponent");

    public MenuPanel() {
        super();

        this.setLayout(this.mainLayout);
        this.mainLayout.setHorizontalGroup(
                this.mainLayout.createSequentialGroup()
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(this.mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(this.titleLabel)
                            .addGroup(this.mainLayout.createSequentialGroup()
                                .addGroup(this.mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.serverAddressLabel)
                                        .addComponent(this.usernameLabel)
                                )
                                .addGroup(this.mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.serverAddressInput)
                                        .addComponent(this.usernameInput)
                                )
                            )
                            .addComponent(this.connectButton)
                    )
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        this.mainLayout.setVerticalGroup(
                this.mainLayout.createSequentialGroup()
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this.titleLabel)
                    .addGap(30)
                    .addGroup(this.mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(this.serverAddressLabel).addComponent(this.serverAddressInput)
                    )
                    .addGroup(this.mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(this.usernameLabel).addComponent(this.usernameInput)
                    )
                    .addComponent(this.connectButton)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        this.connectButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                onConnect();
            }
        });
    }

    private Callback gameFoundCallback = null;

    public void setGameFoundCallback(Callback gameFoundCallback) {
        this.gameFoundCallback = gameFoundCallback;
    }


    void gameFound () {
//        System.out.println("Game Found");
        this.gameFoundCallback.f();
    }

    void connectSuccess () {
        var ip = Globals.getValue("serverIP");
        var port = Integer.parseInt(Globals.getValue("serverPort"));
        var params = new HashMap<String, String>();
        params.put("userID", Globals.getValue("userID"));

        AtomicBoolean foundGame = new AtomicBoolean();
        foundGame.set(false);

        var t = new Thread(() -> {
            while (! foundGame.get()) {
                new Net().makeRequest(
                        new Request(ip, port, params),
                        Codes.clientInMatchmaking,
                        (code, response) -> {
                            if ( code == Codes.serverFoundMatch ) {
                                Globals.setValue("color", response.get("color"));
                                Globals.setValue("opponentName", response.get("opponentName"));
                                Globals.setValue("counts", response.get("counts"));
                                Globals.setValue("players", response.get("players"));
                                foundGame.set(true);
                            }

                            System.out.println("refresh");
                        }
                );

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            gameFound();
        });
        t.start();
        this.connectButton.setEnabled(false);
        this.connectButton.setText("Searching ... ");
    }

    void onConnect () {
        if ( this.usernameInput.getText().isEmpty() ) {
            JOptionPane.showMessageDialog(this.getParent(), "Username Field cannot be empty!",
                    "Username Empty", JOptionPane.ERROR_MESSAGE);

            return;
        }

        int port = 0;
        var tokens = serverAddressInput.getText().split(":");
        if (tokens.length > 1) port = Integer.parseInt(tokens[1]);

        String address = tokens[0];
        if (port == 0) port = 35000;

        Globals.setValue("serverIP", address);
        Globals.setValue("serverPort", "" + port);

        var params = new HashMap<String, String>();
        params.put("user", this.usernameInput.getText());

        new Net().makeRequest(
                new Request(address, port, params),
                Codes.clientConnect,
                (code, response) -> {
                    if ( code == Codes.serverSuccess ) {
                        Globals.setValue("userID", response.get("userID"));
                        connectSuccess();
                    }
                    else if ( code == Codes.serverUserTaken )
                        JOptionPane.showMessageDialog(getParent(), "Username already taken",
                                "Username Taken", JOptionPane.WARNING_MESSAGE);
                    else if ( code == Codes.serverNoSlots )
                        JOptionPane.showMessageDialog(getParent(), "No available game slots",
                                "No Game Slots", JOptionPane.INFORMATION_MESSAGE);
                }
        );
    }
}
