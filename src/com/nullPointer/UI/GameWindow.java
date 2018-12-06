package com.nullPointer.UI;

import com.nullPointer.Domain.Controller.PlayerController;
import com.nullPointer.Domain.Model.GameEngine;
import com.nullPointer.Domain.Model.Player;
import com.nullPointer.Domain.Observer;
import com.nullPointer.Domain.Server.ServerInfo;
import com.nullPointer.Utils.ColorSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JPanel implements Observer {
    protected Board board;
    protected DiceDisplay diceDisplay;
    protected ButtonPanel buttonPanel;
    protected PlayerPanel playerPanel;

    private GameEngine gameEngine = GameEngine.getInstance();
    private PlayerController playerController = PlayerController.getInstance();
    private ServerInfo serverInfo = ServerInfo.getInstance();

    private ArrayList<JButton> disabledButtons = new ArrayList<JButton>();

    public GameWindow(int width, int height) {
        super();
        ColorSet colorSet = new ColorSet();
        int offset = 50;


        JPanel contentPane = new JPanel();
        //contentPane.setLayout(new BoxLayout(contentPane, BorderLayout));
        board = new Board(new Point(0, 0), height - offset);

        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.add(board, BorderLayout.LINE_START);

        JPanel middleSide = new JPanel();
        middleSide.setLayout(new BoxLayout(middleSide, BoxLayout.Y_AXIS));

        diceDisplay = new DiceDisplay();
        middleSide.add(diceDisplay);
        buttonPanel = new ButtonPanel();
        middleSide.add(buttonPanel);
        contentPane.add(middleSide, BorderLayout.CENTER);

        JPanel rightSide = new JPanel();
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));

        playerPanel = new PlayerPanel();
        rightSide.add(playerPanel);
        MessageBox msg = new MessageBox();
        rightSide.add(msg);
        contentPane.add(rightSide, BorderLayout.LINE_END);

        this.add(contentPane);
        setOpaque(false);
        contentPane.setBackground(colorSet.getBackground());
        gameEngine.subscribe(this);
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public void paint(Graphics g) {
        super.paint(g);
        //board.paint(g);
    }

    @Override
    public void onEvent(String message) {
        if (gameEngine.isMyTurn()) {
            if (message.equals("buy")) {
                buttonPanel.purchaseButton.setEnabled(true);
            }
            if (message.equals("rollDice")) {
                buttonPanel.rollDice.setEnabled(true);
                buttonPanel.endTurn.setEnabled(true);
            }
            if (message.equals("drawCard")) {
                buttonPanel.drawButton.setEnabled(true);
            }
            if (message.equals("playCard")) {
                buttonPanel.playCardButton.setEnabled(true);
            }
            if (message.equals("improve")) {
                buttonPanel.improveButton.setEnabled(true);
            }
            if (message.equals("resume")) {
                enableButtons();
                System.out.println("[GameWindow: resumed]");
            }
        }
        if (message.equals("pause")) {
            disableButtons();
            System.out.println("[GameWindow: paused]");
        }
    }

    private void enableButtons() {
        buttonPanel.pauseButton.setEnabled(true);
        for (int i = 0; i < disabledButtons.size(); i++) {
            JButton currButton = disabledButtons.get(i);
            currButton.setEnabled(true);
        }
        disabledButtons.clear();
        buttonPanel.resumeButton.setEnabled(false);
    }

    private void disableButtons() {
        if (buttonPanel.rollDice.isEnabled()) {
            disabledButtons.add(buttonPanel.rollDice);
            buttonPanel.rollDice.setEnabled(false);
        }
        if (buttonPanel.purchaseButton.isEnabled()) {
            disabledButtons.add(buttonPanel.purchaseButton);
            buttonPanel.purchaseButton.setEnabled(false);
        }
        if (buttonPanel.drawButton.isEnabled()) {
            disabledButtons.add(buttonPanel.drawButton);
            buttonPanel.drawButton.setEnabled(false);
        }
        if (buttonPanel.playCardButton.isEnabled()) {
            disabledButtons.add(buttonPanel.playCardButton);
            buttonPanel.playCardButton.setEnabled(false);
        }
        if (buttonPanel.improveButton.isEnabled()) {
            disabledButtons.add(buttonPanel.improveButton);
            buttonPanel.improveButton.setEnabled(false);
        }
        buttonPanel.pauseButton.setEnabled(false);
        disabledButtons.add(buttonPanel.pauseButton);
    }
}
