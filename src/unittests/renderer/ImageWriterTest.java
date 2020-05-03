package unittests.renderer;

import org.junit.Test;

import java.awt.*;
import java.util.Random;
import renderer.ImageWriter;
/**
 * Created by Moishe on 22/05/2017.
 */
public class ImageWriterTest {

    @Test
    public void writeImageTest(){

        ImageWriter imageWriter = new ImageWriter("Image writer test", 500, 500, 1, 1);
        Random rand = new Random();
        for (int i = 0; i < imageWriter.getHeight(); i++){
            for (int j = 0; j < imageWriter.getWidth(); j++)
            {

                if (i % 25 == 0 || j % 25 == 0 || i == j || i == 499 || j == 499 || i == 500 - j)
                    imageWriter.writePixel(j, i, new Color(0, 0, 0));  // Black
                else
                if(i >= 200 && i <= 300 && j >= 200 && j <= 300)
                    imageWriter.writePixel(j, i,new Color( 255, 0, 0)); // Red
                else
                if(i >= 150 && i <= 350 && j >= 150 && j <= 350)
                    imageWriter.writePixel(j, i,new Color( 0, 255, 0));  // Green
                else
                if(i >= 100 && i <= 400 && j >= 100 && j <= 400)
                    imageWriter.writePixel(j, i,new Color( 0, 0, 255)); // Blue
                else
                if(i >= 50 && i <= 450 && j >= 50 && j <= 450)
                    imageWriter.writePixel(j, i,new Color( 255, 255, 0)); // Yellow
                else
                    imageWriter.writePixel(j, i, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))); // Random
            }
        }

        imageWriter.writeToImage();
    }

    @Test
    public void writeImageFlagTest(){
        ImageWriter imageWriter = new ImageWriter("Image writer Haiti flag test", 450, 300, 1, 1);
        for (int i = 0; i < imageWriter.getHeight(); i++){
            for (int j = 0; j < imageWriter.getWidth(); j++)
            {
                if(i>=299||i<=1||j>=449||j<=1)
                    imageWriter.writePixel(j,i,new Color(105,105,105));
                else
                if(i >= 150)
                    imageWriter.writePixel(j, i,new Color( 238, 64, 0)); // Red
                else
                    imageWriter.writePixel(j, i,new Color( 58, 95, 205)); // Blue

            }
        }
        imageWriter.writeToImage();
    }

    @Test
    public void writeImageFlagTest2() {
        ImageWriter imageWriter = new ImageWriter("Image writer Libya flag test", 450, 300, 1, 1);
        for (int i = 0; i < imageWriter.getHeight(); i++) {
            for (int j = 0; j < imageWriter.getWidth(); j++) {
                if (i >= 299 || i <= 1 || j >= 449 || j <= 1)
                    imageWriter.writePixel(j, i,new Color( 105, 105, 105));
                else
                    imageWriter.writePixel(j, i, new Color(60, 179, 113));
            }
        }
        imageWriter.writeToImage();
    }
}
