package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardLineUpdater;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerScoreboardLineUpdater implements ScoreboardLineUpdater {

    private final Map<UUID, Team> player;
    private final Updater updater;

    private OnBound onBound;

    public ServerScoreboardLineUpdater(Updater updater) {
        this.updater = updater;
        this.player = new HashMap<>();
    }

    public void put(UUID uuid, Team team) {
        player.put(uuid, team);
    }

    public void remove(UUID uuid) {
        player.remove(uuid);
    }

    @Override
    public void update(String value) {
        if (value == null)
            throw new IllegalArgumentException("Value must not be null.");

        player.forEach((p, t) -> updater.updateTeam(value, t));
    }

    @Override
    public void update(String value, UUID target) {
        if (value == null)
            throw new IllegalArgumentException("Value must not be null.");
        if (target == null)
            throw new IllegalArgumentException("Value must not be null.");

        Team team = player.get(target);
        if (team != null)
            updater.updateTeam(value, team);
    }

    @Override
    public void onBound(OnBound onBound) {
        this.onBound = onBound;
    }

    public String onBound(ServerFrameworkPlayer player) {
        return onBound.handle(player);
    }

    public interface Updater {

        void updateTeam(String value, Team team);

    }

}
