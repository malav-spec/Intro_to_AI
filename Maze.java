import java.util.*;
import java.awt.Point;
import javax.swing.*;

public class Maze extends JFrame {
    int rows,columns;
      Cell[][] Grid;
      PriorityQueue<Cell> open = new PriorityQueue<Cell>((Cell c1, Cell c2)->
              c1.f < c2.f ? -1 : c1.f > c2.f ? 1 : 0);
    
      Set<Cell> close = new HashSet<>();
      List<Cell> path = new ArrayList<Cell>();
      public Maze(){

      }

     double Eucledian_Distance(Point a, Point b){
        double x_dist = Math.pow((b.x - a.x), 2);
        double y_dist = Math.pow((b.y - a.y), 2);
         return Math.sqrt(x_dist + y_dist);
    }
    
    void AStar(){
        
        open.add(Grid[0][0]);
        while(!open.isEmpty()){
            Cell currentCell = open.poll();
            close.add(currentCell);
            if(isGoalCell(currentCell)){
                getSolution(currentCell);
            }
            else{
                exploreNeighbour(currentCell);
            }
        }
        
    }
    
    public void getSolution(Cell finalCell){
        
        path.add(finalCell);
        Cell ptr = finalCell.parent; 
        while(ptr != null){
            path.add(0,ptr);
            ptr = ptr.parent;
        }
     printPath(path);   
    }
    
    void printPath(List<Cell> path){
        int i;
        
        for(i=0;i<path.size(); i++){
            System.out.println("(" + path.get(i).coordinates.x +"," + path.get(i).coordinates.y + ")");
        }
    }
    
    public void exploreNeighbour(Cell cell){
        exploreUp(cell);
        exploreDown(cell);
        exploreMiddle(cell);
    }
    
    public void exploreUp(Cell cell){
        int row = cell.coordinates.x - 1;
        Cell nextCell;

        if(row >= 0 ){
            if(cell.coordinates.y - 1 >= 0){
                nextCell = getCellFromGrid(row,cell.coordinates.y - 1);

                if( nextCell.isBlocked) {//Next cell is blocked
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, 0);
                }
                else if(cell.isHard && nextCell.isHard){//2 hard cells
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, Math.sqrt(8));
                }
                else if((!cell.isHard && nextCell.isHard) || (cell.isHard && !nextCell.isHard)){//1 easy and 1 hard
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, (Math.sqrt(2) + Math.sqrt(8))/2);
                }

