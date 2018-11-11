import java.util.*;
import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class dodger extends JFrame implements KeyListener
{	
	String player = "Player";
	private int rock_size = 20;
	private int rock_x[] = new int[50];
	private int rock_y[] = new int[50];
	private int player_y = 540;				//Player position
	private int player_x = 310;
	private int score = 0;
	int front = 0;						//Queue since for every new rock position changes
	int rear = 0;
	int frame_left = 50;
	int frame_top = 50;
	int frame_size = 510;
	javax.swing.Timer timer = new javax.swing.Timer(500, null);  //Timer

	private boolean gameover = false;

	public static void main(String[] args) {
	new dodger();	
	}

	public dodger()
	{
		super("Dodger");
		addKeyListener(this);
		setSize(720, 620);
		getContentPane().setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		play();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(!gameover)
		{
			g.setColor(Color.white);
			g.drawRect(50, 50, frame_size+110, frame_size);
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			g.drawString("Player: " + player, 50, 580);
			g.drawString("Score: " + score, 550, 580);
			g.setColor(Color.gray);
			for(int i=front; i != rear; i = (i+1)%50 )
				g.fillRect(rock_x[i], rock_y[i], rock_size, rock_size);
			g.setColor(Color.cyan);	
			g.fillOval(player_x, player_y, 20, 20);	
		}
		else
		{
			g.setColor(Color.blue);
			g.setFont(new Font("Arial", Font.BOLD, 35));
			g.drawString("Game Over!!", 250, 300);
			g.drawString("Your Score is: "+ score, 220, 340);
		}
		
	}

	public void check()									//Occurs at movement and rocks moving down to check if player and rocks collide
	{
		for(int i=front; i!=rear; i = (i+1)%50)
		{
			if(player_y != rock_y[i])
				continue;
			else
			{
				if(Math.abs(player_x - rock_x[i]) <20)
				{
					gameover = true; 
					timer.stop();
				}

			}
		}
	}

	public void play()
	{
		timer.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)  //Swing Timer object to perform rocks falling every 1/2 second
			{
				Random rand = new Random();
				int x = rand.nextInt(57)*10 + 60;
				int y = 60;
				int z = rand.nextInt(100) + 1;					//Extra probability variable, 60% chance of two rocks
				for(int i=front; i!=rear; i = (i+1)%50 )
				{	
					rock_y[i] += 40;
					if(rock_y[i] >= frame_size + frame_top){
						front = (front +1)%50;
						score++;
					}
				}
				rock_x[rear] = x;
				rock_y[rear] = y;
				rear = (rear+1)%50;
				if(z > 40)					
				{
					int x1;
					x1 = rand.nextInt(57)*10 + 60;
					while(Math.abs(x - x1) < 20)			//So that the two rocks dont overlap
					{
						x1 = rand.nextInt(57)*10 + 60;
					}
					rock_x[rear] = x1;
					rock_y[rear] = y;
					rear = (rear+1)%50;
				}
				check();				
				repaint();
			}
		}
		);
		timer.start();
	}

	public void keyTyped(KeyEvent e) { 
    }
	public void keyPressed(KeyEvent e) {        
    	if(e.getKeyCode() == KeyEvent.VK_RIGHT){					//Only left and right movement possible
    		if(player_x<frame_size+130){
				player_x += 20;
			}
		}
    	else if (e.getKeyCode() == KeyEvent.VK_LEFT)
    		if(player_x>frame_left){
				player_x -= 20;
			}
    	check();
    	repaint();
    }
	public void keyReleased(KeyEvent e) {
    }
}
