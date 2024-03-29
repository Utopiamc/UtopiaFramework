package de.utopiamc.framework.server.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.RequestService;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

public class ServerFrameworkPlayerService implements FrameworkPlayerService {

    private final RequestService requestService;

    private static final class ServerPlayerCache {
        private UUID uuid;
        private String name;
        private Date firstJoined;

        private ServerPlayerCache(UUID uuid, String name, Date firstJoined) {
            this.uuid = uuid;
            this.name = name;
            this.firstJoined = firstJoined;
        }

        public UUID uuid() {
            return uuid;
        }

        public String name() {
            return name;
        }

        public Date firstJoined() {
            return firstJoined;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (ServerPlayerCache) obj;
            return Objects.equals(this.uuid, that.uuid) &&
                    Objects.equals(this.name, that.name) &&
                    Objects.equals(this.firstJoined, that.firstJoined);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid, name, firstJoined);
        }

        @Override
        public String toString() {
            return "ServerPlayerCache[" +
                    "uuid=" + uuid + ", " +
                    "name=" + name + ", " +
                    "firstJoined=" + firstJoined + ']';
        }
    }

    private final Map<UUID, ServerPlayerCache> uuidCache;
    private final Map<String, ServerPlayerCache> nameCache;

    @Inject
    public ServerFrameworkPlayerService(RequestService requestService) {
        this.requestService = requestService;

        this.uuidCache = new HashMap<>();
        this.nameCache = new HashMap<>();
    }

    @Override
    public void cache(UUID uuid, String name, Date firstJoined) {
        ServerPlayerCache serverPlayerCache = new ServerPlayerCache(uuid, name, firstJoined);

        nameCache.put(name, serverPlayerCache);
        uuidCache.put(uuid, serverPlayerCache);
    }

    @Override
    public FrameworkPlayer get(UUID uuid) {
        loadCacheIfNotPresent(uuid);

        ServerPlayerCache cache = uuidCache.get(uuid);
        return new ServerFrameworkPlayer(cache.uuid, cache.name, cache.firstJoined, Bukkit.getPlayer(uuid));
    }

    @Override
    public FrameworkPlayer get(String name) {
        loadCacheIfNotPresent(name);

        ServerPlayerCache cache = nameCache.get(name);
        return new ServerFrameworkPlayer(cache.uuid, cache.name, cache.firstJoined, Bukkit.getPlayer(name));
    }

    private void loadCacheIfNotPresent(UUID uuid) {
        if (uuidCache.containsKey(uuid))
            return;

        try {
            ServerPlayerCache newCache = requestService.get("/player")
                    .queryParam("uuid", uuid.toString())
                    .execute(ServerPlayerCache.class);
            uuidCache.put(uuid, newCache);
            nameCache.put(newCache.name, newCache);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCacheIfNotPresent(String name) {
        if (nameCache.containsKey(name))
            return;

        try {
            ServerPlayerCache newCache = requestService.get("/player")
                    .queryParam("name", name)
                    .execute(ServerPlayerCache.class);
            uuidCache.put(newCache.uuid, newCache);
            nameCache.put(name, newCache);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
