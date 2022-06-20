package de.utopiamc.framework.common.config;

import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;

public class UtopiaMetaConfigImpl implements UtopiaMetaConfig {

    private char primaryColor;
    private char secondaryColor;
    private char textColor;
    private char errorColor;
    private String prefix;

    @Override
    public UtopiaMetaConfig primaryColor(char color) {
        this.primaryColor = color;
        return this;
    }

    @Override
    public UtopiaMetaConfig secondaryColor(char color) {
        this.secondaryColor = color;
        return this;
    }

    @Override
    public UtopiaMetaConfig textColor(char color) {
        this.textColor = color;
        return this;
    }

    @Override
    public UtopiaMetaConfig errorColor(char color) {
        this.errorColor = color;
        return this;
    }

    @Override
    public UtopiaMetaConfig prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public char getPrimaryColor() {
        return primaryColor;
    }

    @Override
    public char getSecondaryColor() {
        return secondaryColor;
    }

    @Override
    public char getTextColor() {
        return textColor;
    }

    @Override
    public char getErrorColor() {
        return errorColor;
    }

}
