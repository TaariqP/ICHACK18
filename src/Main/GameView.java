package Main;

import States.StateManager;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class GameView extends Container implements Runnable, KeyListener,
    MouseListener{

  private int Width = 320;
  private int Height = 240;
  private int Scale = 4;

  private boolean running;
  private Thread thread;
  private int targetTps = 60;

  private BufferedImage image;
  private Graphics2D g;
  private int total = 0;
  private int counter = 0;
  private int mean = 0;
  private String question;
  private static StateManager manager;


  public GameView() {
    super();
    setPreferredSize(new Dimension(Width * Scale, Height * Scale));
    setFocusable(true);
    requestFocus();
    init();
  }

  public void init() {
    image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
    running = true;
    g = (Graphics2D) image.getGraphics();
    thread = new Thread(this);
    manager = new StateManager(Width, Height);
    thread.start();

  }

  public void draw() {
  g.setColor(Color.WHITE);
  g.fillRect(0,0,Width, Height);
  manager.draw(g);
  }

  public void run() {
    int fps = 0;
    int tick = 0;
    double fpsTimer = System.currentTimeMillis();
    double secondsPerTick = 1D / targetTps;
    double nsPerTick = secondsPerTick * 1000000000D;
    double then = System.nanoTime();
    double now;
    double unprocessed = 0;
    while (running) {
      now = System.nanoTime();
      unprocessed += (now - then);
      then = now;
      while (unprocessed >= nsPerTick) {
        update();
        tick++;
        unprocessed -= nsPerTick;
      }
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      draw();
      drawToScreen();
      fps++;
      if (System.currentTimeMillis() - fpsTimer >= 1000) {
        total += fps;
        counter++;
        mean = total / counter;
        fps = 0;
        tick = 0;
        fpsTimer += 1000;
      }
    }
  }

  private void drawToScreen() {
    Graphics g2 = getGraphics();
    g2.drawImage(image, 0, 0, Width * Scale, Height * Scale, null);
    g2.dispose();
  }

  private void update() {

  }

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    manager.keyPressed(e);
  }

  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void mouseClicked(MouseEvent mouseEvent) {
    manager.clickAt(mouseEvent);
  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {

  }
}
