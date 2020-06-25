package com.dope.gagong.bots.Tasks;

import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Utils.SQL;
import com.dope.gagong.bots.Variables.Channels;
import com.dope.gagong.bots.Variables.Roles;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class WarnedDestroyTask extends TimerTask {
    Channels Channels = new Channels();
    Roles Roles = new Roles();
    HashMap<String, String> WarnedUsers = new HashMap<>();
    Collection<String> ServerWarnedUsers = new HashSet<>();

    @Override
    public void run() {
        JDA jda = JDAProtocol.JDA;
        try {
            ResultSet sqlResult = SQL.getWarnedUsersFromSQL();
            while (sqlResult.next()) {
                String UID = sqlResult.getString("UserID");
                String warnedTime = sqlResult.getString("WarnedTime");
                WarnedUsers.put(UID, warnedTime);
            }
            WarnedUsers.forEach((k, v) -> {
                Instant instant = Instant.parse(v);
                Duration timeElapsed = Duration.between(instant, Instant.now());

                if (timeElapsed.toHours() >= 24) {
                    Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER))
                            .removeRoleFromMember(Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER))
                                    .getMember(Objects.requireNonNull(jda.getUserById(k)))), Objects.requireNonNull(jda.getRoleById(Roles.WARNED))).queue();
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("SYNC: Removed Warned role event!");
                    log.setDescription("Removed from: " + Objects.requireNonNull(jda.getUserById(k)).getName() + "\nID: " + k + "\nWarned Time: " + instant);
                    log.setAuthor("DOPE MODERATION", null, null);
                    log.setColor(Color.green);
                    log.setTimestamp(Instant.now());
                    Objects.requireNonNull(jda.getTextChannelById(Channels.WARNED_ARCHIVE)).sendMessage(log.build()).queue();
                    try {
                        SQL.deleteWarnedUserFromSQL(k);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            List<Member> Members = Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER)).getMembers();
            Members.forEach(member -> {
                List<Role> roles = member.getRoles();
                roles.forEach(role -> {
                    if (role.getId().equals(Roles.WARNED))
                        ServerWarnedUsers.add(member.getId());
                });
            });
            ServerWarnedUsers.forEach(user -> {
                try {
                    if (!SQL.getWarnedUserFromSQLByID(user)) {
                        Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER))
                                .removeRoleFromMember(Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(Channels.MAIN_SERVER))
                                        .getMember(Objects.requireNonNull(jda.getUserById(user)))), Objects.requireNonNull(jda.getRoleById(Roles.WARNED))).queue();
                        EmbedBuilder log = new EmbedBuilder();
                        log.setTitle("SYNC: Removed Warned role event!");
                        log.setDescription("Removed from: " + Objects.requireNonNull(jda.getUserById(user)).getName() + "\nID: " + user + "\nWarned Time: NO DATA!");
                        log.setAuthor("DOPE MODERATION", null, null);
                        log.setColor(Color.green);
                        log.setTimestamp(Instant.now());
                        Objects.requireNonNull(jda.getTextChannelById(Channels.WARNED_ARCHIVE)).sendMessage(log.build()).queue();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