                else if(!cell.isHard && !nextCell.isHard){//Regular Cell
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, Math.sqrt(2));
                }
            }
             if(cell.coordinates.y + 1 <= columns-1){

                 nextCell = getCellFromGrid(row,cell.coordinates.y + 1);

                 if( nextCell.isBlocked) {//Next cell is blocked
                     checkCellIfValid(cell, row, cell.coordinates.y + 1, 0);
                 }
                 else if(cell.isHard && nextCell.isHard){//2 hard cells
                     checkCellIfValid(cell, row, cell.coordinates.y + 1, Math.sqrt(8));
                 }
                 else if((!cell.isHard && nextCell.isHard) || (cell.isHard && !nextCell.isHard)){//1 easy and 1 hard
                     checkCellIfValid(cell, row, cell.coordinates.y + 1, (Math.sqrt(2) + Math.sqrt(8))/2);
                 }

                 else if(!cell.isHard && !nextCell.isHard){//Regular Cell
                     checkCellIfValid(cell, row, cell.coordinates.y + 1, Math.sqrt(2));
                 }
            }
             //HV movement
            nextCell = getCellFromGrid(row,cell.coordinates.y);
             if(nextCell.isHard && cell.isHard){
                 checkCellIfValid(cell, row, cell.coordinates.y, 2);
             }
             else if(nextCell.isBlocked){
                 checkCellIfValid(cell, row, cell.coordinates.y , 0);
            }
             else if((nextCell.isHard && !cell.isHard) || (!nextCell.isHard && cell.isHard) ){
                 checkCellIfValid(cell, row, cell.coordinates.y , 1.5);
             }
             else if(!nextCell.isHard && !cell.isHard){
                 checkCellIfValid(cell, row, cell.coordinates.y, 1);
             }

        }
    }
    
    public void exploreDown(Cell cell){
        int row = cell.coordinates.x + 1;
        Cell nextCell;

        if(row < rows){
            if(cell.coordinates.y - 1 >= 0){
                nextCell = getCellFromGrid(row-1,cell.coordinates.y - 1);

                if(nextCell.isBlocked) {//Next cell is blocked
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, 0);
                }
                else if(cell.isHard && nextCell.isHard){//2 hard cells
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, Math.sqrt(8));
                }
                else if((!cell.isHard && nextCell.isHard) || (cell.isHard && !nextCell.isHard)){//1 easy and 1 hard
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, (Math.sqrt(2) + Math.sqrt(8))/2);
                }

                else if(!cell.isHard && !nextCell.isHard){//Regular Cell
                    checkCellIfValid(cell, row, cell.coordinates.y - 1, Math.sqrt(2));
                }

            }
            if(cell.coordinates.y + 1 <= columns-1){
                nextCell = getCellFromGrid(row,cell.coordinates.y + 1);

                if( nextCell.isBlocked) {//Next cell is blocked
                    checkCellIfValid(cell, row, cell.coordinates.y + 1, 0);
                }
                else if(cell.isHard && nextCell.isHard){//2 hard cells
                    checkCellIfValid(cell, row, cell.coordinates.y + 1, Math.sqrt(8));
                }
                else if((!cell.isHard && nextCell.isHard) || (cell.isHard && !nextCell.isHard)){//1 easy and 1 hard
                    checkCellIfValid(cell, row, cell.coordinates.y + 1, (Math.sqrt(2) + Math.sqrt(8))/2);
                }

                else if(!cell.isHard && !nextCell.isHard){//Regular Cell
                    checkCellIfValid(cell, row, cell.coordinates.y + 1, Math.sqrt(2));
                }
            }

            nextCell = getCellFromGrid(row,cell.coordinates.y);

            if(nextCell.isHard && cell.isHard){
                checkCellIfValid(cell, row, cell.coordinates.y, 2);
            }
            else if(nextCell.isBlocked){
                checkCellIfValid(cell, row, cell.coordinates.y , 0);
            }
            else if((nextCell.isHard && !cell.isHard) || (!nextCell.isHard && cell.isHard) ){
                checkCellIfValid(cell, row, cell.coordinates.y , 1.5);
            }
            else if(!nextCell.isHard && !cell.isHard){
                checkCellIfValid(cell, row, cell.coordinates.y, 1);
            }

        }
    }
    
    public void exploreMiddle(Cell cell){
        int row = cell.coordinates.x;
        
        if(cell.coordinates.y - 1 >= 0){
            checkCellIfValid(cell, row, cell.coordinates.y - 1, 1);
        }
        if(cell.coordinates.y + 1 <= columns-1){
            checkCellIfValid(cell, row, cell.coordinates.y + 1, 1);
        }
    }
    
    public void checkCellIfValid(Cell cell, int row, int col, double cost){
        Cell neighbour = getCellFromGrid(row, col);
        if(neighbour != null && !neighbour.isBlocked && !close.contains(neighbour)){
            
            if(!open.contains(neighbour)){
                neighbour.setCellData(cell, 1);
                open.add(neighbour);
            }
            else{
                boolean betterPath = neighbour.checkPath(cell, cost);
                if(betterPath){
                    open.remove(neighbour);
                    open.add(neighbour);
                }
            }
           
        }
       
    }
    
    
    
    public Cell getCellFromGrid(int x, int y){
        int i,j;
        for(i=0;i<rows;i++){
         for(j=0;j<columns;j++){
             if(i ==x && j == y){
                 return Grid[i][j];
                }
            }
        }
        return null;
    }
    
    public boolean isGoalCell(Cell cell){
        return cell.goal;
    }
    
    public static void main(String[] args){


     Scanner sc= new Scanner(System.in);
     Maze obj = new Maze();
     Random rand = new Random();
     int i,j;
     int count = 0;
     
     System.out.println("Enter number of rows:");
     obj.rows = sc.nextInt();
     
     System.out.println("Enter number of columns:");
     obj.columns = sc.nextInt();
     
     obj.Grid = new Cell[obj.rows][obj.columns];
     for(i=0;i<obj.rows;i++){
         for(j=0;j<obj.columns;j++){
            Cell c = new Cell();
            c.coordinates.x = i;
            c.coordinates.y = j;
            c.id = count;
            count++;
            obj.Grid[i][j] = c;
           }
        }

        obj. Grid[0][0].g = 0;
        obj.Grid[0][0].start = true;
        obj.Grid[obj.rows-1][obj.columns-1].goal = true;

     for(i=0;i<obj.rows;i++){
         for(j=0;j<obj.columns;j++){
             int blocked = rand.nextInt(100);
             int hard = rand.nextInt(100);
             if( blocked <= 30 && !obj.Grid[i][j].goal && !obj.Grid[i][j].start ){
                 obj.Grid[i][j].isBlocked = true;
                }
             if(hard <= 70 && !obj.Grid[i][j].goal && !obj.Grid[i][j].start){
                 obj.Grid[i][j].isHard = true;
             }
            }
          
        }
        
     for(i=0;i<obj.rows;i++){
         for(j=0;j<obj.columns;j++){
             Maze point = new Maze();
             Point goal = new Point(2,2);
             obj.Grid[i][j].heuristic = point.Eucledian_Distance(obj.Grid[i][j].coordinates, goal);
            }
          
        }

     for(i=0;i<obj.rows;i++){
         for(j=0;j<obj.columns;j++){
            System.out.print("("+ obj.Grid[i][j].coordinates.x + "," + obj.Grid[i][j].coordinates.y +")" + ": ");
            System.out.print(obj.Grid[i][j].isHard + " ");
            }
            System.out.println();
        }
        //GUI.main(obj.rows,obj.columns,null,obj.Grid);

       obj.AStar();

       GUI.main(obj.rows,obj.columns,obj.path,obj.Grid);
      
       
    }
}