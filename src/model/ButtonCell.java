/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author admin
 */
public class ButtonCell extends JButton{
    private int gridX;
    private int gridY;
    private double h;
    private int g;
    private double f;
    private ButtonCell previousCell = null;

    public ButtonCell getPreviousCell() {
        return previousCell;
    }

    public void setPreviousCell(ButtonCell previousCell) {
        this.previousCell = previousCell;
    }
    private ArrayList<ButtonCell> neighbors = new ArrayList<>();

    public ArrayList<ButtonCell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<ButtonCell> neighbor) {
        this.neighbors = neighbor;
    }

    
    
    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
    

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public ButtonCell(int x, int y) {
        this.gridX = x;
        this.gridY = y;
        
    }

    @Override
    public boolean equals(Object obj) {
        ButtonCell g = (ButtonCell) obj;
        return  g.getGridX() == this.gridX && g.getGridY() == this.gridY; //To change body of generated methods, choose Tools | Templates.
    }
    
public void showOnGrid(Color color) {
    this.setBackground(color);
}
    public ButtonCell() {
        super();
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

  

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }
    
    
}
