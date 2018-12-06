package com.nullPointer.UI;

import com.nullPointer.Domain.Controller.PlayerController;
import com.nullPointer.Domain.Model.GameEngine;
import com.nullPointer.Domain.Model.Player;
import com.nullPointer.Domain.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Board extends JPanel implements Observer {
    private Image image;
    private File imageSrc = new File("./assets/ultimate_monopoly.png");

    private Point position;
    private int length;
    private int sleepTime = 3;
    private List<Pawn> pawnList;
    private List<Player> playerList = new ArrayList<>();

    private int smallSide;
    private Point initialPosition;

    private PlayerController playerController = PlayerController.getInstance();
    private GameEngine gameEngine = GameEngine.getInstance();
    //new added things below
    private HashMap<Integer, Point[]> squareMap = new HashMap<Integer, Point[]>();
    private ArrayList<Integer> currentPath = new ArrayList<Integer>();
    public static Board instance;

    public ArrayList<Integer> getCurrentPath() {
        return currentPath;
    }

    public HashMap<Integer, Point[]> getSquareMap() {
        return squareMap;
    }

    public static Board getInstance() {
        return instance;
    }

    public Board(Point position, int length) {
        instance = this;
        currentPath.add(57);
        currentPath.add(58);
        currentPath.add(59);
        try {
            image = ImageIO.read(imageSrc);
            image = image.getScaledInstance(length, length, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.position = position;
        this.length = length;

        setPreferredSize(new Dimension(length, length));

        pawnList = new ArrayList<>();

        smallSide = length / 17;
        initialPosition = new Point(14 * smallSide - 20, 14 * smallSide - 20);

        gameEngine.subscribe(this);
        initializeSquarePositions();
    }

    public void initializeSquarePositions() {
        int x = smallSide;
        Point startRightBottom = new Point(17 * x, 17 * x);
        Point startLeftTop = new Point(15 * x, 15 * x);
        Point[] pair = new Point[]{startRightBottom, startLeftTop};
        squareMap.put(0, pair);

        //outer layer
        startRightBottom.x -= 2 * x;
        startLeftTop.x -= x;
        for (int i = 1; i < 13; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.x -= x;
            startLeftTop.x -= x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(13, pair);

        startRightBottom.x -= x;
        startLeftTop.x -= 2 * x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(14, pair);

        startRightBottom.y -= 2 * x;
        startLeftTop.y -= x;
        for (int i = 15; i < 27; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.y -= x;
            startLeftTop.y -= x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(27, pair);

        startRightBottom.y -= x;
        startLeftTop.y -= 2 * x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(28, pair);

        startRightBottom.x += x;
        startLeftTop.x += 2 * x;
        for (int i = 29; i < 41; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.x += x;
            startLeftTop.x += x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(41, pair);

        startRightBottom.x += 2 * x;
        startLeftTop.x += x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(42, pair);

        startRightBottom.y += x;
        startLeftTop.y += 2 * x;
        for (int i = 43; i < 55; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.y += x;
            startLeftTop.y += x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(55, pair);

        //medium layer
        startRightBottom.x -= 2 * x;
        startLeftTop.x -= 2 * x;
        startLeftTop.y -= x;
        squareMap.put(56, pair);

        startRightBottom.x -= 2 * x;
        startLeftTop.x -= x;
        for (int i = 57; i < 65; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.x -= x;
            startLeftTop.x -= x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(65, pair);

        startRightBottom.x -= x;
        startLeftTop.x -= 2 * x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(66, pair);

        startRightBottom.y -= 2 * x;
        startLeftTop.y -= x;
        for (int i = 67; i < 75; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.y -= x;
            startLeftTop.y -= x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(75, pair);

        startRightBottom.y -= x;
        startLeftTop.y -= 2 * x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(76, pair);

        startRightBottom.x += x;
        startLeftTop.x += 2 * x;
        for (int i = 77; i < 85; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.x += x;
            startLeftTop.x += x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(85, pair);

        startRightBottom.x += 2 * x;
        startLeftTop.x += x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(86, pair);

        startRightBottom.y += x;
        startLeftTop.y += 2 * x;
        for (int i = 87; i < 95; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.y += x;
            startLeftTop.y += x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(95, pair);

        //inner layer
        startRightBottom.x -= 2 * x;
        startLeftTop.x -= 2 * x;
        startLeftTop.y -= x;
        squareMap.put(96, pair);

        startRightBottom.x -= 2 * x;
        startLeftTop.x -= x;
        for (int i = 97; i < 101; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.x -= x;
            startLeftTop.x -= x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(101, pair);

        startRightBottom.x -= x;
        startLeftTop.x -= 2 * x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(102, pair);

        startRightBottom.y -= 2 * x;
        startLeftTop.y -= x;
        for (int i = 103; i < 107; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.y -= x;
            startLeftTop.y -= x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(107, pair);

        startRightBottom.y -= x;
        startLeftTop.y -= 2 * x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(108, pair);

        startRightBottom.x += x;
        startLeftTop.x += 2 * x;
        for (int i = 109; i < 113; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.x += x;
            startLeftTop.x += x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(113, pair);

        startRightBottom.x += 2 * x;
        startLeftTop.x += x;
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(114, pair);

        startRightBottom.y += x;
        startLeftTop.y += 2 * x;
        for (int i = 115; i < 119; i++) {
            pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                    new Point(startLeftTop.x, startLeftTop.y)};
            squareMap.put(i, pair);
            startRightBottom.y += x;
            startLeftTop.y += x;
        }
        pair = new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
        squareMap.put(119, pair);

    }

    public void initializePawns() {
        playerList = playerController.getPlayers();
        playerList.forEach(player -> addNewPawn(player));
    }


    public void paint(Graphics g) {
        //g.setColor(color);
        g.fillRect(position.x, position.y, length, length);
        g.drawImage(image, position.x, position.y, length, length, null);
        g.setColor(Color.RED);

		/*for (Entry<Integer, Point[]> entry : squareMap.entrySet())
		{
			g.fillOval(entry.getValue()[0].x, entry.getValue()[0].y,20, 20);
			g.setColor(Color.CYAN);
		}*/
		/*for (Entry<Integer, Point[]> entry : squareMap.entrySet())
		{
			g.fillOval(entry.getValue()[1].x, entry.getValue()[1].y,20, 20);
			g.setColor(Color.GREEN);
		}*/
        pawnList.forEach(pawn -> pawn.paint(g));
    }

    public void addNewPawn(Player player) {
        pawnList.add(new Pawn(initialPosition, player));
    }

    @Override
    public void onEvent(String message) {
        if (message.equals("initializePawns")) {
            initializePawns();
            repaint();
        } else if (message.contains("path")) {
            proccessPath(message);
            pawnList.get(playerController.getCurrentPlayerIndex()).setPath(currentPath);
        }
    }

    private void proccessPath(String message) {
        //path/[57, 58, 59, 60, 61, 62, 63]
        ArrayList<Integer> path = new ArrayList<Integer>();
        message = message.substring(6, message.length() - 1);
        message = message.replaceAll("\\s+", "");
        String[] parts = message.split(",");
        for (String string : parts) {
            path.add(Integer.parseInt(string));
        }
        currentPath = path;
    }
}