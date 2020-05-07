package Utils;

import Debug.Debug;
import Json.TicketIDClass;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;

public class FilesManager {

    public static void tryToDeleteFile(String path) {
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
            Debug.p("FilesManager", "createNewUserFile", "UserDataBase file for " + path + " created!");
        else
            Debug.p("FilesManager", "createNewUserFile", "UserDataBase file for " + path + " already exist in this directory!");
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

    public static void SetTicketID(Integer ID) throws IOException {
        JSONObject TicketID = new JSONObject();
        TicketID.put("ID", ID);
        Files.write(Paths.get("Stuff/DOPE/TicketID.txt"), TicketID.toJSONString().getBytes());
    }

    public static Integer GetTicketID() throws IOException, ParseException {
        FileReader reader = new FileReader("Stuff/DOPE/TicketID.txt");
        JSONParser jsonParser = new JSONParser();
        Gson gson = new Gson();
        TicketIDClass Ticket = gson.fromJson(jsonParser.parse(reader).toString(), TicketIDClass.class);
        return Ticket.ID;
    }
}
