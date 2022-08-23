import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Draw surface.
 * Displays painted, and currently painted, figures.
 * Gets data from DrawModel and DrawControl
 */
public class DrawView extends JPanel {
    
    private final DrawModel model;

    //----Constructor----------------------------------------------

    /**
     * Constructor to DrawView.
     * @param model instance of DrawModel used
     */
    public DrawView(DrawModel model){
        setBackground(Color.white);
        this.model = model;
    }
    //--------------------------------------------------------------
    //----Methods---------------------------------------------------

    /**
     * Paints Figures onto DrawView panel
     * @param g not used. paintComponent is only called via the repaint() function
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //paint stored figures
        if (model.getFigure() != null) {
            for (Figures s : model.getFigureList()) {
                g2d.setColor(s.getColor());
                if(s.getShape() instanceof Line2D){
                    g2d.setStroke(new BasicStroke(s.getLineWidth()));
                    g2d.draw(s.getShape());
                } else
                g2d.fill(s.getShape());
            }
        }
        //paint figures that are being temporarily drawn (mouse dragged)
        if(model.getClicked()) {
            if (model.getMode().equals("RECTANGLE")){
                g2d.setColor(model.getColor());
                g2d.fill(model.drawRect());
            }
            if (model.getMode().equals("OVAL")){
                g2d.setColor(model.getColor());
                g2d.fill((model.drawEllips()));
            }
            if (model.getMode().equals("LINE")){
                g2d.setColor(model.getColor());
                g2d.setStroke(new BasicStroke(model.getLineWidth()));
                g2d.draw(model.drawLine());
            }
        }
    }
    //-----------------------------------------------------------
}
