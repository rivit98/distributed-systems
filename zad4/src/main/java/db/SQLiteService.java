package db;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class SQLiteService {
    public static final String JDBC_ADDRESS = "jdbc:sqlite:astra-link.db";
    private Connection connection;

    public SQLiteService() {
        connect();
        createTable();
        initStats(100, 200);
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC_ADDRESS);
            log.info("Connected to DB");
        } catch (Exception e) {
            log.error("Error during database initialization: " + e.getMessage());
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("DB closing failed " + e.getMessage());
            } finally {
                connection = null;
            }
            log.info("DB connection closed");
        }
    }

    private void createTable() {
        try {
            simpleQuery("DROP TABLE IF EXISTS satellite_stats;");
            simpleQuery("CREATE TABLE IF NOT EXISTS satellite_stats (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "errorCount INTEGER DEFAULT 0" +
                    ");");

            log.info("Table created");
        } catch (SQLException e) {
            log.error("Error during creating tables: " + e.getMessage());
            throw new RuntimeException("Cannot create tables");
        }
    }

    public void simpleQuery(String sql) throws SQLException {
        connection.prepareStatement(sql).execute();
    }

    private void initStats(int start, int end) {
        try {
            var prep = connection.prepareStatement("INSERT INTO satellite_stats (id) VALUES(?);");
            IntStream.range(start, end)
                    .forEach(id -> {
                        try {
                            prep.setInt(1, id);
                            prep.addBatch();
                        } catch (SQLException throwables) {
                            log.error("initStats - " + prep);
                        }
                    });

            prep.executeBatch();
            log.info("Table initialized");
        } catch (SQLException e) {
            log.error("Error during creating tables: " + e.getMessage());
        }
    }

    public void updateMany(List<Integer> errors) {
        if (errors.isEmpty()) {
            return;
        }

        try {
            var prep = connection.prepareStatement("UPDATE satellite_stats SET errorCount=errorCount+1 WHERE id=?");
            errors
                    .forEach(id -> {
                        try {
                            prep.setInt(1, id);
                            prep.addBatch();
                        } catch (SQLException throwables) {
                            log.error("updateStats - " + prep);
                        }
                    });

            var res = prep.executeBatch();
            log.info(res.length + " rows updated");
        } catch (SQLException e) {
            log.error("Error during creating tables: " + e.getMessage());
        }
    }

    public int getErrorCount(int satelliteID) {
        var errorCount = 0;
        try {
            var prep = connection.prepareStatement("SELECT errorCount from satellite_stats WHERE id=?");
            prep.setInt(1, satelliteID);

            try (var resultSet = prep.executeQuery()) {
                resultSet.next();
                errorCount = resultSet.getInt("errorCount");
            }
        } catch (SQLException e) {
            log.error("Get errors [" + satelliteID + "] failed " + e.getMessage());
        }

        return errorCount;
    }
}
