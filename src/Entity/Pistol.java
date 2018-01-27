package Entity;

import Map.Coordinate;
import States.Camera;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pistol extends Weapon {

  private static final String weaponName = "pistol";
  private static final int attackDamage = 10;
  private BufferedImage image;

  public Pistol(
      Coordinate position, Camera camera) {
    super(position, weaponName, attackDamage, camera);
    File location = new File("PNG/weapon_gun.png");
    image = null;
    try {
      image = ImageIO.read(location);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Rectangle getBounds() {
    return new Rectangle(position.getX(), position.getY(), image
        .getWidth(),
        image.getHeight());
  }

  public void draw(Graphics2D g) {
    if (position.getX() >= camera.getX() - 2000 && position.getX() <= camera
        .getX() + 2000 && position.getY() >= camera.getY() - 2000 && position
        .getY() <= camera.getY() + 2000) {
      g.drawImage(image, position.getX() - camera.getX(), position.getY() -
              camera.getY(),
          null);
    }
  }
}

