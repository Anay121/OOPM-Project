import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.*;
//import java.lang.*;

public class Games extends JFrame implements ActionListener
{
	JLabel l = new JLabel("Please enter your name:");
	JLabel l1 = new JLabel("Which game do you wish to play?");
	JTextField t = new JTextField("Player", 20);
	JTextField t1 = new JTextField("Player2", 20);
	JButton b = new JButton("Dive in!");
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();
	ButtonGroup g = new ButtonGroup();
	JRadioButton j1,j2,j3;

	public static void main(String[] args) {
		new Games();
	}
	public Games()
	{
		b.addActionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300 ); //size of frame

		t.setMaximumSize(t.getPreferredSize());

		t1.setMaximumSize(t1.getPreferredSize());

		j1 = new JRadioButton("Snakes");
		j2 = new JRadioButton("Reversi");
		j3 = new JRadioButton("Asteroid Dodger");
		j1.addActionListener(this);
		j2.addActionListener(this);
		j3.addActionListener(this);

		g.add(j1);
		g.add(j2);
		g.add(j3);

		getContentPane().setBackground(Color.cyan); 
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		setLocationRelativeTo(null);
		setVisible(true);
		p.setVisible(true);
		p1.setVisible(true);
		
		p.add(l1);
		p.add(j1);
		p.add(j2);
		p.add(j3);
		
		p.add(l);
		p1.add(t);
		p1.add(t1);
		p1.add(b);
		p1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p.add(p1);

		getContentPane().add(p);
		t1.setVisible(false);
	}
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == j2)
		{
			t.setText("Player1");
			t1.setVisible(true);
			revalidate();
		}
		else if(evt.getSource() == j1 || evt.getSource() == j3){
			t.setText("Player");
			t1.setVisible(false);
		}
		else if(evt.getSource() == b )
		{
			String game_name, player1, player2;
			if(j1.isSelected())
			{
				game_name = new String("Snakes");
				player1 = t.getText().toString();
				if(!player1.equals(""))
					new Snakes(player1);
			}
			else if(j2.isSelected())
			{
				game_name = new String("Reversi");
				player1 = t.getText().toString();
				player2 = t1.getText().toString();
				if(!player1.equals("") && !player2.equals(""))
					new Reversi(player1, player2);
			}
			else if(j3.isSelected())
			{
				game_name = new String("Dodger");
				player1 = t.getText().toString();
				if(!player1.equals(""))
					new Dodger(player1);
			}

		}

	}
}



//Snake
class Snakes extends JFrame implements KeyListener,Runnable{  
	
	Thread t=null;
	String name;
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
		//new Snakes();
	}
	
	public Snakes(String name){
        super("Snakes Game ");
		t=new Thread(this);
		t.start();
       	addKeyListener(this);
		getContentPane().setBackground(Color.BLACK);//set background
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(330, 360);//size of frame
        setVisible(true);
		this.name=name;
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
		//System.out.println("Food: "+food_x+" "+food_y);
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
			g.drawString("YOUR SCORE: "+score,45,220);
			g.setFont(new Font("Arial", Font.PLAIN, 17));
			g.drawString("Press any key to exit", 90, 270);

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
				if(size%3==0 && TIME_DELAY>100){//decrease sleep time as size increases
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
				//System.out.println("Snake: x:"+snake_x[0]+" y:"+snake_y[0]);
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
        int keyCode = e.getKeyCode();
        //int flag = 0;
        //System.out.println(keyCode);
        if (keyCode == 37) // Left arrow key
        {
            LEFT = true;
            RIGHT = UP = DOWN = false;
        }
        if (keyCode == 39) // Right arrow key
        {
            RIGHT = true;
            LEFT = UP = DOWN = false;
        }
        if (keyCode == 40) // Down arrow key
        {
            DOWN = true;
            UP = LEFT = RIGHT = false;
        }
        if (keyCode == 38) // Up arrow key
        {
            UP = true;
            DOWN = LEFT = RIGHT = false;
        }
        if(gameover)		//Close window
        	dispose();
    }
	public void keyReleased(KeyEvent e) {
    }
}

//Reversi
class Reversi extends JFrame implements MouseListener
{
	String player1 = "Player1";
	String player2 = "Player2";
	int box_size = 50;
	int boxleft = 30;	//Game board leftmost start point
	int boxtop = 70;	// GAme board top starting point

	int board[][] = new int[8][8]; 	//board position info
	int clicked_x;		
	int clicked_y;
	int countB = 2, countW = 2;
	int turn = 0;
	int winner=-1;	//Whos the winner

	int enemyX[] = {1, -1, 1, -1, 0, 0, 1, -1};
	int enemyY[] = {1, 1, -1, -1, 1, -1, 0, 0};
	private boolean gameover = false;

