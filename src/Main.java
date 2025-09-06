import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) {
        createFolder("C:/Games/src");
        createFolder("C:/Games/res");
        createFolder("C:/Games/savegames");
        createFolder("C:/Games/temp");

        createFolder("C:/Games/src/main");
        createFolder("C:/Games/src/test");

        createFile("C:/Games/src/main/Main.java");
        createFile("C:/Games/src/main/Utils.java");

        createFolder("C:/Games/res/drawables");
        createFolder("C:/Games/res/vectors");
        createFolder("C:/Games/res/icons");

        try (FileWriter fw = new FileWriter("C:/Games/temp/temp.txt", true)) {
            fw.write(String.valueOf(stringBuilder));
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFolder(String folderPath) {
        File file = new File(folderPath);

        if (file.mkdir()) {
            stringBuilder.append("Папка ").append(folderPath).append(" удачно создана!\n");
        } else {
            stringBuilder.append("Папка ").append(folderPath).append(" была создана ранее\n");
        }
    };

    public static void createFile(String filePath) {
        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                stringBuilder.append("Файл ").append(filePath).append(" удачно создан!\n");
            } else {
                stringBuilder.append("Файл ").append(filePath).append(" был создан ранее\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
