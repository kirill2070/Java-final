public class Country {
    private final String country;
    private final String region;
    private final int happinessRank;
    private final double happinessScore;
    private final double lower, upper;
    private final double economy;
    private final double family;
    private final double health;
    private final double freedom;
    private final double trust;
    private final double generosity;
    private final double dystopia;

    public Country(String[] data) {
        this.country = data[0];
        this.region = data[1];
        this.happinessRank = Integer.parseInt(data[2]);
        this.happinessScore = Double.parseDouble(data[3]);
        this.lower = Double.parseDouble(data[4]);
        this.upper = Double.parseDouble(data[5]);
        this.economy = Double.parseDouble(data[6]);
        this.family = Double.parseDouble(data[7]);
        this.health = Double.parseDouble(data[8]);
        this.freedom = Double.parseDouble(data[9]);
        this.trust = Double.parseDouble(data[10]);
        this.generosity = Double.parseDouble(data[11]);
        this.dystopia = Double.parseDouble(data[12]);
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public int getHappinesRank() {
        return happinessRank;
    }

    public double getHappinesScore() {
        return happinessScore;
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public double getEconomy() {
        return economy;
    }

    public double getFamily() {
        return family;
    }

    public double getHealth() {
        return health;
    }

    public double getFreedom() {
        return freedom;
    }

    public double getTrust() {
        return trust;
    }

    public double getGenerosity() {
        return generosity;
    }

    public double getDystopia() {
        return dystopia;
    }
}
