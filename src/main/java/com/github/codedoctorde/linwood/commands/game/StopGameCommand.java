package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class StopGameCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 0)
        return false;
        var bundle = getBundle(entity);
        if(message.getMember() == null)
            return false;
        if(!entity.getGameEntity().isGameMaster(message.getMember())){
            message.getChannel().sendMessage(bundle.getString("NoPermission")).queue();
            return true;
        }
        if(Linwood.getInstance().getSingleApplicationManager().getGame(entity.getGuildId()) == null)
            message.getTextChannel().sendMessage(bundle.getString("NoGameRunning")).queue();
        else {
            Linwood.getInstance().getSingleApplicationManager().stopGame(entity.getGuildId());
            message.getTextChannel().sendMessage(bundle.getString("Success")).queue();
        }
        return true;
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "stop",
                "stopgame",
                "cancel",
                "stop-game",
                "s",
                "c"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.game.Stop");
    }
}
