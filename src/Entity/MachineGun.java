package Entity;

import Map.Coordinate;
import States.Camera;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MachineGun extends Weapon {

  private static final String weaponName = "Machine Gun";
  private static final int attackDamage = 20;
  private BufferedImage image;


  public MachineGun(
      Coordinate position, Camera camera) {
    super(position, weaponName, attackDamage, camera, 30, 0);
    File location = new File("PNG/weapon_machine.png");
    image = null;
    try {
      image = ImageIO.read(location);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int reload(int ammo) {
    if (ammo + super.CURRENT_CLIP <= 30) {
      super.CURRENT_CLIP += ammo;
      return 0;
    } else {
      ammo -= (30 - super.CURRENT_CLIP);
      super.CURRENT_CLIP = 30;
      return ammo;
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
      g.drawImage(image, position.getX() - camera.getX() + 640, position
              .getY() -
              camera.getY() + 480,
          null);
      g.drawString("Machine Gun", position.getX() -
              camera.getX
                  () + 640 - image.getWidth() / 2,
          position.getY
              () -
              camera.getY() + 480);
    }
  }
}

