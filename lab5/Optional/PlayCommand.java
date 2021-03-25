package com.company;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayCommand {
    public static void play(Item item) throws IOException {
        try {
            File file = new File(item.getLocation() + "//" + item.getName() + '.' +item.getExtensie());

            if(!(file.exists())){
                throw new MyCustomException("File does not exist!");
            }

            Desktop.getDesktop().open(file);
        }catch(java.lang.IllegalArgumentException e){
            System.out.println("File not found!");
        }catch (MyCustomException x){
            x.printStackTrace();
        }
    }
}
