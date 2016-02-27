package me.geetock.game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

import me.geetock.game.gamestate.GameState;
import me.geetock.game.main.GamePanel;
import me.geetock.game.objects.Block;
import me.geetock.game.physics.Collision;

public class Player {

	// movement booleans
	private boolean left = false;
	private boolean right = false;
	private boolean jumping = false;
	private boolean falling = false;
	private boolean topCollision = false;
	
	// player bounds
	private double x, y;
	private int width, height;
	
	// player move speed
	private double moveSpeed = 1.5;
	
	// jump speed
	private double jumpSpeed = 5;
	private double currentJumpSpeed = jumpSpeed;
	
	// fall speed
	private double maxFallSpeed = 5;
	private double currentFallSpeed = 0.1;

	// CONSTRUCTOR
	public Player(int width, int height) {
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		this.width = width;
		this.height = height;
	}
	
	public void tick(Block[][] b) {
		
		int iX = (int) x;
		int iY = (int) y;
		
		// Collision detection
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				
				if (b[i][j].getID() != 0) {
					// top right corner || bottom right corner
					if (Collision.playerBlock(new Point(iX + width + (int) GameState.xOffset, iY + (int) GameState.yOffset + 2), b[i][j]) 
							|| Collision.playerBlock(new Point(iX + width + (int) GameState.xOffset, iY + height + (int) GameState.yOffset - 1), b[i][j])) {
						right = false;
					}
					
					// left top corner || left bottom corner
					if (Collision.playerBlock(new Point(iX + (int) GameState.xOffset - 1, iY + (int) GameState.yOffset + 2), b[i][j]) 
							|| Collision.playerBlock(new Point(iX + (int) GameState.xOffset - 1, iY + height + (int) GameState.yOffset - 1), b[i][j])) {
						left = false;
					}
					
					// top left corner || top right corner
					if (Collision.playerBlock(new Point(iX + (int) GameState.xOffset + 1, iY + (int) GameState.yOffset), b[i][j]) 
							|| Collision.playerBlock(new Point(iX + width + (int) GameState.xOffset - 2, iY + (int) GameState.yOffset), b[i][j])) {
						jumping = false;
						falling = true;
					}
					
					// bottom left corner || bottom right corner
					if (Collision.playerBlock(new Point(iX + (int) GameState.xOffset + 2, iY + height + (int) GameState.yOffset + 1), b[i][j])
							|| Collision.playerBlock(new Point(iX + width + (int) GameState.xOffset - 2, iY + height + (int) GameState.yOffset + 1), b[i][j])) {
						y = b[i][j].getY() - height - GameState.yOffset;
						falling = false;
						topCollision = true;
					} else {
						if (!topCollision && !jumping) {
							falling = true;
						}
					}			
				}
			}
		}
		
		topCollision = false;
		
		if (right) {
			GameState.xOffset += moveSpeed;
		}
		
		if (left) {
			GameState.xOffset -= moveSpeed;
		}
		
		if (jumping) {
			GameState.yOffset -= currentJumpSpeed;
			
			currentJumpSpeed -= 0.1;
			
			if (currentJumpSpeed <= 0) {
				currentJumpSpeed = jumpSpeed;
				jumping = false;
				falling = true;
			}
		}
		
		if (falling) {
			GameState.yOffset += currentFallSpeed;
			
			if (currentFallSpeed < maxFallSpeed) {
				currentFallSpeed += 0.1;
			}
		}
		
		if (!falling) {
			currentFallSpeed = 0.1;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, width, height);
		
	}
	
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_D) right = true;
		if (k == KeyEvent.VK_A) left = true;
		if (k == KeyEvent.VK_SPACE && !jumping && !falling) jumping = true;
	}
	
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_D) right = false;
		if (k == KeyEvent.VK_A) left = false;
	}

}
