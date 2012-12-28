package com.benjaminlanders.mapstuff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Network implements Serializable{
	private static final long serialVersionUID = 9102325679061554041L;
	public ArrayList<Node> nodes = new ArrayList<Node>();
	public ArrayList<Connection> connections = new ArrayList<Connection>();
	public Node findClosest(int x, int y){
		if(!nodes.isEmpty()){
			int bestscore = 50000;
			int temp2 = 0;
			Node best = null;
			for(Node temp: nodes){
				temp2 = (int) (Math.pow((temp.getX()-x),2)+Math.pow((temp.getY()-y),2));
				if(temp2 < bestscore){
					bestscore = temp2;
					best = temp;
				}
			}
			return best;
		}
		return null;
		
	}
	public void deleteNode(Node node){
		List<Connection> remove = new ArrayList<Connection>();
		for(Connection connector: connections){
			if(connector.getOne() == node|| connector.getTwo() == node){
				connector.deleteThis();
				remove.add(connector);
			}
		}
		connections.removeAll(remove);
		nodes.remove(node);
	}

}
