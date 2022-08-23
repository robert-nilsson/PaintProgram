import java.awt.*;
import java.io.Serializable;

/**
 * Creates a figure containing a
 * Graphics2d shape with attached color
 *
 */
public class Figures implements Serializable {

    private final Shape shape;
    private final Color color;
    private float lineWidth;

    /**
     * Constructor for new figure with shape and color.
     *
     * @param s the figure shape
     * @param c the figure color
     */
    //----Constructor-----------------------------------
    public Figures(Shape s, Color c){
        this.shape = s;
        this.color = c;
    }
    public Figures(Shape s, Color c, float lineWidth){
        this.shape = s;
        this.color = c;
        this.lineWidth = lineWidth;
    }
    //--------------------------------------------------

    //----Getters and setters---------------------------
    public Color getColor(){
        return color;
    }
    public Shape getShape(){
        return shape;
    }
    public void setLineWidth(float lineWidth){this.lineWidth = lineWidth;}
    //TODO -create pull down menu with selectable linewidth
    public float getLineWidth(){return this.lineWidth;}
    //---------------------------------------------------
}
