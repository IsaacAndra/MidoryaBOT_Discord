package com.isaacandra.messages;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class RolesEmbedMessages {

    public EmbedBuilder embedBuilder = new EmbedBuilder();

    public MessageEmbed embedRoleSelectionMessage(Member author, String roleList, String guildImageUrl){
        embedBuilder
                .setAuthor(author.getUser().getEffectiveName())
                .setThumbnail(guildImageUrl)
                .setTitle("Selecione o Cargo Padrão")
                .setDescription(roleList)
                .setFooter("Digite o número do Cargo e envie nesse chat")
                .setColor(Color.ORANGE);

                return embedBuilder.build();
    }

    public MessageEmbed embedRoleSelectedMessage(Member author, String role, String guildImageUrl) {
        embedBuilder
                .setAuthor(author.getUser().getEffectiveName())
                .setThumbnail(guildImageUrl)
                .setTitle("Sucesso")
                .setDescription("O cargo selecionado foi " + role)
                .setColor(Color.GREEN);


        return embedBuilder.build();
    }

    public MessageEmbed embedRoleErrorMessage(Member author, String guildImageUrl) {
        embedBuilder
                .setAuthor(author.getUser().getEffectiveName())
                .setThumbnail(guildImageUrl)
                .setTitle("Erro: Verifique")
                .setDescription("""
                    Nenhum cargo gerenciável foi encontrado.\n
                    O cargo do bot `DEVE` ficar acima dos cargos que você deseja gerenciar. Veja as configurações do servidor!\n
                    Verifique se você selecionou o número correspondente aos cargos disponíveis!
                    """)
                .setColor(Color.RED);


        return embedBuilder.build();
    }
    public MessageEmbed embedRoleTimerMessage(Member author, String guildImageUrl) {
        embedBuilder
                .setAuthor(author.getUser().getEffectiveName())
                .setThumbnail(guildImageUrl)
                .setTitle("Demorou Demais!")
                .setDescription("""
                    Você precisa selecionar o cargo em um intervalo de 10 segundos.
                    """)
                .setFooter("Tente novamente!")
                .setColor(Color.RED);


        return embedBuilder.build();
    }
}
