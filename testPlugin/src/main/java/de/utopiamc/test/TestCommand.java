package de.utopiamc.test;

import com.google.inject.Inject;
import de.utopiamc.framework.api.commands.CommandConfig;
import de.utopiamc.framework.api.commands.descriptors.Configure;
import de.utopiamc.framework.api.commands.descriptors.MapRoute;
import de.utopiamc.framework.api.commands.descriptors.Variable;
import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.service.MessageService;
import de.utopiamc.framework.api.service.ScoreboardFactory;
import de.utopiamc.framework.api.stereotype.Command;

/**
 * /test kill Leon_lp9
 */
@Command("test")
public class TestCommand {

    private final ScoreboardFactory factory;

    @Inject
    public TestCommand(ScoreboardFactory factory) {
        this.factory = factory;
    }

    @Configure
    public void configure(CommandConfig config) {
        config.title("$p&lTest").prefix("$p&lTest &8• $r").primaryColor('6').secondaryColor('e');
    }

    @MapRoute("<Player>")
    public void deineMudda(MessageService messageService, @Variable("player")FrameworkPlayer player) {
        factory.createScoreboard().addLine().setTitle("$pTrix")
                .setContent("$sdu wurdest ausgetrixt.")
                .build()
                .bind(player);
    }

}
