package de.utopiamc.framework.api.config.meta;

import de.utopiamc.framework.api.config.UtopiaConfiguration;

public interface UtopiaMetaConfig extends UtopiaConfiguration {

    /**
     * This color is used (unless otherwise configured) in places like the prefix or the sidebar.
     * @param color A valid color. (All numbers from 0-9, as well as the letters a, b, c, d, e and f)
     * @return This instance (builder pattern)
     */
    UtopiaMetaConfig primaryColor(char color);

    /**
     * This color is used (unless otherwise configured) in places such as accents in messages.
     * @param color A valid color. (All numbers from 0-9, as well as the letters a, b, c, d, e and f)
     * @return This instance (builder pattern)
     */
    UtopiaMetaConfig secondaryColor(char color);

    /**
     * This color is used (unless otherwise configured) as the base color of a message.
     * @param color A valid color. (All numbers from 0-9, as well as the letters a, b, c, d, e and f)
     * @return This instance (builder pattern)
     */
    UtopiaMetaConfig textColor(char color);

    /**
     * This color is used for error messages.
     * @param color A valid color. (All numbers from 0-9, as well as the letters a, b, c, d, e and f)
     * @return This instance (builder pattern)
     */
    UtopiaMetaConfig errorColor(char color);


    /**
     * The prefix is appended before each message of the framework.
     * @param prefix The prefix should not be longer than 16 characters, color variables can be used ($p, $s, $e, $r).
     * @return This instance (builder pattern)
     */
    UtopiaMetaConfig prefix(String prefix);

    String getPrefix();
    char getPrimaryColor();
    char getSecondaryColor();
    char getTextColor();
    char getErrorColor();

}
