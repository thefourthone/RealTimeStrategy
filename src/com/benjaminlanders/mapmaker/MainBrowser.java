package com.benjaminlanders.mapmaker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import com.benjaminlanders.mapstuff.Connection;
import com.benjaminlanders.mapstuff.Map;
import com.benjaminlanders.mapstuff.Node;

/**
 * The main mapmaker launcher 
 * @author Benjamin Landers
 *
 */
public class MainBrowser extends JPanel implements MouseListener, KeyListener, MouseMotionListener
{
	private static final long serialVersionUID = -50573324983488103L;
	JFrame main, saveWindow;
	JTextField input;
	char key=0;
	Node selection;
	int x=0,y=0;
	String DefaultName="save";
	//Network network = new Network();
	Map map= new Map();
	
	Node first, second;
	/**
	 * calls setup frame
	 * @param Args
	 */
	public static void main(String Args[])
	{
		new MainBrowser().setUpFrame();
		
	}
	/**
	 * The method called to set up the gui
	 */
	public void setUpFrame()
	{
		main = new JFrame();
		//main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getContentPane().add(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		main.addKeyListener(this);
		main.setSize(700, 500);
		main.setVisible(true);
		saveWindow = new JFrame();
		saveWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		input = new JTextField(20);
		saveWindow.add(BorderLayout.CENTER, input);
		/*JButton save = new JButton("Save");
		save.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent paramActionEvent) {
				startSave();
		}}
		);
		saveWindow.add(BorderLayout.EAST, save);*/
		saveWindow.setSize(400, 75);
		saveWindow.setVisible(true);
	}
	
	/**
	 * 
	 */
	public void startSave()
	{
		String name = input.getText()+".map";
		saveMap(name, map);
	}
	public void saveMap(String name, Map map)
	{
		
		
		try {
			FileOutputStream filestream = new FileOutputStream(name);
			ObjectOutputStream os = new ObjectOutputStream(filestream);
			map.setUID(Math.random());
			os.writeObject(map);
			os.close();
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
		
	}
	public void Loadmap()
	{
		String name = input.getText()+".map";
		Map temp = null;
		try 
		{
			FileInputStream filestream = new FileInputStream(name);
			ObjectInputStream is = new ObjectInputStream(filestream);
			temp = (Map) is.readObject();
			temp.restoreImage();
			is.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(temp != null)
		{
		map = temp;
		}
		
	}
	public void paintComponent(Graphics g)
	{
		g.drawImage(map.map,-x, -y,2000, 1000, this);
		drawNodes(map.network.nodes, g);
		if(selection!=null){
		g.setColor(Color.RED);
		g.fillOval(selection.getX()-4-x, selection.getY()-y-4,8, 8);}
		drawConnections(map.network.connections, g);
		g.setColor(Color.BLACK);
		g.drawImage(map.map, main.getWidth()-317, main.getHeight()-188, 300,150, this);
		g.setColor(Color.BLUE);
		for(Node temp: map.network.nodes)
		{
			g.fillOval(main.getWidth()-317+3*temp.getX()/20-2, main.getHeight()-188+3*temp.getY()/20-2, 5, 5);
		}
		g.setColor(Color.GREEN);
		for(Connection temp: map.network.connections)
		{
			g.drawLine(main.getWidth()-317+3*temp.getOne().getX()/20, main.getHeight()-188+3*temp.getOne().getY()/20,
					main.getWidth()-317+3*temp.getTwo().getX()/20, main.getHeight()-188+3*temp.getTwo().getY()/20);
		}
		g.setColor(Color.BLACK);
		g.drawRect( this.getWidth()-300+3*x/20, this.getHeight()-150+3*y/20, 3*this.getWidth()/20, 3*this.getHeight()/20);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("# of nodes: " + map.network.nodes.size(),20,20);
		g.drawString("# of connections: " + map.network.connections.size(),20,40);
		
	}
	public void drawNodes(ArrayList<Node> nodes, Graphics G)
	{
		for(Node temp: nodes){
			G.setColor(Color.BLUE);
			G.fillOval(temp.getX()-5-x, temp.getY()-5-y, 10, 10);
		}

	}
	public void drawConnections(ArrayList<Connection> connections, Graphics g)
	{
		for(Connection temp: connections)
		{
			g.setColor(Color.GREEN);
			g.drawLine(temp.getOne().getX()-x, temp.getOne().getY()-y, temp.getTwo().getX()-x,  temp.getTwo().getY()-y);
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent)
	{
		
	}
	public void mousePressed(MouseEvent down)
	{
		selection = map.network.findClosest(down.getX()+x, down.getY()+y);
		if(key != ' ')
		{
			switch (key)
			{
				case 'n':
					map.network.nodes.add(new Node(down.getX()+x,down.getY()+y));
					break;
				case 'd':
					map.network.deleteNode(selection);
					selection = null;
					break;
				case 'c':
					if(first == null)
					{
					first = selection;
					}else
					{
					if(second==null)
					{
					second = selection;
					if(first != second&&!first.getNeighbors().contains(second))
					{
					map.network.connections.add(new Connection(first,second));
					}
					first = null;
					second = null;
					}
					}
					break;
				case 'f':
					System.out.println(selection.getX()+" "+ selection.getY());
					break;
				case 's':
					startSave();
					break;
				case 'l':
					Loadmap();
					break;
				default:
					break;
			}
		}
		main.repaint();
	}

	public void mouseReleased(MouseEvent paramMouseEvent)
	{
	}
	public void mouseEntered(MouseEvent paramMouseEvent)
	{
		
	}
	public void mouseExited(MouseEvent paramMouseEvent)
	{
		
	}
	public void keyTyped(KeyEvent paramKeyEvent)
	{
		key = paramKeyEvent.getKeyChar();
		main.repaint();
	}
	public void keyPressed(KeyEvent paramKeyEvent)
	{
	}
	public void keyReleased(KeyEvent paramKeyEvent)
	{
		key = ' ';
		main.repaint();
	}
	public void mouseDragged(MouseEvent arg)
	{
		
		if(arg.getX()>this.getWidth()-300&& arg.getY()>this.getHeight()-150)
		{
			x = (arg.getX()-this.getWidth()+300)*20/3 -300;
			y = (arg.getY()-this.getHeight()+150)*20/3 -150;
		}else
		{
			if(key == ' ')
			{
				selection.setX(arg.getX()+x);
				selection.setY(arg.getY()+y);
			}
		}
		
		main.repaint();
	}
	public void mouseMoved(MouseEvent paramMouseEvent) 
	{
		
	}
}
