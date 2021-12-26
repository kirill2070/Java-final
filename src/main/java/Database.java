import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Connection connection;
    private static Statement statement;
    private static Map<String, Double> generosity;

    public static void init(ArrayList<Country> countries) throws SQLException {
        connect();

        createTableCountry();
        createTablePeople();
//        countries.forEach(c -> {
//            try {
//                putDataIntoCountry(c);
//                putDataIntoPeople(c);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
        getGenerosityByCountry();
        EventQueue.invokeLater(() -> {
            Graphics g = new Graphics(generosity);
            g.setVisible(true);
        });
        System.out.println("\nСтрана с самым низким показателем щедрости:\n" + countryWithLowGenerosity());
        System.out.println("\nСтрана с самыми средними показателями:\n" + getAverageCountry());
        disconnect();
    }

    private static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/happy.db");
        statement = connection.createStatement();
    }

    private static void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

    private static void getGenerosityByCountry() throws SQLException {
        generosity = new HashMap<>();
        String sql =
                "SELECT country, generosity " +
                "FROM People " +
                "GROUP BY country;";

        ResultSet res = statement.executeQuery(sql);
        while(res.next()) {
            generosity.put(res.getString("country"), Double.parseDouble(res.getString("generosity")));
        }
    }

    private static String countryWithLowGenerosity() throws SQLException {
        return statement.executeQuery(
                "SELECT C.country, min(generosity) " +
                "FROM People " +
                "         JOIN Country C on People.country = C.country " +
                "WHERE region IN ('Middle East and Northern Africa', 'Central and Eastern Europe');"
        ).getString("country");
    }

    private static String getAverageCountry() throws SQLException {
        return statement.executeQuery(
                "SELECT country, AVG(avgCountry) " +
                        "FROM (SELECT country, " +
                        "             (AVG(happinessRank) + " +
                        "              AVG(happinessScore) + " +
                        "              AVG(upper) + " +
                        "              AVG(lower) + " +
                        "              AVG(economy) + " +
                        "              AVG(family) + " +
                        "              AVG(health) + " +
                        "              AVG(freedom) + " +
                        "              AVG(generosity) + " +
                        "              AVG(trust) + " +
                        "              AVG(dystopia)) / 11.0 AS avgCountry " +
                        "      FROM (SELECT * " +
                        "            FROM Country C " +
                        "                     JOIN People P ON C.country = P.country " +
                        "            WHERE region IN ('Southeastern Asia', 'Sub-Saharan Africa')) " +
                        "      GROUP BY country);"
        ).getString("country");
    }

    private static void createTableCountry() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS Country (" +
                "country TEXT PRIMARY KEY, " +
                "region TEXT, " +
                "happinessRank INTEGER, " +
                "happinessScore FLOAT, " +
                "lower FLOAT, " +
                "upper FLOAT, " +
                "economy FLOAT, " +
                "dystopia FLOAT);"
        );
    }

    private static void createTablePeople() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS People (" +
                "country TEXT, " +
                "family FLOAT, " +
                "health FLOAT, " +
                "freedom FLOAT, " +
                "trust FLOAT, " +
                "generosity FLOAT);"
        );
    }

    private static void putDataIntoCountry(Country country) throws SQLException {
        String query = String.format(
                "INSERT INTO Country (" +
                "country, region, happinessRank, happinessScore, lower, upper, economy, dystopia) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                country.getCountry(),
                country.getRegion(),
                country.getHappinesRank(),
                country.getHappinesScore(),
                country.getLower(),
                country.getUpper(),
                country.getEconomy(),
                country.getDystopia()
        );

        statement.execute(query);
    }

    private static void putDataIntoPeople(Country country) throws SQLException {
        String query = String.format(
                "INSERT INTO People (" +
                "country, family, health, freedom, trust, generosity) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                country.getCountry(),
                country.getFamily(),
                country.getHealth(),
                country.getFreedom(),
                country.getTrust(),
                country.getGenerosity()
        );

        statement.execute(query);
    }
}
