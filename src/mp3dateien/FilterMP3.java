/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mp3dateien;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Alexander Kluge
 */
public class FilterMP3 implements FilenameFilter{

    public boolean accept(File dir, String name) {
      String endung = name.substring(name.length() - 4);
      if (endung.equalsIgnoreCase(".mp3")) {
        return true;
      } else {
        return false;
      }
    }

}
