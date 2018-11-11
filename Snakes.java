import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.lang.*;


//import javax.swing.JOptionPane;

class Snakes extends JFrame implements KeyListener,Runnable{  
	
	Thread t=null;
	String name="Player";
	int food_x;//coordinates of food
	int food_y;
	int snake_x[]=new int[900];//coordinates of every part of snake
	int snake_y[]=new int[900];
	int size;//size of snake
	static final int dim=10;
	private boolean UP=false;//direction
	private boolean DOWN=false;
	private boolean LEFT=false;
	private boolean RIGHT=true;
	static final int max_y=330;//size of frame
	static final int max_x=310;
	static final int min_y=30;
	static final int min_x=10;
	static int TIME_DELAY=300;
	private boolean gameover=false;
	static int score=0;
	Random r=new Random();
	
	public static void main(String args[]){//main function
		new Snakes();
	}
	
	public Snakes(){
		/*timer = new javax.swing.Timer(TIME_DELAY, new TimerListener());
        	timer.start();*/
        	super("Snakes Game ");
		t=new Thread(this);
		t.start();
       		addKeyListener(this);
		getContentPane().setBackground(Color.BLACK);//set background
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	setSize(330, 360);//size of frame

        setVisible(true);
		initGame();
	}
	
	public void initGame(){//start the game with snake of size 3
		size=3;
		for(int i=0;i<size;i++){
			snake_x[i]=(100-dim*i);
			snake_y[i]=(100);
		}
		locateFood();
	}
	public void locateFood(){//generate food at random positions 
		food_x=((r.nextInt(290))*10);
		food_x=((Math.abs(food_x))%290)+20;
		food_y=((r.nextInt(290))*10);
		food_y=((Math.abs(food_y))%290)+40;
		System.out.println("Food: "+food_x+" "+food_y);
	}
	
	public void drawSnake(Graphics g){
		g.setColor(Color.orange);
		g.fillOval(snake_x[0], snake_y[0], dim, dim);
		g.setColor(Color.white);
		for(int j=1;j<size;j++){
			g.fillOval(snake_x[j],snake_y[j],dim,dim);
		}
	}
	public void drawFood(Graphics g){
		g.setColor(Color.red);
		g.fillRect(food_x,food_y,10,10);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
        	g.drawRect(min_x+10, min_y+10, 290, 290);
        	if(gameover){

			Font f1 = new Font("Arial",Font.BOLD,30);  
			g.setFont(f1);
			g.setColor(Color.blue);
			t.stop();
			g.drawString("GAME OVER",75,170);
			g.drawString("YOUR SCORE: "+score,25,220);

		}
		else{
			g.drawString("Player: "+name,30,345);
			g.drawString("Score: "+score,250,345);
			drawSnake(g);
			drawFood(g);
		}
    }
//snake_x[0],snake_y[0] are coordinates of head of snake 
	public void Check(){//check for game over or snake has eaten food
		if(snake_x[0]==food_x && snake_y[0]==food_y){
			size++;
			score+=5;
			locateFood();
		}
		if(snake_x[0]>=max_x || snake_x[0]<=min_x || snake_y[0]>=max_y || snake_y[0]<=min_y || score>=4500){
			gameover=true;
		}
		for(int i=1;i<size;i++){
			if(snake_x[0]==snake_x[i] && snake_y[0]==snake_y[i]){
				gameover=true;
				break;
			}
		}
		
	}	
	public void run(){//threading
		while(true){
			try{
				Thread.sleep(TIME_DELAY);
				if(size%3==0 && TIME_DELAY>=100){//decrease sleep time as size increases
					TIME_DELAY--;
				}
				for(int i=size-1;i>0;i--){//shift coordinates one position to the front ie snake[1]=snake[0] and so on
					snake_x[i]=snake_x[i-1];
					snake_y[i]=snake_y[i-1];
				}
				if(UP){
					snake_y[0]-=dim;
				}
				else if(DOWN){
					snake_y[0]+=dim;
				}
				else if(LEFT){
					snake_x[0]-=dim;
				}
				else if(RIGHT){
					snake_x[0]+=dim;
				}
				System.out.println("Snake: x:"+snake_x[0]+" y:"+snake_y[0]);
				Check();
				repaint();
			}
			catch(Exception e){
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {//check key pressed
    }
	public void keyPressed(KeyEvent e) {
        //displayInfo(e, "KEY PRESSED: ");
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        if (keyCode == KeyEvent.VK_LEFT) // Left arrow key
        {
            LEFT = true;
            RIGHT = UP = DOWN = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT) // Right arrow key
        {
            RIGHT = true;
            LEFT = UP = DOWN = false;
        }
        if (keyCode == KeyEvent.VK_DOWN) // Down arrow key
        {
            DOWN = true;
            UP = LEFT = RIGHT = false;
        }
        if (keyCode == KeyEvent.VK_UP) // Up arrow key
        {
            UP = true;
            DOWN = LEFT = RIGHT = false;
        }
    }
	public void keyReleased(KeyEvent e) {
    }
}
