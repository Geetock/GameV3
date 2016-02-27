package me.geetock.game.physics;

import java.awt.Point;
import me.geetock.game.objects.Block;

public class Collision {

	public static boolean playerBlock(Point p, Block b) {
		return b.contains(p);
	}

}
