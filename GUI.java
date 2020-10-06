import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
   
   // Maze ob = new Maze();
 int g_row;
 int g_col;
 List<Cell> path;
 Cell[][] Grid;

 public GUI(){
 }

 public void paint(Graphics g) {
        super.paint(g);
        //drawRectangles(g);
        drawGrid(g);
    }
 public GUI(int r, int c, List<Cell> path, Cell[][] Grid) {
        super("Rectangles Drawing Demo");
        g_row = r;
        g_col = c;
        this.path = path;
        this.Grid = Grid;

        getContentPane().setBackground(Color.WHITE);
     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     setSize(screenSize.width, screenSize.height);
        //setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

     JPanel container = new JPanel();
     JScrollPane scrPane = new JScrollPane(container);
     scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
     scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     add(scrPane);
            
    }
    
 
    public void drawGrid(Graphics g){
        
            Graphics2D g2d = (Graphics2D)g;
            int r= 10;
            int c = 50;

        for(int j =0; j < g_row; j++){
                for (int i = 0; i < g_col; i++) {
                    if(Grid[j][i].goal){
                        g2d.setColor(Color.RED);
                        g2d.draw3DRect(r, c, 20, 20, true);
                        g2d.fillRect(r, c, 20, 20);
                    }

                    if(path.contains(Grid[j][i]) ){
                        if(Grid[i][j].isHard) {
                            g2d.setColor(Color.PINK);
                            g2d.draw3DRect(r, c, 20, 20, true);
                            g2d.fillRect(r, c, 20, 20);
                        }
                        else if(Grid[j][i].goal || Grid[j][i].start){
                            g2d.setColor(Color.RED);
                            g2d.draw3DRect(r, c, 20, 20, true);
                            g2d.fillRect(r, c, 20, 20);
                        }
                        else{
                            g2d.setColor(Color.GREEN);
                            g2d.draw3DRect(r, c, 20, 20, true);
                            g2d.fillRect(r, c, 20, 20);
                        }
                    }
                    else if(Grid[j][i].isBlocked){
                       g2d.setColor(Color.black);
                       g2d.draw3DRect(r,c,20,20,true);
                       g2d.fillRect(r, c, 20, 20);
                    }

                    else if(Grid[j][i].isHard){
                        g2d.setColor(Color.GRAY);
                        g2d.draw3DRect(r,c,20,20,true);
                        g2d.fillRect(r, c, 20, 20);
                    }


                    else{
                       g2d.setColor(Color.BLUE);
                       g2d.draw3DRect(r,c,20,20,true);
                       g2d.fillRect(r, c, 20, 20);
                    }
                    r= r+20;
                    
                }
                c=c+20;
                r=10;
            }
    }
    
 public static void main(int r, int c, List<Cell> path, Cell[][] Grid){

            SwingUtilities.invokeLater(() -> new GUI(r,c,path,Grid).setVisible(true));
        
    }
    
}