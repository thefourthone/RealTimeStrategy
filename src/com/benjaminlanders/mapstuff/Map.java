package com.benjaminlanders.mapstuff;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Map implements Serializable{
	private static final long serialVersionUID = 8538735059537356340L;
	private double UID = 0;
	public Network network = new Network();
	public String name = "map.png", colname = "";
	transient public Image map;
	transient public BufferedImage collision;
	public Map(){
		map = new ImageIcon(name).getImage();
	}
	public void restoreImage(){
		map = new ImageIcon(name).getImage();
		File temp= new File(colname);
		try {
			collision = ImageIO.read(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public double getUID() {
		return UID;
	}
	public void setUID(double uID) {
		UID = uID;
	}
}
