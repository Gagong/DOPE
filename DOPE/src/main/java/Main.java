import Utils.Api;
import java.io.IOException;

public class Main
{
    public boolean mainVer = false;
    public static void main(String[] args) throws IOException {
        Api _api = new Api();
        _api.buildJDA();
    }
}

