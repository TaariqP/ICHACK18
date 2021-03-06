package States;

import Map.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class GameOver extends State {

  private BufferedImage image;
  private List<Button> buttons;
  private Map endmap;
  private Camera deathcamera;

  public GameOver(int width, int height, StateManager manager) {
    super("GameOver", width, height, manager);
    buttons = new ArrayList<>();
    initStart();
    initExit();
    File location = new File("PNG/death-skull-and-bones.png");
    image = null;
    try {
      image = ImageIO.read(location);
    } catch (IOException e) {
      e.printStackTrace();
    }
    pickaMap();
  }

  private void pickaMap() {
    deathcamera = new Camera(64 * 64, 64 * 64);
    Random picker = new Random();
    int m = picker.nextInt(10);
    endmap = new Map("Maps/" + String.valueOf(m) + ".txt", deathcamera);
  }

  private void moveMap() {
    if (deathcamera.getX() < 128 * 64 - getWidth()) {
      deathcamera.setX(deathcamera.getX() + 4);
    } else {
      deathcamera.setX(getWidth());
    }
  }


  @Override
  public void draw(Graphics2D g) {
    moveMap();
      endmap.draw(g);
    g.setColor(Color.black);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
    g.drawImage(image, super.getWidth() / 2 - image
        .getWidth() / 2, super.getHeight() / 4 - 200, null);
    for (Button b : buttons) {
      b.draw(g);
    }
  }

  private void initExit() {
    Clickable exitClick = new Clickable() {
      @Override
      public void click() {
        System.exit(0);
      }
    };
    File file = new File("PNG/buttons/button_exit.png");
    BufferedImage image = null;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    buttons.add(new Button("Exit", image, super.getWidth() / 2 - image
        .getWidth() / 2, (super.getHeight() / 2) + 250, exitClick));
  }

  private void initStart() {
    Clickable startClick = new Clickable() {
      @Override
      public void click() {
        getManager().states.replace(StateManager.Game_ID, new Game(getWidth()
            , getHeight(), getManager()));
        getManager().SwitchState(StateManager.Game_ID);
      }
    };
    File file = new File("PNG/buttons/button_pa.png");
    BufferedImage image = null;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }

    buttons.add(new Button("Play again", image, super.getWidth() / 2 - image
        .getWidth() / 2, super.getHeight() / 2 + 120, startClick));
  }

  @Override
  public void init() {
    super.init();
  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void clickAt(MouseEvent mouseEvent) {
    for (Button b : buttons) {
      b.clickAt(mouseEvent);
    }
  }

  @Override
  public void mouseMoved(MouseEvent mouseEvent) {

  }

  @Override
  public void update() {
    super.update();
  }
}
