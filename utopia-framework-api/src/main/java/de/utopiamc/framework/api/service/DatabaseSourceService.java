package de.utopiamc.framework.api.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseSourceService {

    Connection createConnection() throws SQLException;

}
