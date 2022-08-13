package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.model.TempEventSubscription;
import de.utopiamc.framework.api.model.VariableEventHandler;
import de.utopiamc.framework.api.ui.scoreboard.DynamicScoreboardLineBuilder;
import de.utopiamc.framework.module.server.IntegerFunction;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerDynamicScoreboardLineBuilder extends ServerScoreboardLineBuilder implements DynamicScoreboardLineBuilder {


    private String title;
    private String content;

    private final Map<String, ServerDynamicVariable> variables;
    int varKeyCount;

    public ServerDynamicScoreboardLineBuilder(ServerScoreboardBuilder builder) {
        super(builder);

        this.variables = new HashMap<>();
        this.varKeyCount = 0;
    }

    @Override
    public DynamicScoreboardLineBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public DynamicScoreboardLineBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public <E> DynamicScoreboardLineBuilder addDynamicVariable(String name, String defaultValue, Class<E> event, VariableEventHandler<E> handler) {
        return addDynamicVariable(name, () -> defaultValue, event, handler);
    }

    @Override
    public <E> DynamicScoreboardLineBuilder addDynamicVariable(String name, Supplier<String> defaultValue, Class<E> event, VariableEventHandler<E> handler) {
        variables.put(name, new ServerDynamicVariable(this, name, defaultValue, event, handler));
        return this;
    }

    @Override
    public Integer requiredLines() {
        int i = 0;
        if (title!=null)
            i++;
        if (content!=null)
            i++;
        return i;
    }

    @Override
    public void registerLine(IntegerFunction integerFunction, Objective objective, ServerFrameworkPlayer player) {
        registerLine(integerFunction, objective, player, title);
        registerLine(integerFunction, objective, player, content);
    }

    private void registerLine(IntegerFunction integerFunction, Objective objective, ServerFrameworkPlayer player, String value) {
        if (value != null) {
            Integer integer = integerFunction.get();

            Pattern pattern = Pattern.compile("%[a-zA-Z]+%");

            Matcher matcher = pattern.matcher(value);

            String v2 = matcher.replaceAll((result) -> {
                String string = result.group();
                String name = string.substring(1, string.length()-1);
                ServerDynamicVariable variable = variables.get(name);

                System.out.println(variable);

                if (variable == null)
                    return "";

                String key1 = variable.getKey();

                System.out.println(key1.replace('§', '/'));

                setupVariable(player, objective, variable);

                return key1;
            });

            System.out.println(v2.replace('§', '/'));

            String entry = builder.colorService.translateColors(v2);
            System.out.println(entry.replace('§', '/'));

            objective.getScore(entry).setScore(integer);
        }
    }

    private void setupVariable(ServerFrameworkPlayer player, Objective objective, ServerDynamicVariable<?> variable) {
        Scoreboard scoreboard = objective.getScoreboard();
        Team team = scoreboard.getTeam(variable.getTeamName());
        if (team == null)
            team = scoreboard.registerNewTeam(variable.getTeamName());

        team.addEntry(variable.getKey());

        setTeamValue(variable.getDefaultValue(), team);

        System.out.println(team);
        System.out.println(team.getEntries());
        System.out.println(team.getPrefix());

        final Team finalTeam = team;
        TempEventSubscription subscribe = builder.eventService.subscribe(variable.getEvent(), (event) -> {
            String handle = variable.getHandler().handle(player, event);
            setTeamValue(handle, finalTeam);
        });
    }

    private void setTeamValue(String v2, Team team) {
        v2 = builder.colorService.translateColors(v2);
        if (v2.length() > 64) {
            team.setPrefix(v2.substring(0, 63));
            team.setSuffix(v2.substring(64));
        }else {
            team.setPrefix(v2);
        }
    }
}
