import java.awt.*;
import javax.swing.*;
public class Cell extends JFrame{
    int id;
    Point coordinates = new Point(0,0);
    boolean isBlocked;
    double heuristic;
    double f;
    double g;
    boolean start;
    boolean goal;
    boolean isHard;
    boolean highway;
    boolean river;
    Cell parent;

    public Cell(){
        this.id = 0;
        coordinates.x = 0;
        coordinates.y = 0;
        isBlocked = false;
        heuristic = 0;
        f = 0;
    }
    
    public void setCellData(Cell cell, double cost){
        double G_VALUE = cell.g + cost;
        this.parent = cell;
        this.g = G_VALUE;
        this.f = G_VALUE + this.heuristic;
    }
    
    public boolean checkPath(Cell cell, double cost){
        double G_VALUE = cell.g + cost;
        if(G_VALUE < this.g){
         setCellData(cell, cost); 
         return true;
        }
        return false;   
    }
}