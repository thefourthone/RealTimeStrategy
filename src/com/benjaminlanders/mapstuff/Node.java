package com.benjaminlanders.mapstuff;

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable{
	private static final long serialVersionUID = -5827341829760708563L;
	private int x,y ;//ID;
	private ArrayList<Node> neighbors= new ArrayList<Node>();
	public Node(int x, int y){
		this.x=x;
		this.y=y;
	}
	public ArrayList<Node> getNeighbors(){
		return neighbors;
	}
	public void addNeighbor(Node neighbor){
		neighbors.add(neighbor);
	}
	public void removeNeighbor(Node removal){
		neighbors.remove(removal);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
