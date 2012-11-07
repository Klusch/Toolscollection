package dateilisting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileSystemView;

public class Dateilisting {

    public static void diskspace() {
        System.out.println("Drive     Total        Free      Usable");
        System.out.println("---------------------------------------");
        File dir[] = File.listRoots();
        for (int i = 0; i < dir.length; i++) {
            System.out.printf("%s   %6d MB   %6d MB  %6d MB%n", dir[i],
                    dir[i].getTotalSpace() / (1024 * 1024),
                    dir[i].getFreeSpace() / (1024 * 1024),
                    dir[i].getUsableSpace() / (1024 * 1024));
        }
    }

    public static void laufwerksbuchstaben() {
        FileSystemView view = FileSystemView.getFileSystemView();
        File f[] = File.listRoots();
        for (int i = 0; i < f.length; i++) {
            if (f[i].exists()) {
                System.out.println(view.getSystemDisplayName(f[i]));
            } else {
                System.out.println("- kein Datenträger eingelegt -");
            }
        }
    }

    public static List<File> find(String start, String extensionPattern) {
        List<File> files = new ArrayList<File>(1024);
        Stack<File> dirs = new Stack<File>();
        File startdir = new File(start);
        //Pattern p = Pattern.compile(extensionPattern, Pattern.CASE_INSENSITIVE);

        if (startdir.isDirectory()) {
            dirs.push(startdir);
        }

        while (dirs.size() > 0) {
            for (File file : dirs.pop().listFiles()) {
                if (file.isDirectory()) {
                    dirs.push(file);
                    System.out.println(file.getName());
                } else {
                    //if (p.matcher(file.getName()).matches())
                    //files.add(file);
                    //System.out.println(file.getName());
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        //diskspace();
        find("D:/", null);
    }
}
