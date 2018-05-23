
package snake_game;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Snake extends JFrame {

    Snake() 
    {
        start(this);
    }
    
    public void start(JFrame snake) {
        
        GUI gui= new GUI(snake);
        add(gui);
        gui.createGUI(); 
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void createJFrame()
    {
        EventQueue.invokeLater(() -> {
            JFrame snake = new Snake();
            snake.setVisible(true);
        });
    }
    
    public static void main(String[] args) 
    {
        createJFrame();
        
    }
}
