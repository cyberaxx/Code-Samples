import java.io.File;
import java.util.Arrays;

public class FileSort {

  public static void main (String [] args) {
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    Arrays.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());
  }

}
