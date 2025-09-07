import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

        GameProgress gameProgress1 = new GameProgress(10, 100, 2, 243.3);
        GameProgress gameProgress2 = new GameProgress(45, 53, 4, 453.7);
        GameProgress gameProgress3 = new GameProgress(67, 133, 7, 734.1);

        saveGame("C:/Games/savegames/saveGame1.dat", gameProgress1);
        saveGame("C:/Games/savegames/saveGame2.dat", gameProgress2);
        saveGame("C:/Games/savegames/saveGame3.dat", gameProgress3);

        List<String> filesDat = Arrays.asList("C:/Games/savegames/saveGame1.dat", "C:/Games/savegames/saveGame2.dat",
                "C:/Games/savegames/saveGame3.dat");

        zipFiles("C:/Games/savegames/savesGame.zip", filesDat);
        deleteFilesDat(filesDat);
        openZip("C:/Games/savegames/savesGame.zip", "C:/Games/savegames");

        GameProgress gp = openProgress("C:/Games/savegames/saveGame2.dat");
        System.out.println(gp);
    }

    public static void createFolder(String folderPath) {
        File file = new File(folderPath);

        if (file.mkdir()) {
            stringBuilder.append("Папка ").append(folderPath).append(" удачно создана!\n");
        } else {
            stringBuilder.append("Папка ").append(folderPath).append(" была создана ранее\n");
        }
    }

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

    public static void saveGame(String pathDat, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(pathDat);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zipFiles(String filePath, List<String> filesDat) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(filePath))) {
            for (String fileDat : filesDat) {
                try (FileInputStream fis = new FileInputStream(fileDat)) {
                    ZipEntry zipEntry = new ZipEntry(fileDat);
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFilesDat(List<String> filesDat) {
        for (String fileDat : filesDat) {
            File file = new File(fileDat);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void openZip(String filePath, String dirPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                try (FileOutputStream fos = new FileOutputStream(fileName)) {
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        fos.write(c);
                    }
                    fos.flush();
                    zis.closeEntry();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameProgress openProgress(String fileDatPath) {
        GameProgress gameProgress;
        try (FileInputStream fis = new FileInputStream(fileDatPath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return gameProgress;
    }
}
