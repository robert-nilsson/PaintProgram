import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 * Logic for calculating Figures using coordinates.
 * Stores all generated Figures
 */
public class DrawModel {

    private LinkedList<Figures> figureList = new LinkedList<>();
    private int y, x, y2, x2;
    private Color color;
    private String mode;
    private float lineWidth = 2.0f;
    private final JLabel statusPanelText;
    private boolean clicked; //needed so paintComponent only draws "temp" figure during a mouse drag

    //----Constructor----------------------------------------------

    /**
     * Constructor.
     * Initiate coordinates to 0, mode to "no mode", color to black
     * and show this mode/color info in the status panel
     */
    public DrawModel(){
        this.mode = "No figure selected";
        this.color = Color.BLACK;
        this.statusPanelText = new JLabel("Mode: " + getMode() + " Color: "+ getColor());
        this.y = this.x = this.x2 = this.y2 = 0;
    }
    //-------------------------------------------------------------

    //----Getters and Setters----------------------------------
    public JLabel getModePanelText(){
        return statusPanelText;
    }
    public void setModePanelText(){
        statusPanelText.setText("Mode: " + getMode() + " Color: "+ getColor());
    }
    public String getMode(){
        return mode;
    }
    public void setMode(String m){
        mode = m;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color c){
        color = c;
    }
    public Figures getFigure(){
        return figureList.peek();
    }
    public void setFigure(Figures s){
        figureList.add(s);
    }
    public LinkedList<Figures> getFigureList(){
        return figureList;
    }
    public void setFigureList(LinkedList<Figures> f){
        this.figureList = f;
    }
    public void setClicked(boolean b){
        this.clicked = b;
    }
    public boolean getClicked(){return this.clicked;}
    public float getLineWidth(){return this.lineWidth;}

    //----------------------------------------------------------

    //----Methods-----------------------------------------------

    /**
     * Initiate a Figure start coordinates
     * @param x x-value
     * @param y y-value
     */
    public void setStartPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Sets end coordinates for Figure
     *
     * @param x2 x-value
     * @param y2 y-value
     */
    public void setEndPoint(int x2, int y2){
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Calculates corners for a rectangle
     * @return Rectangle2d object
     */
    public Rectangle2D drawRect(){
        int px = Math.min(x, x2);
        int py = Math.min(y, y2);
        int pw = Math.abs(x-x2);
        int ph = Math.abs(y-y2);
        return new Rectangle2D.Double(px, py, pw, ph);
    }

    /**
     * Calculate ellipse "corners".
     * @return an Ellipse2D object
     */
    public Ellipse2D drawEllips(){
        int px = Math.min(x, x2);
        int py = Math.min(y, y2);
        int pw = Math.abs(x-x2);
        int ph = Math.abs(y-y2);
        return new Ellipse2D.Double(px, py, pw, ph);
    }

    /**
     * Draws a line from star to end position.
     * @return a Line2D object
     */
    public Line2D drawLine(){
        int sx = x;
        int sy = y;
        int ex = x2;
        int ey = y2;
        return new Line2D.Double(sx, sy, ex, ey) {
        };
    }

    /**
     * Removes last added figure in LinkedList
     */
    public void undoFigure(){
        this.figureList.removeLast();
    }
    //----------------------------------------------------------
}