	public static void main(String[] args) {
		//new Reversi();
	}
	public Reversi(String pl1, String pl2)
	{
		super("Reversi");
		addMouseListener(this);
		setSize(550, 600);
		getContentPane().setBackground(new Color(20, 133, 56));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		player1 = pl1;
		player2 = pl2;
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				board[i][j] = -1;
		board[3][3] = board[4][4] = 0;
		board[3][4] = board[4][3] = 1;
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		//Place gameover conditions
		Font f1 = new Font("Arial", Font.PLAIN, 17);
		g.setColor(Color.white);
		g.setFont(f1);
		if(!gameover)
		{
			g.drawString("Player1: " + player1, 30, 525);
			g.drawString("Pieces: " + countW, 30, 560);
			g.drawString("Player2: " + player2, 300, 525);
			g.drawString("Pieces: " + countB, 300, 560);
			if(turn % 2 == 0)
			{
				g.setColor(Color.white);
				g.drawRect(20, 505, 200, 30);
			}
			else
			{
				g.setColor(Color.black);
				g.drawRect(250, 505, 200, 30);
			}
			for(int i=0; i<8; i++)
				for(int j=0; j<8; j++)
				{
					g.setColor(Color.white);
					g.drawRect(boxleft+box_size*i, boxtop+box_size*j, box_size, box_size);
					//g.drawString(board[i][j] + "", boxleft + box_size*i + 15, boxtop + box_size*j +15);
					if(board[i][j] == 0)
					{
						g.setColor(Color.white);
						g.fillOval(boxleft+box_size*i + 5, boxtop+box_size*j + 5, box_size - 10, box_size - 10);
					}
					else if(board[i][j] == 1)
					{
						g.setColor(Color.black);
						g.fillOval(boxleft+box_size*i + 5, boxtop+box_size*j + 5, box_size - 10, box_size - 10);
					}
				}
		}
		else
		{
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("Game Over!!", 200, 200);
			if(winner ==1)
				g.drawString("The winner is: " + player1, 150, 250);
			if(winner ==2)
				g.drawString("The winner is: " + player2, 150, 250);
			if(winner ==3)
				g.drawString("Its a Draw!! ", 210, 250);
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			g.drawString("Click anywhere to exit", 190, 300);
		}
	}

	public boolean validClick(int x, int y)
	{
		if(x < 8 && y<8 && x>=0 && y>=0 && board[x][y] == -1)
			return true;
		else 
			return false;
	}
	public void check()
	{
		countB = 0;
		countW = 0;
		// If any one player runs out of pieces, then its game over for them 
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				if(board[i][j] == 1)
					countB++;
				else if(board[i][j] == 0)
					countW++;
		//System.out.println(countB + " " + countW );
		if(countW == 0 || (countB + countW == 64 && countB > countW))  //Black wins 
			winner = 2;
		else if(countB == 0 || (countB + countW == 64 && countW > countB))  //White wins
			winner = 1;
		else if(countB + countW == 64 && countW == countB)  //Draw
			winner = 3; 
		else 
			winner = -1;
	}

	public int convert(int x, int y)
	{
		int count = 0, convertTo;
		boolean yes = false;  //Should it convert the line's enemy to my pieces
		convertTo = turn % 2;
		//System.out.println(convertTo);
		for(int k=0; k<8; k++)
		{
			//One loop ot determine whether conversion has to take place, one to convert
			for(int i=x+enemyX[k], j=y+enemyY[k]; i<8 && i>=0 && j<8 &&j>=0 && board[i][j] !=-1; i+=enemyX[k], j+=enemyY[k])
			{
				if(board[i][j] == convertTo)
				{	
					yes = true;
					break;
				}
			}
			if(yes)
			{
				for(int i=x+enemyX[k], j=y+enemyY[k]; i<8 && i>=0 && j<8 &&j>=0 && board[i][j] != convertTo; i+=enemyX[k], j+=enemyY[k])
				{
					board[i][j] = convertTo;
					count++;
				}
				yes = false;
			}
		}
		return count;
	}


	public void mouseReleased(MouseEvent e)			// ABStract methods have ot be overridden
	{

	}
	public void mouseClicked(MouseEvent e)
	{
		int x, y;
		x = e.getX();
		y = e.getY();
		if(gameover)	//close window
			dispose();
		//Boundary conditions
		clicked_x = (x - boxleft)/box_size;
		clicked_y = (y - boxtop)/box_size;
		if(validClick(clicked_x, clicked_y))
		{	
			if(convert(clicked_x, clicked_y) != 0)  //Only if at least one thing is being changed then its a valid move else nothing 
			{
				board[clicked_x][clicked_y] = turn % 2;
				
				check();
				turn++;
				if(winner !=-1)
					gameover = true;
				repaint();
			}

		}
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
}



//Asteroid Dodger
class Dodger extends JFrame implements KeyListener
{
	
	String player ;
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
	//new dodger();	
	}

	public Dodger(String name)
	{
		super("Dodger");
		addKeyListener(this);
		setSize(720, 620);
		getContentPane().setBackground(Color.black);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		player=name;
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
			g.drawString("Game Over!!", 250, 280);
			g.drawString("Your Score is "+ score, 220, 340);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Press any key to exit", 265, 380);
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
		if(gameover)		//close window
			dispose();   
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
