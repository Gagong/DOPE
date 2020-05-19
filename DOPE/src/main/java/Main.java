import Protocols.LanguageQueryProtocol;
import Protocols.CommandQueryProtocol;
import Protocols.JDAProtocol;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        JDAProtocol.ExecuteJDAProtocol();
        Thread.sleep(1000);
        CommandQueryProtocol.ExecuteMainProtocol();
        LanguageQueryProtocol.ExecuteLanguageQueryProtocol();
    }
}


