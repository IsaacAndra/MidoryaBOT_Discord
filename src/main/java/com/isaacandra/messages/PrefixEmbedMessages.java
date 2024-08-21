package com.isaacandra.messages;

import com.isaacandra.utils.RegexUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class PrefixEmbedMessages {

    public EmbedBuilder embedBuilder = new EmbedBuilder();
    RegexUtils regexUtils = new RegexUtils();
    public MessageEmbed embedGetPrefixMessage(Member author, String currentPrefix, String guildImageUrl) {

        embedBuilder
                .setAuthor(author.getUser().getEffectiveName(), author.getAvatarUrl(), author.getUser().getAvatarUrl())
                .setThumbnail(guildImageUrl)
                .setTitle("PREFIXO ATUAL")
                .setDescription("O prefixo atual é: " + "`" + currentPrefix + "`")
                .setColor(Color.ORANGE);


        return embedBuilder.build();
    }

    public MessageEmbed embedSetPrefixMessage(Member author, String newPrefix, String guildImageUrl) {
        embedBuilder
                .setAuthor(author.getUser().getEffectiveName(), author.getAvatarUrl(), author.getUser().getAvatarUrl())
                .setThumbnail(guildImageUrl)
                .setTitle("Prefixo Alterado")
                .setDescription("Prefixo foi alterado para: " + "`" + newPrefix + "`")
                .setColor(Color.GREEN);

        return embedBuilder.build();
    }

    public MessageEmbed embedSetPrefixVerficationMessage(Member author, String guildImageUrl) {
        embedBuilder
                .setAuthor(author.getUser().getEffectiveName(), author.getAvatarUrl(), author.getUser().getAvatarUrl())
                .setThumbnail(guildImageUrl)
                .setTitle("ERRO")
                .setDescription("Por favor, forneça um prefixo válido! Exemplo: !setPrefix $")
                .setFooter("Prefixos válidos: " + regexUtils.getSpecialCharacteresRegex())
                .setColor(Color.RED);

        return embedBuilder.build();
    }
}