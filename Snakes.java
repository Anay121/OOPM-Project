import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.lang.*;


import javax.swing.JOptionPane;

class Snakes extends JFrame implements KeyListener,Runnable{  
	
	Thread t=null;
	int food_x;
	int food_y;
	int snake_x[]=new int[900];
	int snake_y[]=new int[900];
	int size;
	static final int dim=10;
	private boolean UP=false;
	private boolean DOWN=false;
	private boolean LEFT=false;
	private boolean RIGHT=true;
	static final int max=300;
	static final int min=0;
	static int TIME_DELAY=300;
	private boolean gameover=false;
	static int score=0;
	Random r=new Random();
	
	public Snakes(){
		/*timer = new javax.swing.Timer(TIME_DELAY, new TimerListener());
        timer.start();*/
		t=new Thread(this);
		t.start();
        addKeyListener(this);
		getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(max+20 , max+20 );
        setVisible(true);
		initGame();
	}
	
	public void initGame(){
		size=3;
		for(int i=0;i<size;i++){
			snake_x[i]=(50-dim*i);
			snake_y[i]=(50);
		}
		locateFood();
	}
	public void locateFood(){
		food_x=((r.nextInt(290))*10);
		food_x=(Math.abs(food_x))%290;
		food_y=((r.nextInt(290))*10);
		food_y=(Math.abs(food_y))%290;
		System.out.println("Food: "+food_x+" "+food_y);
	}
	
	public void drawSnake(Graphics g){
		g.setColor(Color.white);
		for(int j=0;j<size;j++){
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
        g.drawRect(5, 5, max+5, max+5);
        if(gameover){
			Font f1 = new Font("Arial",Font.BOLD,30);  
			g.setFont(f1);
			g.setColor(Color.blue);
			t.stop();
			g.drawString("GAME OVER",50,100);
			g.drawString("YOUR SCORE: "+score,35,140);
		}
		else{
			drawSnake(g);
			drawFood(g);
		}
    }

	public void Check(){
		if(snake_x[0]==food_x && snake_y[0]==food_y){
			size++;
			score+=5;
			locateFood();
		}
		if(snake_x[0]>=max || snake_x[0]<=min || snake_y[0]>=max || snake_y[0]<=min || score>=4500){
			gameover=true;
		}
	}	
	public void run(){
		for(int k=0;;k++){
			try{
				Thread.sleep(TIME_DELAY);
				if(size%3==0){
					TIME_DELAY--;
				}
				for(int i=size-1;i>0;i--){
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
	
	public void keyTyped(KeyEvent e) {
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
        if (keyCode == KeyEvent.VK_RIGHT) // Left arrow key
        {
            RIGHT = true;
            LEFT = UP = DOWN = false;
        }
        if (keyCode == KeyEvent.VK_DOWN) // Left arrow key
        {
            DOWN = true;
            UP = LEFT = RIGHT = false;
        }
        if (keyCode == KeyEvent.VK_UP) // Left arrow key
        {
            UP = true;
            DOWN = LEFT = RIGHT = false;
        }
		//move();
        //repaint();
    }
	public void keyReleased(KeyEvent e) {
    }
	
	public static void main(String args[]){
		new Snakes();
	}
}