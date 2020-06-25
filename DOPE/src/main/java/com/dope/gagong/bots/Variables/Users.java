package com.dope.gagong.bots.Variables;

import net.dv8tion.jda.api.entities.User;

public class Users {
    public String PowerOfDark = "173743111023886336";
    public String FrontendDev = "396067257760874496";

    public String Gagong = "140422565393858560";
    public String Kewai = "323058900771536898";

    public String CrankTV = "270647751941947393";
    public String zhoiak = "380786597458870282";
    public String AD3RTRON = "235114392482480139";
    public String Sumi = "214092234285514753";
    public String era = "206781133596262401";
    public String Fabio = "237666016740507649";

    public String DOPE = "609397286757466133";
    public String BLACK_DOPE = "670367326356504604";

    public boolean isDevsOrCM (User author) {
        return (author.getId().equals(PowerOfDark) ||
                author.getId().equals(FrontendDev) ||
                author.getId().equals(Gagong) ||
                author.getId().equals(Kewai));
    }
}
