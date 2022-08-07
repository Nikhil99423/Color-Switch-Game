package g;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

import java.io.*;

public class encoding{
    public void serial(String s) throws IOException {
        Game g=new Game();
        ObjectOutputStream out=null;
        try{
            out=new ObjectOutputStream(new FileOutputStream(s+".txt"));
            out.writeObject(g);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    public Game deserial(String ss) throws IOException{
        ObjectInputStream in =null;
        Game g = null;
        try{
            in=new ObjectInputStream(new FileInputStream(ss+".txt"));
            g=(Game)in.readObject();

        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return g;
    }
}