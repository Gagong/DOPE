package Json;

import Debug.Debug;
import com.google.gson.Gson;
import java.util.Date;

public class JSONDataParser {
    private static boolean debug = false;
    public static boolean ENABLED_GG;
    public static String GAME_VERSION;
    public static String CLI_VERSION;
    public static String DOPE_VERSION;
    public static String GAME_REMOTE;
    public static boolean PROTOCOL_OUT_OF_DATE;
    public static String BREAKING_NEWS;
    public static Date MAINTENANCE;

    public static void parser(String api) {
        Gson gson = new Gson();
        MainJSONClass data = gson.fromJson(api, MainJSONClass.class);
        VersionClass versions = gson.fromJson(data.Versions.toString(), VersionClass.class);

        ENABLED_GG = data.EnabledGG;

        GAME_VERSION = versions.game;
        CLI_VERSION = versions.cli;
        DOPE_VERSION = versions.dope;
        GAME_REMOTE = versions.game_remote;

        PROTOCOL_OUT_OF_DATE = data.ProtocolOutOfDate;

        String[] Fix_BREAKING_NEWS = data.BreakingNews;

        if (Fix_BREAKING_NEWS.length == 0) {
            BREAKING_NEWS = "";
        } else if (Fix_BREAKING_NEWS.length == 1) {
            BREAKING_NEWS = Fix_BREAKING_NEWS[0];
        } else if (Fix_BREAKING_NEWS.length == 2) {
            BREAKING_NEWS = Fix_BREAKING_NEWS[0] + "\n" + Fix_BREAKING_NEWS[1];
        } else if (Fix_BREAKING_NEWS.length == 3) {
            BREAKING_NEWS = Fix_BREAKING_NEWS[0] + "\n" + Fix_BREAKING_NEWS[1] + "\n" + Fix_BREAKING_NEWS[2];
        } else if (Fix_BREAKING_NEWS.length == 4) {
            BREAKING_NEWS = Fix_BREAKING_NEWS[0] + "\n" + Fix_BREAKING_NEWS[1] + "\n" + Fix_BREAKING_NEWS[2] + "\n" + Fix_BREAKING_NEWS[3];
        }

        MAINTENANCE = data.Maintenance;

        if (debug) {
            Debug.b("Utils", "EnabledGG", ENABLED_GG);
            Debug.p("Utils", "game", GAME_VERSION);
            Debug.p("Utils", "cli", CLI_VERSION);
            Debug.p("Utils", "dope", DOPE_VERSION);
            Debug.p("Utils", "game_remote", GAME_REMOTE);
            Debug.b("Utils", "ProtocolOutOfDate", PROTOCOL_OUT_OF_DATE);
            Debug.p("Utils", "BreakingNews", BREAKING_NEWS);
            Debug.d("Utils", "Maintenance", MAINTENANCE);
        }
    }
}
