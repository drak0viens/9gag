/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg9gag;

import java.sql.Time;

/**
 *
 * @author Drak
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        final String dir = "C:\\9gag\\";
        WebClient client = new WebClient();
        Downloader dw = new Downloader();

        System.out.print("Downloading...");

            boolean cont = true;
            
            while (cont) {
                cont = dw.downloadImages(dir, client.getImageUrls());
            }

            Time time = new Time(System.currentTimeMillis());
            System.out.println("\nDownloaded " + dw.getCount() + 
                    " new images ["+ time.toString() +"]\n");
            
            Thread.sleep(3000);
    }
}
