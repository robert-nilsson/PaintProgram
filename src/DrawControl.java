import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

/**
 * Main model of "Drawing Program".
 * Handles user input
 * Listeners for user input,
 * @version 1.1
 * @author Robert Nilsson
 */
public class DrawControl extends JFrame {

    private final DrawModel model;
    private final DrawView view;

    //----Constructor---------------------------------------------------------------
    /**
     * Constructor.
     * Create instances of DrawModel and DrawView, initiate main program with contentpane and panels,
     * implements and initiate listeners
     */
    public DrawControl(){
        //Create a frame for "the program"
        setTitle("Drawing Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,500);
        setLocation(500,500);

        //Create a button panel on top
        ButtonPanel buttonPanel = new ButtonPanel();
        add(buttonPanel, BorderLayout.NORTH);

        //Create the draw surface
        model = new DrawModel();
        view = new DrawView(model);
        MyMouseListener m = new MyMouseListener();
        view.addMouseListener(m);
        view.addMouseMotionListener(m);
        add(view, BorderLayout.CENTER);

        //Create a status panel at the bottom
        Statuspanel statusPanel = new Statuspanel();
        statusPanel.add(model.getModePanelText());
        add(statusPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    //------------------------------------------------------------------------------

    //---Inner classes--------------------------------------------------------------

    /**
     * Panel with buttons for user interaction.
     */
    private class ButtonPanel extends JPanel{
        /**
         * Constructor.
         * Add buttons and connect listeners
         */
        public ButtonPanel(){
            setBackground(Color.LIGHT_GRAY);

            //create buttons
            JButton black = new JButton("black");
            JButton red = new JButton("red");
            JButton green = new JButton("green");
            JButton dot = new JButton("dot");
            JButton oval = new JButton("oval");
            JButton rect = new JButton("rect");
            JButton line = new JButton("line");
            JButton undo = new JButton("undo");
            JButton save = new JButton("save");
            JButton load = new JButton("load");
            //add buttons
            add(black);
            add(red);
            add(green);
            add(dot);
            add(oval);
            add(rect);
            add(line);
            add(undo);
            add(save);
            add(load);
            //add listener to the buttons
            black.addActionListener(new ButtonListener());
            red.addActionListener(new ButtonListener());
            green.addActionListener(new ButtonListener());
            dot.addActionListener(new ButtonListener());
            oval.addActionListener(new ButtonListener());
            rect.addActionListener(new ButtonListener());
            line.addActionListener(new ButtonListener());
            undo.addActionListener(new ButtonListener());
            save.addActionListener(new ButtonListener());
            load.addActionListener(new ButtonListener());

        }
    }

    /**
     * Implements ActionListener for the buttons
     */
    private class ButtonListener implements ActionListener {
        /**
         * Listens to buttons pressed.
         * Selects mode/color/save/load
         * @param e automatically generated event
         */
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            if (input.equals("black")) {
                model.setColor(Color.BLACK);
                model.setModePanelText();
            }
            if (input.equals("red")) {
                model.setColor(Color.RED);
                model.setModePanelText();
            }
            if (input.equals("green")) {
                model.setColor(Color.GREEN);
                model.setModePanelText();
            }
            if (input.equals("dot")) {
                model.setMode("DOT");
                model.setModePanelText();
            }
            if (input.equals("oval")) {
                model.setMode("OVAL");
                model.setModePanelText();
            }
            if (input.equals("rect")) {
                model.setMode("RECTANGLE");
                model.setModePanelText();
            }
            if (input.equals("line")){
                model.setMode(("LINE"));
                model.setModePanelText();
            }
            if (input.equals("undo")) {
                if(model.getFigure() != null){
                    model.undoFigure();
                    view.repaint();
                }
            }
            if (input.equals("save")) {
                SaveLoad m =  new SaveLoad();
                m.save(model.getFigureList());
            }
            if (input.equals("load")) {
                SaveLoad m = new SaveLoad();
                LinkedList<Figures> newList = m.load();
                //only replace with new list if the new list is not empty
                if (newList.peek() != null){
                    model.setFigureList(newList);
                }
                view.repaint();
            }

        }
    }

    /**
     * Listens for user inputs via the mouse.
     * Implements MouseListener and MouseMotionListener
     */
    private class MyMouseListener implements MouseListener, MouseMotionListener{
        /**
         * Sends draw commands to DrawView
         * @param e automatically generated when mouse are pressed
         */
        public void mousePressed(MouseEvent e) {
            if(model.getMode().equals("DOT")){
                model.setStartPoint(e.getX()-7, e.getY()-7);
                model.setEndPoint(e.getX()+7, e.getY()+7);
                Figures f = new Figures(model.drawEllips(), model.getColor());
                model.setFigure(f);
                view.repaint();
            }
            if (model.getMode().equals("RECTANGLE") || model.getMode().equals("OVAL") || model.getMode().equals("LINE")) {
                model.setClicked(true);
                model.setStartPoint(e.getX(), e.getY());
                model.setEndPoint(e.getX(), e.getY());
                view.repaint();
            }
        }

        /**
         * Sends draw commands to DrawView
         * @param e automatically generated when mouse are pressed
         */
        public void mouseDragged(MouseEvent e) {
            if(model.getMode().equals("RECTANGLE") || model.getMode().equals("OVAL") || model.getMode().equals("LINE")) {
                model.setEndPoint(e.getX(), e.getY());
                view.repaint();
            }
        }
        /**
         * Sends draw commands to DrawView
         * @param e automatically generated when mouse are pressed
         */
        public void mouseReleased(MouseEvent e){
            model.setClicked(false);
            model.setEndPoint(e.getX(), e.getY());
            if(model.getMode().equals("RECTANGLE")){
                Figures f = new Figures(model.drawRect(), model.getColor());
                model.setFigure(f);
                view.repaint();
            }
            if(model.getMode().equals("OVAL")) {
                Figures f = new Figures(model.drawEllips(), model.getColor());
                model.setFigure(f);
                view.repaint();
            }
            if(model.getMode().equals("LINE")) {
                Figures f = new Figures(model.drawLine(), model.getColor(), model.getLineWidth());
                model.setFigure(f);
                view.repaint();
            }
        }
        public void mouseClicked(MouseEvent e) {}
        public void mouseMoved(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
    }

    /**
     * Current mode and color selected.
     */
    private static class Statuspanel extends JPanel{
        /**
         * Constructor. Sets layout and displays current mode/color
         */
        public Statuspanel(){
            setBackground(Color.LIGHT_GRAY);
            setLayout(new GridLayout(1,1));
        }
    }
    /**
     * Save/Load Figures stored in a LinkList
     */
    private static class SaveLoad {

        //----Constructor---------------------------------------------------------

        /**
         * Constructor, creates empty new object.
         * Takes no parameters.
         */
        public SaveLoad() {
        }
        //------------------------------------------------------------------------
        //----Methods-------------------------------------------------------------

        /**
         * Save method.
         * @param f LinkedList with Figures that are to be saved
         */
        public void save(LinkedList<Figures> f) {
            try {
                String s = JOptionPane.showInputDialog("Give a file name for save operation:");
                //If cancel or no filename selected
                if(s == null){
                    return;
                }
                FileOutputStream output = new FileOutputStream(s);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
                objectOutputStream.writeObject(f);
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (IOException e) {
                System.out.println("Write failed, because " + e);
            }
        }

        /**
         * Load method.
         * @return a LinkedList<Figures>
         */
        public LinkedList<Figures> load() {
            try {
                String l = JOptionPane.showInputDialog("Give a file name for load operation:");
                //If abort or no filename selected
                if(l == null){
                    return new LinkedList<Figures>();
                }
                FileInputStream input = new FileInputStream(l);
                ObjectInputStream objectInputStream = new ObjectInputStream(input);
                LinkedList<Figures> figures = (LinkedList<Figures>) (objectInputStream.readObject());
                objectInputStream.close();
                return figures;
            } catch (Exception e) {
                System.out.println("Load failed, because " + e);
                return new LinkedList<Figures>();
            }
        }
        //------------------------------------------------------------------------
    }
    //------------------------------------------------------------------------------

    //----Main----------------------------------------------------------------------
    public static void main(String[] args){
        new DrawControl();
    }
    //------------------------------------------------------------------------------
}

