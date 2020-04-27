import Protocols.LanguageQueryProtocol;
import Protocols.CommandQueryProtocol;
import Protocols.JDAProtocol;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JDAProtocol.ExecuteJDAProtocol();
        CommandQueryProtocol.ExecuteMainProtocol();
        LanguageQueryProtocol.ExecuteLanguageQueryProtocol();
        /*ProcessBuilder process = new ProcessBuilder();
        process.command("bash", "-c", "python ./Test.py");
        Process p = process.start();*/
    }
}


