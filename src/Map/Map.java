package Map;

import States.Camera;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;

public class Map {

  private Tile[][] map;
  private Camera camera;
  private static final int PIXEL_SIZE = 64;
  private final String fileName;
  private int width;
  private int height;

  public Map(String fileName) {
    this(fileName, new Camera(PIXEL_SIZE*PIXEL_SIZE, PIXEL_SIZE*PIXEL_SIZE));
  }

  public Map(String fileName, Camera camera) {
    this.fileName = fileName;
    this.camera = camera;
    convertStringToMap();
  }

  public int getWidth() {
    return map.length * PIXEL_SIZE;
  }

  public int getHeight() {
    return map[0].length * PIXEL_SIZE;
  }

  public int getTileHeight(){
    return height;
  }

  public int getTileWidth(){
    return width;
  }



  public void convertStringToMap() {

    // The name of the file to open.

    // This will reference one line at a time
    String line = null;

    try {
      // FileReader reads text files in the default encoding.
      FileReader fileReader =
          new FileReader(fileName);

      // Always wrap FileReader in BufferedReader.
      BufferedReader bufferedReader =
          new BufferedReader(fileReader);

      String dimensionsLine = bufferedReader.readLine();
      String[] dimensions = dimensionsLine.split(" ");
      int width = Integer.parseInt(dimensions[0]);
      int height = Integer.parseInt(dimensions[1]);
      this.width = width;
      this.height = height;

      map = new Tile[width][height];

      populateTilesFromFile(fileName);

      // Always close files.
      bufferedReader.close();

    } catch (FileNotFoundException ex) {
      System.out.println(
          "Unable to open file '" +
              fileName + "'");
    } catch (IOException ex) {
      System.out.println(
          "Error reading file '"
              + fileName + "'");
      // Or we could just do this:
      // ex.printStackTrace();
    }

  }

  // pre: array is non-jagged and the height/width matches that in the file
  private void populateTilesFromFile(String fileName) {

    try {
      FileReader fileReader =
          new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      //bufferedReader.readLine();
      bufferedReader.readLine();
      for (int x = 0; x < map.length; x++) {
        String row = bufferedReader.readLine();
        String[] column = row.split(" ");
        for (int y = 0; y < column.length; y++) {
          map[x][y] = convertFromStringToTile(column[y]);
        }
      }
    } catch (FileNotFoundException ex) {
      System.out.println(
          "Unable to open file '" +
              fileName + "'");
    } catch (IOException ex) {
      System.out.println(
          "Error reading file '"
              + fileName + "'");
    }
  }

  private Tile convertFromStringToTile(String tileString) {
    switch (tileString) {
      case "grass":
        return new Tile(TileType.GRASS);
      case "water":
        return new Tile(TileType.WATER);
      case "wall":
        return new Tile(TileType.WALL);
      default:
        return new Tile(TileType.ERROR);
    }
  }

  public void draw(Graphics2D g) {
    int topLeftX = camera.getX() - 640;
    int topLeftY = camera.getY() - 480;
    for (int i = camera.getX() / 64 - 11; i < camera.getX() / 64 + 11; i++) {
      for (int j = camera.getY() / 64 - 11; j < camera.getY() / 64 + 11; j++) {
        if (i >= 0 && j >= 0 && i < map.length && j < map[0].length) {
            map[i][j].draw(g, i * 64 - topLeftX, j * 64 - topLeftY);
          } else {
          File location = new File("PNG/Tiles/tile_1.png");
          BufferedImage image = null;
          try {
            image = ImageIO.read(location);
          } catch (IOException e) {
            e.printStackTrace();
          }
          g.drawImage(image, i * 64 - topLeftX, j * 64 - topLeftY, null);
        }
      }
    }
  }

  public Tile getTileAtPosition(int x, int y) {
    return map[x][y];
  }

  public void setTileAtPosition(int x, int y, TileType tileType) {
    map[x][y].setTileType(tileType);
  }
}
