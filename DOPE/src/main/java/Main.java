import Utils.GetJDA;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
        GetJDA GetJDA = new GetJDA();
        GetJDA.buildJDA();
        ProcessBuilder process = new ProcessBuilder();
        process.command("bash", "-c", "python ./Test.py");
        Process p = process.start();
    }
}


