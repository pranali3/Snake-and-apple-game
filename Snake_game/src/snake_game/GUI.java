
package snake_game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JPanel implements ActionListener 
{

    final int GUI_Width = 250;
    final int GUI_Height = 250;
    final int cell_size = 10;
    final int total_cells = 625;
    
    final int x[] = new int[total_cells];
    final int y[] = new int[total_cells];
    
    private int apple_at_x;
    private int apple_at_y;
    
    final int headvar=0;
    
    private Timer timer;
    private int length;
    
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean game_over = false;
    private boolean start_game=false;
    
    private Image apple;
    private Image head;
    private Image body;
    private JButton restart;
    JFrame snake;
    private JButton start;
    private int score=0;
    
    public GUI(JFrame new_snake)
    {   
        snake=new_snake;
    }
    
    protected void createGUI() 
    {
        setBackground(Color.black);
        setFocusable(true);
        setDoubleBuffered(true);
        addKeyListener(new Adapter());
        
        setPreferredSize(new Dimension(GUI_Width, GUI_Height));
        
        String start_button = "Start Game";
    start = new JButton(start_button);
    Font font = new Font("Arial", Font.BOLD, 12);
    start.setFont(font);
    start.setText(start_button);
    start.setVisible(true);
    start.setPreferredSize(new Dimension(25,25));
    FontMetrics m = getFontMetrics(font);
    setLayout(null);
    int x_loc =(GUI_Width-100)/2;
    int y_loc= (GUI_Height-20) / 2;
    start.setBounds(x_loc,(y_loc),100,20);
    
    add(start);
   
    start.addActionListener((ActionEvent e) -> {
        EventQueue.invokeLater(() -> {
            startGame();
        });
        });  

              
    }

       
    private void startGame() 
    {
        start.setVisible(false);
        start_game=true;
        //load images
        ImageIcon icon_apple = new ImageIcon("src/snake_game/apple.png");
        apple = icon_apple.getImage();
        ImageIcon icon_head = new ImageIcon("src/snake_game/head.png");
        head = icon_head.getImage();
        ImageIcon icon_body = new ImageIcon("src/snake_game/body.png");
        body = icon_body.getImage();
        
        length = 3;
        int i=0;

        while(i<length) 
        {
            x[i] = 50 - i * 10;
            y[i] = 50;
            i++;
        }
        
        apple_at_x = (int)(Math.floor(Math.random()*24)*cell_size);
        apple_at_y = (int)(Math.floor(Math.random()*24)*cell_size);
        
        timer = new Timer(200, this);
        timer.start();
    }
   
    private void gameOver(Graphics g) 
    {

        String message = "Game Over!";
        String scoremsg = "You scored : "+score+" points";
        Font font = new Font("Arial", Font.BOLD, 14);
        g.setFont(font);
        g.setColor(Color.white);
        FontMetrics m = getFontMetrics(font);
//        screen mid
        int x_loc =(GUI_Width-m.stringWidth(message))/2;
        int y_loc= (GUI_Height-50) / 2;
        g.drawString(message, x_loc, y_loc);
        
        x_loc =(GUI_Width-m.stringWidth(scoremsg))/2;
        y_loc= (GUI_Height) / 2;
        g.drawString(scoremsg, x_loc, y_loc);
        
        String button = "Restart";
    restart = new JButton(button);
    
    restart.setFont(font);
    restart.setText(button);
    restart.setVisible(true);
    restart.setPreferredSize(new Dimension(25,25));
    setLayout(null);
    x_loc =(GUI_Width-100)/2;
    y_loc= (GUI_Height+50) / 2;
    restart.setBounds(x_loc,(y_loc+20),100,20);
    add(restart);
    
    restart.addActionListener((ActionEvent e) -> {
        EventQueue.invokeLater(() -> {
            snake.dispose();
            JFrame snake = new Snake();
            snake.setVisible(true);
        });
        });  

    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!start_game)
        {
            Font font = new Font("Arial", Font.BOLD, 12);
            FontMetrics m = getFontMetrics(font);
            g.setFont(font);
            g.setColor(Color.white);
            String instruct = "Use arrows keys to play the game!";
            int x_loc =(GUI_Width-m.stringWidth(instruct))/2;
            int y_loc= (GUI_Height+50) / 2;
            g.drawString(instruct, x_loc, y_loc+10);
        }
        if (!game_over) {

            g.drawImage(apple, apple_at_x, apple_at_y, this);

            for (int i = 0; i < length; i++) {
                if (i == headvar) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }
    
private void movement() {

        for (int i = length-1; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        if (left) 
            x[0] -= cell_size;
        
        if (right) 
            x[0] += cell_size;
        
        if (up) 
            y[0] -= cell_size;
        
        if (down) 
            y[0] += cell_size;
        
    }

    private void collides() {
        //collides with itself
        for (int i = length; i > 0; i--) 
            if ((i > 4) && (x[headvar] == x[i]) && (y[headvar] == y[i])) 
                game_over = true;
           
//        or collides with the wall

        if (y[0] >= GUI_Height || y[0] < 0 || x[0] >= GUI_Width || x[0] < 0)
            game_over = true;
          
        if(game_over)
            timer.stop();

    }

    private void HitsApple() {

        if ((x[headvar] == apple_at_x) && (y[headvar] == apple_at_y)) {
            //length of snake increases
            length++;
            score++;
            apple_at_x = (int)(Math.floor(Math.random()*24)*cell_size);
            apple_at_y = (int)(Math.floor(Math.random()*24)*cell_size);
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
           
        if (!game_over) {
            collides();
            HitsApple();
            movement();
        }

        repaint();
    }

    private class Adapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            
            if ((key == KeyEvent.VK_UP) && (!down)) 
            {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) 
            {
                down = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_LEFT) && (!right)) 
            {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) 
            {
                right = true;
                up = false;
                down = false;
            }

        }
    }
}

