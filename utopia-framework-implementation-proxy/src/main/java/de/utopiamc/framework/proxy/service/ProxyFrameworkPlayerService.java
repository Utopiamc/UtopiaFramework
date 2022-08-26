package de.utopiamc.framework.proxy.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.entity.ProxyFrameworkPlayer;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.proxy.plugin.UtopiaFrameworkPlugin;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProxyFrameworkPlayerService implements FrameworkPlayerService {

    private final RequestService requestService;

    private record ProxyPlayerCache(UUID uuid, String name, Date firstJoined) {}

    private final Map<UUID, ProxyPlayerCache> uuidCache;
    private final Map<String, ProxyPlayerCache> nameCache;

    @Inject
    public ProxyFrameworkPlayerService(RequestService requestService) {
        this.requestService = requestService;

        this.uuidCache = new HashMap<>();
        this.nameCache = new HashMap<>();
    }

    @Override
    public void cache(UUID uuid, String name, Date firstJoined) {
        ProxyPlayerCache serverPlayerCache = new ProxyPlayerCache(uuid, name, firstJoined);

        nameCache.put(name, serverPlayerCache);
        uuidCache.put(uuid, serverPlayerCache);
    }

    @Override
    public FrameworkPlayer get(UUID uuid) {
        loadCacheIfNotPresent(uuid);

        ProxyPlayerCache cache = uuidCache.get(uuid);
        return new ProxyFrameworkPlayer(cache.uuid, cache.name, cache.firstJoined, UtopiaFrameworkPlugin.getInstance().getProxy().getPlayer(uuid));
    }

    @Override
    public FrameworkPlayer get(String name) {
        loadCacheIfNotPresent(name);

        ProxyPlayerCache cache = nameCache.get(name);
        return new ProxyFrameworkPlayer(cache.uuid, cache.name, cache.firstJoined, UtopiaFrameworkPlugin.getInstance().getProxy().getPlayer(name));
    }

    private void loadCacheIfNotPresent(UUID uuid) {
        if (uuidCache.containsKey(uuid))
            return;

        try {
            ProxyPlayerCache newCache = requestService.get("/player")
                    .queryParam("uuid", uuid.toString())
                    .execute(ProxyPlayerCache.class);
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
            ProxyPlayerCache newCache = requestService.get("/player")
                    .queryParam("name", name)
                    .execute(ProxyPlayerCache.class);
            uuidCache.put(newCache.uuid, newCache);
            nameCache.put(name, newCache);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
