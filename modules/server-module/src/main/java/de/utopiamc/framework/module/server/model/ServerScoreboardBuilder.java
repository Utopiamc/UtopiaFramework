package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.service.ColorService;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.TempEventService;
import de.utopiamc.framework.api.ui.scoreboard.DynamicScoreboardLineBuilder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardBuilder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardHolder;
import de.utopiamc.framework.api.ui.scoreboard.StaticScoreboardLineBuilder;

import java.util.LinkedList;

public class ServerScoreboardBuilder implements ScoreboardBuilder {

    private static final Integer DEFAULT_SPACE_BETWEEN_LINES = 1;
    public static final String[] SPACES = { "§a§b", "§a§c", "§a§d", "§a§e", "§a§f", "§a§1", "§a§2", "§a§3", "§a§4", "§a§5", "§a§6", "§a§7", "§a§8", "§a§9", "§a§0" };
    public static final String[] VAR_KEYS = { "§b§a", "§b§c", "§b§d", "§b§e", "§b§f", "§b§1", "§b§2", "§b§3", "§b§4", "§b§5", "§b§6", "§b§7", "§b§8", "§b§9", "§b§0" };
    final ColorService colorService;
    final TempEventService eventService;

    final FrameworkPlayerService playerService;

    final UtopiaMetaConfig metaConfig;

    private String title;
    private Integer spaceBetweenLines = DEFAULT_SPACE_BETWEEN_LINES;
    private LinkedList<ServerScoreboardLineBuilder> lineBuilders;

    public ServerScoreboardBuilder(ColorService colorService, TempEventService eventService, FrameworkPlayerService playerService, UtopiaMetaConfig metaConfig) {
        this.colorService = colorService;
        this.eventService = eventService;
        this.playerService = playerService;
        this.metaConfig = metaConfig;

        this.lineBuilders = new LinkedList<>();
    }

    @Override
    public ScoreboardBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public ScoreboardBuilder shouldSpaceLines(Boolean value) {
        this.spaceBetweenLines = value ? DEFAULT_SPACE_BETWEEN_LINES : 0;
        return this;
    }

    @Override
    public ScoreboardBuilder shouldSpaceLines(Integer space) {
        this.spaceBetweenLines = space;
        return this;
    }

    @Override
    public StaticScoreboardLineBuilder addLine() {
        ServerStaticScoreboardLineBuilder builder = new ServerStaticScoreboardLineBuilder(this);
        lineBuilders.addLast(builder);
        return builder;
    }

    @Override
    public DynamicScoreboardLineBuilder addDynamicLine() {
        ServerDynamicScoreboardLineBuilder builder = new ServerDynamicScoreboardLineBuilder(this);
        lineBuilders.add(builder);
        return builder;
    }

    @Override
    public ScoreboardHolder build() {
        return new ServerScoreboardHolder(this);
    }

    public String getTitle() {
        return title != null ? colorService.translateColors(title) : colorService.translateColors(metaConfig.getTitle());
    }

    public Integer getSpaceBetweenLines() {
        return spaceBetweenLines;
    }

    public LinkedList<ServerScoreboardLineBuilder> getLineBuilders() {
        return lineBuilders;
    }
}
