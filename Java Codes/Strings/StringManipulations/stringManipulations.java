import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class stringManipulations {
  public static void main (String [] args) throws IOException {
  File inputFile = new File ("stringInputFile.txt");
  List<String> fileStringList = new ArrayList<String>();
  fileStringList = readFileToStringArray(inputFile, fileStringList);
  System.out.println(stringReverse (fileStringList.get(0)));
  }
  
  public static List<String> readFileToStringArray (File inputFile, List<String> fileStringList) throws IOException {
    FileReader fr = new FileReader(inputFile);
    BufferedReader br = new BufferedReader(fr);
    String line;
    while( (line = br.readLine()) != null) {
      fileStringList.add(line);
    }
    return fileStringList;
  }

  public static String stringReverse (String inputString) {
    char [] charArray = inputString.toCharArray();
    char temp;
    for(int i=0; i<charArray.length/2; i++) {
      temp = charArray[i];
      charArray[i] = charArray[charArray.length - 1 - i];
      charArray[charArray.length - 1 - i] = temp;
    }
    
    String outputString = String.valueOf(charArray);
    return outputString;

  }
}
