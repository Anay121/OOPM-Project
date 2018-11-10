import javax.swing.*;
import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;

public class reversi extends JFrame implements MouseListener
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
		new reversi();
	}
	public reversi()
	{
		super("Reversi");
		addMouseListener(this);
		setSize(600, 600);
		getContentPane().setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
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
		//String thingy;
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
				g.setColor(Color.green);
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
						g.setColor(Color.green);
						g.fillOval(boxleft+box_size*i + 5, boxtop+box_size*j + 5, box_size - 10, box_size - 10);
					}
				}
		}
		else
		{
			g.drawString("Game Over!!", 100, 100);
			if(winner ==1)
				g.drawString("The winner is: " + player1, 100, 150);
			if(winner ==2)
				g.drawString("The winner is: " + player2, 100, 150);
			if(winner ==3)
				g.drawString("Its a Draw!! ", 100, 150);
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
		System.out.println(countB + " " + countW );
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
		System.out.println(convertTo);
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