package com.nullPointer.UI;

import com.nullPointer.Domain.Controller.CommunicationController;
import com.nullPointer.Domain.Model.GameEngine;
import com.nullPointer.Domain.Model.Player;
import com.nullPointer.Domain.Observer;
import com.nullPointer.Domain.Server.ServerInfo;
import com.nullPointer.Utils.ColorSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class ServerWindow extends JPanel implements Observer {
    private JButton startGame;
    private JButton addPlayer;
    private JButton quitServer;
    private CommunicationController communicationController = CommunicationController.getInstance();
    private GameEngine gameEngine = GameEngine.getInstance();
    private ServerInfo serverInfo = ServerInfo.getInstance();
    private Navigator navigator = Navigator.getInstance();
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JPanel playerPanel;
    private List<ClientDisplay> clientDisplayList;
    private int buttonHeight = 40;
    private int buttonWidth = 180;

    private int pButtonHeight = 50;
    private int pButtonWidth = 200;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private Image background;
    private File backgroundSrc = new File("./assets/background2.jpg");
    JLabel back;

    public ServerWindow() {

        buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //buttonPanel.add(new JLabel("Server Screen"));
        buttonPanel.setPreferredSize(new Dimension(buttonWidth, 4 * buttonHeight));
        buttonPanel.setOpaque(false);
        this.add(buttonPanel);
        addButtons(buttonPanel);

        gameEngine.subscribe(this);

        try {
            background = ImageIO.read(backgroundSrc);
            background = background.getScaledInstance(
                    screenSize.width,
                    screenSize.height,
                    Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createClientDisplay();
        createPlayerDisplay();
        this.add(scrollPane);

        ImageIcon backgroundIcon = new ImageIcon(background);
        back = new JLabel();
        back.setIcon(backgroundIcon);
        add(back);

    }

    private void addButtons(JPanel panel) {

        startGame = new CustomButton("Start Game");
        startGame.setToolTipText("Start the game ");
        startGame.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
                //navigator.gameScreen();
            }
        });
        panel.add(startGame);

        addPlayer = new CustomButton("Add player");
        addPlayer.setToolTipText("add new player ");
        addPlayer.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player player = new Player("Test", serverInfo.getClientID());
                communicationController.sendClientMessage(player);
                //navigator.gameScreen();
            }
        });
        panel.add(addPlayer);

        quitServer = new CustomButton("Quit Server ");
        quitServer.setToolTipText("Quit from the server");
        quitServer.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        quitServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                communicationController.removeClient();
                navigator.menuScreen();
            }
        });
        panel.add(quitServer);

    }

    private void startServer() {
        communicationController.sendClientMessage("game/start");
    }

    public List<ClientDisplay> createClientDisplay() {
        List<Integer> clientList = serverInfo.getClientList();
        clientDisplayList = new ArrayList<>();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        for (int i = 0; i < clientList.size(); i++) {
            if (i < 6) {
                ClientDisplay clientDisplay = new ClientDisplay("Computer " + (i + 1), new Point(50, i * height / 6), ColorSet.getPlayerColors().get(i));
                clientDisplayList.add(clientDisplay);
            } else {
                ClientDisplay clientDisplay = new ClientDisplay("Computer " + (i + 1), new Point(400, (i - 6) * height / 6), ColorSet.getPlayerColors().get(i));
                clientDisplayList.add(clientDisplay);
            }
        }
        return clientDisplayList;
    }

    public void addClient() {
        createClientDisplay();
    }

    public void createPlayerDisplay() {
        playerPanel = new JPanel();
        //playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setPreferredSize(new Dimension(pButtonWidth + 30, 12 * (pButtonHeight + 10)));
        scrollPane = new JScrollPane(playerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(screenSize.width / 3 * 2, screenSize.height / 80, 150, 300);
        this.add(scrollPane);

    }

    public void addPlayer() {
        ArrayList<Player> pList = gameEngine.getPlayerController().getPlayers();
        CustomButton newButton = new CustomButton(pList.get(pList.size() - 1).getName());
        newButton.setPreferredSize(new Dimension(pButtonWidth, pButtonHeight));

        playerPanel.add(newButton);
        playerPanel.validate();
        scrollPane.validate();

    }

    public void paint(Graphics g) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        super.paint(g);
        //g.drawImage(background, 0, 0, null);
        back.setLocation(0, 0);
        clientDisplayList.forEach(clientDisplay -> clientDisplay.paint(g));
        buttonPanel.setLocation((screenSize.width - buttonPanel.getWidth()) / 2, 200);
        scrollPane.setLocation((screenSize.width ) / 4 * 3, 100);

    }

    @Override
    public void onEvent(String message) {
        if (message.equals("newClient")) {
            this.addClient();
            repaint();
        } else if (message.equals("newPlayer")) {
            addPlayer();
            //repaint();
        }
    }
}

class ClientDisplay extends JPanel {

    String clientName;
    Point position;
    int width = 300;
    int height = 100;
    Random rand = new Random();
    Color clientColor;

    ClientDisplay(String name, Point position, Color color) {
        clientName = name;
        this.position = position;
        clientColor = color;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(clientColor);
        g2.setFont(new Font("Corbel", Font.PLAIN, 20));
        g2.drawString(clientName, position.x + 100, position.y + height / 2);
        g2.setStroke(new BasicStroke(2.0F));
        g2.drawRect(position.x, position.y, width, height);
    }
}
