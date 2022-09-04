package de.utopiamc.framework.api.events.player;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.events.BukkitEvent;
import de.utopiamc.framework.api.events.player.qualifier.Player;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.OneToOneInjectableCandidate;

public class FrameworkPlayerEvent extends BukkitEvent {

    private final ServerFrameworkPlayer frameworkPlayer;

    public FrameworkPlayerEvent(org.bukkit.event.player.PlayerEvent event, ServerFrameworkPlayer player) {
        super(event);

        this.frameworkPlayer = player;
    }

    @Override
    public void addInjectableCandidates(CandidateQueue queue) {
        super.addInjectableCandidates(queue);
        queue.addFirst(new OneToOneInjectableCandidate(Player.class, frameworkPlayer));
    }

    public ServerFrameworkPlayer getFrameworkPlayer() {
        return frameworkPlayer;
    }
}
