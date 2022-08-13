package de.utopiamc.framework.api.config.database;

public interface SpecificDatabaseConfig {

    /**
     * This has to be a jdbc url! It is used to establish a connection!
     * @param url A valid jdbc url
     * @return This instance (builder pattern)
     */
    SpecificDatabaseConfig url(String url);

    /**
     * The username used to authenticate with the given database. Leave empty when no authentication is used.
     * @param username Leave empty when no authentication is used.
     * @return This instance (builder pattern)
     */
    SpecificDatabaseConfig username(String username);

    /**
     * The password used to authenticate with the given database. Leave empty when no password is used.
     * @param password The password used to authenticate with the given database.
     * @return This instance (builder pattern)
     */
    SpecificDatabaseConfig password(String password);

    String getUrl();

    String getUsername();

    String getPassword();

    DatabaseConfig and();

}
