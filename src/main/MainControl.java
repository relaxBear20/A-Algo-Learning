/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import frame.MainFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import model.ButtonCell;

/**
 *
 * @author admin
 */
public class MainControl {

    static MainFrame frame = new MainFrame();
    static final int height = 40;
    static final int width = 40;
    static final int frameHeight = 400;
    static final int frameWidth = 400;
    static final int rows = 80;
    static final int cols = 80;
    static ButtonCell[][] grid;
    static ArrayList<ButtonCell> openSet = new ArrayList<>();
    static ArrayList<ButtonCell> closeSet = new ArrayList<>();
    static ButtonCell start;
    static ButtonCell end;

    static public void addEvent(ButtonCell b) {
        b.addActionListener((e) -> {
            System.out.println("At " + b.getGridX() + " h = " + b.getH() + " g = " + b.getG());
            for (ButtonCell neighbor : b.getNeighbors()) {
                System.out.println(neighbor.getGridX() + "  " + neighbor.getGridY());
            }
        });
    }

    static public void initSetup() throws Exception {

        frame.getDisplayPanel().setPreferredSize(new Dimension(frameWidth, frameHeight));

        frame.getDisplayPanel().removeAll();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new ButtonCell(i, j);
                addEvent(grid[i][j]);
                grid[i][j].showOnGrid(Color.WHITE);
                grid[i][j].setMargin(new Insets(2, 2, 2, 2));
                grid[i][j].setPreferredSize(new Dimension(width, height));
                frame.getDisplayPanel().add(grid[i][j]);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ButtonCell upperNeighbor = null;
                ButtonCell underNeighbor = null;
                ButtonCell rightNeighbor = null;
                ButtonCell leftNeighbor = null;
                if (i > 0) {
                    upperNeighbor = grid[i - 1][j];
                    grid[i][j].getNeighbors().add(upperNeighbor);
                }
                if (i < rows - 1) {
                    underNeighbor = grid[i + 1][j];
                    grid[i][j].getNeighbors().add(underNeighbor);
                }
                if (j < cols - 1) {

                    rightNeighbor = grid[i][j + 1];
                    grid[i][j].getNeighbors().add(rightNeighbor);
                }
                if (j > 0) {
                    leftNeighbor = grid[i][j - 1];
                    grid[i][j].getNeighbors().add(leftNeighbor);
                }

            }
        }
        start = grid[0][0];
        end = grid[rows - 1][0];

        openSet.add(start);

        frame.setVisible(false);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws Exception {

        try {
            frame.getDisplayPanel().setLayout(new GridLayout(rows, cols));
            grid = new ButtonCell[rows][cols];
            initSetup();

            draw();
        } catch (InterruptedException ex) {
            Logger.getLogger(MainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double heuristis(ButtonCell bc, ButtonCell end) {
        double x1 = bc.getGridX();
        double x2 = end.getGridX();
        double y1 = bc.getGridY();
        double y2 = end.getGridY();
        return Point2D.distance(x1, y1, x2, y2);
    }

    public static ArrayList<ButtonCell> Path = new ArrayList<>();

    public static void draw() throws InterruptedException {
        while (true) {
            if (!openSet.isEmpty()) {

                int lastIndex = 0;
                for (int i = 0; i < openSet.size(); i++) {
                    if (openSet.get(i).getF() < openSet.get(lastIndex).getF()) {
                        lastIndex = i;
                    }
                }

                ButtonCell current = openSet.get(lastIndex);

                if (current.equals(end)) {
                    //found solution

                    //Re Draw Path
                    ButtonCell temp = current;
                    Path.add(temp);
                    while (temp.getPreviousCell() != null) {
                        Path.add(temp);
                        temp = temp.getPreviousCell();
                    }
                    
                    for (ButtonCell buttonCell : Path) {
                        buttonCell.showOnGrid(Color.BLUE);
                    }

                    System.out.println("DONE");
                    break;
                }

                openSet.remove(current);
                closeSet.add(current);

                ArrayList<ButtonCell> neighbors = current.getNeighbors();
                for (ButtonCell neighbor : neighbors) {
                    if (!closeSet.contains(neighbor)) {
                        int tempG = current.getG() + 1;
                        if (openSet.contains(neighbor)) {
                            if (neighbor.getG() > tempG) {
                                neighbor.setG(tempG);
                            }
                        } else {
                            neighbor.setG(tempG);
                            openSet.add(neighbor);
                        }

                        neighbor.setH(heuristis(neighbor, end));
                        neighbor.setF(neighbor.getH() + neighbor.getG());
                        neighbor.setPreviousCell(current);
                    }
                }

            } else {
                //no solusion
                break;
            }

            for (ButtonCell buttonCell : openSet) {
                buttonCell.showOnGrid(Color.GREEN);
            }
            for (ButtonCell buttonCell : closeSet) {
                buttonCell.showOnGrid(Color.RED);
            }

            TimeUnit.MILLISECONDS.sleep(7);

        }

    }

}
