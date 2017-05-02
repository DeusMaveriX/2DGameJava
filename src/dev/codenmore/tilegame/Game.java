package dev.codenmore.tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.codenmore.tilegame.display.Display;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.GameCamera;
import dev.codenmore.tilegame.input.KeyManager;
import dev.codenmore.tilegame.states.GameState;
import dev.codenmore.tilegame.states.MenuState;
import dev.codenmore.tilegame.states.State;

//////////////////////////////////////////////

public class Game implements Runnable
{
		private Display display;
		private int width, height;
		public String title;

		private boolean running = false;
		private Thread thread;

		//bs = buffer strategy. buffer is hidden pc screen on ur pc screen
		private BufferStrategy bs;

		//g=graphics object
		private Graphics g;

		//private BufferedImage testImage;

////////////////////////////////**************

		//States
		private State gameState;
		private State menuState;

////////////////////////////////**************

		//private BufferedImage test;
		//private SpriteSheet sheet;

/////////////////////////////////**************

		//Input
		private KeyManager keyManager;

		//Camera
		private GameCamera gameCamera;

		//Handler
		private Handler handler;

		public Game(String title, int width, int height)
		{
			this.width = width;
			this.height = height;
			this.title = title;
			keyManager = new KeyManager();
		}

		//init=initializing game states
		private void init()
		{
			display = new Display(title, width, height);
			display.getFrame().addKeyListener(keyManager);

			//test = ImageLoader.loadImage("/textures/sheet.png");
			//sheet = new SpriteSheet(test);
			Assets.init();

			gameCamera = new GameCamera(this, 0, 0);
			handler = new Handler(this);

			//passing game object into the current state
			gameState = new GameState(handler);
			menuState = new MenuState(handler);
			State.setState(gameState);
		}

		private void tick()
		{
			keyManager.tick();

			if(State.getState() != null)
			{
				State.getState().tick();
			}
		}

		//int x = 0;
		//x += 1;

/////////////////////////////////////

		//render means drawing something to the screen
		private void render()
		{
			bs = display.getCanvas().getBufferStrategy();
			if(bs == null)
			{
				display.getCanvas().createBufferStrategy(3);
				return;
			}
			//g = graphics object
			g = bs.getDrawGraphics();

			//clear SCREEN
			g.clearRect(0, 0, width, height);

////////////////////*****************************////////////////////////////////////////
////////////////////*****************************////////////////////////////////////////
////////////////////*****************************////////////////////////////////////////

			//START DRAWING

			if(State.getState() != null)
			{
				State.getState().render(g);
			}

			//g.drawImage(Assets.grass, x, 10, null);

			//g.drawImage(sheet.crop(32, 0, 32, 32), 5, 5, null);

			//g.drawImage(test, 0, 0, null);

			//null = image observer
			//g.drawImage(testImage, 20, 20, null);

/*			g.fillRect(0, 0, width, height);
  			g.setColor(Color.red);
			g.fillRect(10, 50, 50, 70);

			g.setColor(Color.green);
			g.fillRect(0, 0, 10, 10);
*/

			//END DRAWING
			bs.show();
			g.dispose();
		}

////////////////////*****************************////////////////////////////////////////
////////////////////*****************************////////////////////////////////////////
////////////////////*****************************////////////////////////////////////////

		public void run()
		{
			init();

			//amount of times we call tick and render
			int fps = 60;
			//amount of time to execute
			double timePerTick = 1000000000 / fps;
			//amount of time before we get tick&render method
			double delta = 0;
			//now = current time of pc
			long now;
			long lastTime = System.nanoTime();
			long timer = 0;
			int ticks = 0;

			//while running=true, keep ticking and rendering
			while(running)
			{
				now = System.nanoTime();
				//
				delta += (now - lastTime) / timePerTick;
				timer += now - lastTime;
				//now = time of our pc
				lastTime = now;

				if(delta >= 1)
				{
					tick();
					render();
					ticks++;
					delta--;
				}

				//checks if timer exceeds 1sec
				if(timer >= 1000000000)
				{
					System.out.println("Ticks and Frames: " + ticks);
					ticks = 0;
					timer=0;
				}
			}
			stop();
		}

//////////////////////////

		//method that returns private variable so that other classes can access it
		public KeyManager getKeyManager()
		{
			return keyManager;
		}

		public GameCamera getGameCamera()
		{
			return gameCamera;
		}

		public int getWidth()
		{
			return width;
		}

		public int getHeight()
		{
			return height;
		}

		public synchronized void start()
		{
			if(running)
			{
				return;
			}
			/////
			//if not running, set to true. Initialize thread to this class
			running = true;
			thread = new Thread(this);
			thread.start();
		}

//////////////////////////////////

		public synchronized void stop()
		{
			if(!running)
			//just return and dont execute the code below
			return;

			running = false;

/////////////////

			try
			{
				thread.join();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
