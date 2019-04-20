package repository.io;

import java.io.*;

public class IOProvider {
public static BufferedReader getReader(String fileName) throws FileNotFoundException {
    return new BufferedReader(new FileReader(fileName));
}

    public static BufferedWriter getWriter(String fileName, boolean mode) throws IOException {
        return new BufferedWriter(new FileWriter(fileName, mode));
    }

}
