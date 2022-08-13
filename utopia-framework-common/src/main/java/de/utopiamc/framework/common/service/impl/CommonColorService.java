package de.utopiamc.framework.common.service.impl;

import com.google.inject.Inject;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.service.ColorService;

public class CommonColorService implements ColorService {

    private static final String VALID_COLOR_CHARS = "psre";

    private static final char COLOR_SYMBOL = '$';

    private final UtopiaMetaConfig metaConfig;

    @Inject
    public CommonColorService(UtopiaMetaConfig metaConfig) {
        this.metaConfig = metaConfig;
    }

    @Override
    public String translateColors(String string) {
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == COLOR_SYMBOL) {
                char key = Character.toLowerCase(chars[i + 1]);
                if (VALID_COLOR_CHARS.contains(String.valueOf(key))) {
                    chars[i] = '§';
                    if (key == 'p')
                        chars[i+1] = metaConfig.getPrimaryColor();
                    else if (key == 's')
                        chars[i+1] = metaConfig.getSecondaryColor();
                    else if (key == 'e')
                        chars[i+1] = metaConfig.getErrorColor();
                    else
                        chars[i+1] = metaConfig.getTextColor();
                }
            }
        }

        return new String(chars).replaceAll("&", "§");
    }
}
