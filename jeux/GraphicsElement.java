package jeux;

import javax.microedition.lcdui.Graphics;

public abstract class GraphicsElement {

	protected int x;
	protected int y;

	public GraphicsElement(){}

	public GraphicsElement(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public abstract void paint(Graphics g);
}