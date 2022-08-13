package de.utopiamc.framework.game.teams;


import org.bukkit.ChatColor;

public enum TeamColor {
    RED("Rot", ChatColor.RED),
    GREEN("Grün", ChatColor.GREEN),
    BLUE("Blau", ChatColor.BLUE),
    YELLOW("Gelb", ChatColor.YELLOW),
    BLACK("Schwarz", ChatColor.BLACK),
    DARK_BLUE("Dunkelblau", ChatColor.DARK_BLUE),
    DARK_GREEN("Dunkelgrün", ChatColor.DARK_GREEN),
    DARK_AQUA("Türkis", ChatColor.DARK_AQUA),
    DARK_RED("Dunkelrot", ChatColor.DARK_RED),
    DARK_PURPLE("Lila", ChatColor.DARK_PURPLE),
    DARK_GRAY("Dunkelgrau", ChatColor.DARK_GRAY),
    ORANGE("Orange", ChatColor.GOLD),
    GRAY("Grau", ChatColor.GRAY),
    AQUA("Aqua", ChatColor.AQUA),
    PINK("Pink", ChatColor.LIGHT_PURPLE),
    WHITE("Weiß", ChatColor.WHITE);

    final String name;
    final ChatColor color;

    TeamColor(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return color + name;
    }
}
