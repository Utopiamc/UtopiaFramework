package de.utopiamc.framework.module.server.services;

import com.google.inject.Inject;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.service.ColorService;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.ScoreboardFactory;
import de.utopiamc.framework.api.service.TempEventService;
import de.utopiamc.framework.api.stereotype.Service;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardBuilder;
import de.utopiamc.framework.module.server.model.ServerScoreboardBuilder;

@Service
public class ServerScoreboardFactory implements ScoreboardFactory {

    private final ColorService colorService;
    private final TempEventService eventService;
    private final FrameworkPlayerService playerService;

    private final UtopiaMetaConfig metaConfig;

    @Inject
    public ServerScoreboardFactory(ColorService colorService, TempEventService eventService, FrameworkPlayerService playerService, UtopiaMetaConfig metaConfig) {
        this.colorService = colorService;
        this.eventService = eventService;
        this.playerService = playerService;
        this.metaConfig = metaConfig;
    }

    @Override
    public ScoreboardBuilder createScoreboard() {
        return new ServerScoreboardBuilder(colorService, eventService, playerService, metaConfig);
    }

}
