package dev.codenmore.tilegame.states;

import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.entities.creatures.Player;
import dev.codenmore.tilegame.worlds.World;

public class GameState extends State
{
	private Player player;
	private World world;

	public GameState(Handler handler)
	{
		//calls constructor of whatever class u extend up top
		super(handler);
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);
		player = new Player(handler, 100, 100);

		//handler.getGameCamera().move(100, 200);
	}

	@Override
	public void tick()
	{
		world.tick();
		player.tick();
	}

	@Override
	public void render(Graphics g)
	{
		world.render(g);
		player.render(g);
		//g.drawImage(Assets.dirt, 0, 0, null);
		//Tile.tiles[0].render(g, 0, 0);
	}




}
