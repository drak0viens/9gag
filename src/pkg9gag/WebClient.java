/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg9gag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Drak
 */
public class WebClient {

    private URL Url;
    private URLConnection Conn;
    private BufferedReader Reader;
    private StringBuffer PageBuffer;
    private boolean Isopen = false;
    private String NextPage = null;
    private final String WEBSITE = "http://9gag.com";
    
    public WebClient() {
        PageBuffer = new StringBuffer();
    }    
    
    public void open() {
        try {
            if(NextPage == null){
                Url = new URL(WEBSITE);
            } else {
                Url = new URL(WEBSITE + NextPage);
            }
            
            Conn = Url.openConnection();
            Reader = new BufferedReader(new InputStreamReader(
                    Conn.getInputStream()));
            Isopen = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            Reader.close();
            Isopen = false;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void toBegin() {
        this.NextPage = null;
    }

    public Map<String, String> getImageUrls() {

        String line;
        Map<String,String> images = new HashMap<String, String>();

        if (!Isopen) {
            this.open();
        }

        PageBuffer.delete(0, PageBuffer.length());

        try {

            while ((line = Reader.readLine()) != null) {
                
                if (line.contains("<div class=\"img-wrap\">")){
                    Reader.readLine();
                    line = Reader.readLine();
                    
                    int beg, end;
                    
                    beg = line.indexOf("src=\"");
                    beg += 5;
                    
                    for(end = beg; line.charAt(end) != '"'; end++)
                        ;
                    
                    String url = line.substring(beg, end);                    
                    
                    beg = line.indexOf("alt=\"");
                    beg += 5;
                    
                    for(end = beg; line.charAt(end) != '"'; end++)
                        ;
                    
                    String name = line.substring(beg, end);
                    name = name.replace("&#039;", "");
                    
                    images.put(name, url);
                }
                
                if (line.contains("next_button")){
                    
                    int beg, end;
                     //<a id="next_button" class='next' href="/hot/id/5818841">
                    
                    beg = line.lastIndexOf("href=\"");
                    beg += 6;
                    
                    for(end = beg; line.charAt(end) != '"'; end++)
                        ;
                    
                    NextPage = line.substring(beg, end);
                    
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        this.close();
        return images;
    }
}
