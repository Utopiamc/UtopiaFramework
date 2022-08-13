package de.utopiamc.framework.api.config.database;

import de.utopiamc.framework.api.config.UtopiaConfiguration;

public interface DatabaseConfig extends UtopiaConfiguration {

    SpecificDatabaseConfig database(String databaseName);

}
