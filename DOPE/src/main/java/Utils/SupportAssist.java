package Utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SupportAssist {
    private List<String> SupportID = new ArrayList<String>();

    public void readSupportFromFile () {
        try {
            SupportID = Files.readAllLines(Paths.get("SupportUsers.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> returnCurrentSupportList() {
        readSupportFromFile();
        return SupportID;
    }

    public String compareMembers(Collection<String> members) {
        readSupportFromFile();
        Collection<String> similar = new HashSet<String>(SupportID);
        Collection<String> different = new HashSet<String>();
        different.addAll(SupportID);
        different.addAll(members);
        similar.retainAll(members);
        different.removeAll(similar);
        return different.toArray()[0].toString();
    }
}
