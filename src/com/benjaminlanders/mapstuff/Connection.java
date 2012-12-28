package com.benjaminlanders.mapstuff;

import java.io.Serializable;

public class Connection implements Serializable{
	private static final long serialVersionUID = 8150739306460457041L;
	private Node one, two;
	public Connection(Node one, Node two){
		this.two = two;
		this.one = one;
		this.one.addNeighbor(this.two);
		this.two.addNeighbor(this.one);
	}
	public void deleteThis(){
		one.removeNeighbor(two);
		two.removeNeighbor(one);
	}
	public Node getOne() {
		return one;
	}
	public void setOne(Node one) {
		this.one.removeNeighbor(this.two);
		this.two.removeNeighbor(this.one);
		this.one = one;
		this.one.addNeighbor(two);
		this.two.addNeighbor(this.one);
	}
	public Node getTwo() {
		return two;
	}
	public void setTwo(Node two) {
		this.one.removeNeighbor(this.two);
		this.two.removeNeighbor(this.one);
		this.two = two;
		this.two.addNeighbor(one);
		this.one.addNeighbor(this.two);
	}
}
