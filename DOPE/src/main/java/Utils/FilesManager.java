package Utils;

import Debug.Debug;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesManager {
    private static Collection<String> SupportID = new HashSet<String>();

    public static void readSupportList() throws IOException {
        SupportID.clear();
        Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir") + "/Support"));
        List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".txt")).collect(Collectors.toList());
        result.forEach(f -> {
            String[] name = f.split("Support");
            String UID = name[1].substring(1).split(".txt")[0].toString();
            SupportID.add(UID);
        });
    }

    public static Collection<String> returnCurrentSupportList() throws IOException {
        readSupportList();
        return SupportID;
    }

    public static String compareMembers(Collection<String> members) throws IOException {
        readSupportList();
        Collection<String> similar = new HashSet<String>(SupportID);
        Collection<String> different = new HashSet<String>();
        different.addAll(SupportID);
        different.addAll(members);
        similar.retainAll(members);
        different.removeAll(similar);
        return different.toArray()[0].toString();
    }

    public void tryToWriteFile (byte[] bytes, String path) throws IOException {
        File f = new File(path);
        f.createNewFile();
        Files.write(Paths.get(path), bytes, StandardOpenOption.APPEND);
    }

    public void tryToDeleteFile (String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (NoSuchFileException e) {
            Debug.p("FilesManager", "tryToDeleteFile", "No such file/directory exists!");
        } catch (DirectoryNotEmptyException e) {
            Debug.p("FilesManager", "tryToDeleteFile", "Directory is not empty.");
        } catch (IOException e) {
            Debug.p("FilesManager", "tryToDeleteFile", "Invalid permissions.");
        }
        Debug.p("FilesManager", "tryToDeleteFile", "File successfully removed!");
    }

    public static void createNewUserFile (String path) throws IOException {
        File userFile = new File(path + ".txt");
        if (userFile.createNewFile())
            Debug.p("FilesManager", "createNewUserFile", "DataBase file for " + path + " created!");
        else
            Debug.p("FilesManager", "createNewUserFile", "DataBase file for " + path + " already exist in this directory!");
    }

    public static void writeJson(String filename, String userName, String ID) throws Exception {
        JSONObject warnedUser = new JSONObject();
        warnedUser.put("userName", userName);
        warnedUser.put("ID", ID);
        warnedUser.put("warnedTime", Instant.now().toString());
        Files.write(Paths.get(filename + ".txt"), warnedUser.toJSONString().getBytes());
    }

    public static Object readJson(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public void updateVersion(String filename, String version) throws Exception {
        JSONObject warnedUser = new JSONObject();
        warnedUser.put("version", version);
        warnedUser.put("updateTime", Instant.now().toString());
        Files.write(Paths.get(filename + ".txt"), warnedUser.toJSONString().getBytes());
    }
}
