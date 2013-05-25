/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg9gag;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Drak
 */
public class Downloader {

    private int count = 0;
    private int badcount = 0;
    private int screenWidth = Integer.MAX_VALUE;
    private int screenHeight = Integer.MAX_VALUE;

    public Downloader() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean downloadImages(String dir, Map<String, String> imageUrls) {
        boolean getNext = true;
        badcount = 0;
        URL image;
        InputStream is = null;
        OutputStream os = null;
        int len;
        
        Iterator it = imageUrls.entrySet().iterator();
        
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();

            try {
                String url = pair.getValue().toString();
                String ext = url.substring(url.lastIndexOf("."), url.length());
                String filename = dir + pair.getKey() + ext;

                File file = new File(filename);

                if (file.exists()) {
                    badcount++;
                    continue;
                }
                
                image = new URL(url);

                is = image.openStream();
                os = new FileOutputStream(filename);

                byte[] buffer = new byte[2048];
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }

                BufferedImage img = ImageIO.read(file);

                int height = img.getHeight();
                int width = img.getWidth();

                if (width > screenWidth || height > screenHeight || 
                        file.length() == 0L) {
                    try {
                        if (is != null) {
                            is.close();
                            is = null;
                        }
                        if (os != null) {
                            os.close();
                            os = null;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    file.delete();
                    continue;
                }

                System.out.print(".");
                count++;

            } catch (Exception ex) {
                continue;
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            it.remove();
        }

        if (badcount > 5) {
            getNext = false;
        }

        return getNext;
    }
}
