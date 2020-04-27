package Variables;

import Json.JSONDataParser;
import Utils.Base64Utils;

public class Variables {
    Base64Utils _base = new Base64Utils();

    public String PREFIX = "!";

    //public String BOT_KEY = _base.Decode("TmpNM056RTRORGN5TkRBeU5qWTFORGN5LlhtWHNrUS5xYlB5VjRCM3JELXVnaC0wVXNmeVNlQng2bzA="); // TEST
    public String BOT_KEY = _base.Decode("TmpBNU16azNNamcyTnpVM05EWTJNVE16LlhqRzNLUS5FVkM2dWduR2tnV1N6VHdVOG8tOUlZSGN5UFU=");

    public String FLAG_RU = "U+1f1f7U+1f1fa";
    public String FLAG_EN = "U+1f1faU+1f1f8";
    public String FLAG_IT = "U+1f1eeU+1f1f9";
    public String FLAG_PT = "U+1f1f5U+1f1f9";
    public String FLAG_HU = "U+1f1edU+1f1fa";
    public String FLAG_TR = "U+1f1f9U+1f1f7";
    public String FLAG_DE = "U+1f1e9U+1f1ea";
    public String FLAG_ES = "U+1f1eaU+1f1f8";
    public String FLAG_PL = "U+1f1f5U+1f1f1";
    public String FLAG_CZ = "U+1f1e8U+1f1ff";
    public String FLAG_SK = "U+1f1f8U+1f1f0";
    public String FLAG_FR = "U+1f1ebU+1f1f7";

    public String SUPPORT_EMBED = "704348154631290921";
    public String BUG_REPORT_EMBED = "704348057684279346";
    public String SUPPORT_CHANNEL = "656178853055561739";
    public String BUG_REPORT_CHANNEL = "606538901242380318";
    public String SUPPORT_CATEGORY = "656194495725174785";
    public String BUG_REPORT_CATEGORY = "681539905247248471";
    public String TICKETS_ARCHIVE = "655729108789100565";

    public String CHANNEL_PATTERN = "ticket-";

    public String WINDOWS = "703674920164655224";
    public String LINUX = "703675811474964551";
    public String ENVELOPE = "U+1f4e9";
    public String LOCK = "U+1f512";

    public String DISCORD = "https://discord.gg/nNqEJRk";
    public String API = "https://powerofdark.space/api/status";
    public String WEB_URL = "https://powerofdark.space";
    public String DOWNLOAD_URL = "https://powerofdark.space/#Home/Download/";

    public String DOPE = JSONDataParser.DOPE_VERSION;
    public String CLI = JSONDataParser.CLI_VERSION;

    public String WINDOWS_64 = "https://powerofdark.space/downloads/DOPE/" + DOPE + "/DOPE.exe";
    public String WINDOWS_86 = "https://powerofdark.space/downloads/DOPE/" + DOPE + "/DOPE32.zip";
    public String LINUX_64 = "https://powerofdark.space/downloads/DOPE/" + CLI + "/DOPE.cli";
    public String LINUX_ARM = "https://powerofdark.space/downloads/DOPE/" + CLI + "/DOPE.cli-arm.zip";

    public String BOT_PANEL = "https://powerofdark.space/#Panel/";
    public String LICENSES = "https://powerofdark.space/#Licenses/";
    public String INFO = "https://powerofdark.space/#Info/";
    public String GUIDE = "https://powerofdark.space/#Guide/";
}
