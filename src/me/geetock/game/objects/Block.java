package me.geetock.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.geetock.game.gamestate.GameState;

public class Block extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	public static final int BLOCKSIZE = 64;
	private int id;

	public Block(int x, int y, int id) {
		setBounds(x, y, BLOCKSIZE, BLOCKSIZE);
		this.id = id;
	}

	public void tick() {

	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		if (id != 0) {
			g.fillRect(x - (int) GameState.xOffset, y - (int) GameState.yOffset, width, height);
		}
	}
	
	// getters and setters
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
