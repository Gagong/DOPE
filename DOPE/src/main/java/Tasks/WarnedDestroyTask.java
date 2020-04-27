package Tasks;

import Json.UserDataBase;
import Protocols.JDAProtocol;
import Variables.Channels;
import Variables.Roles;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarnedDestroyTask extends TimerTask {
    Channels Channels = new Channels();
    Roles Roles = new Roles();

    @Override
    public void run() {
        JDA jda = JDAProtocol.JDA;
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir") + "/Users"))) {
            List<String> result = walk.map(Path::toString).filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

            result.forEach(warnedFile -> {
                Gson gson = new Gson();
                UserDataBase data = null;
                try {
                    data = gson.fromJson(Utils.FilesManager.readJson(warnedFile).toString(), UserDataBase.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                assert data != null;
                Instant instant = Instant.parse(data.warnedTime);
                Duration timeElapsed = Duration.between(instant, Instant.now());

                if (timeElapsed.toHours() >= 24) {
                    Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER))
                            .removeRoleFromMember(Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER))
                                    .getMember(Objects.requireNonNull(jda.getUserById(data.ID)))), Objects.requireNonNull(jda.getRoleById(Roles.WARNED))).queue();
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("SYNC: Removed Warned role event!");
                    log.setDescription("Removed from: " + data.userName + "\nID: " + data.ID + "\nWarned Time:" + data.warnedTime);
                    log.setAuthor("DOPE MODERATION", null, null);
                    log.setColor(Color.green);
                    log.setTimestamp(Instant.now());
                    Objects.requireNonNull(jda.getTextChannelById(Channels.WARNED_ARCHIVE)).sendMessage(log.build()).queue();
                    Utils.FilesManager.tryToDeleteFile(warnedFile);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
