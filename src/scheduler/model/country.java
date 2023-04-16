package scheduler.model;

/***
 * The country class is the model for each country in the application.  It is generally used to get country names and IDs.
 */
public class country {

    private int countryId;
    private String country;

    public country(int countryId, String country){
        this.country = country;
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry(){
        return country;
    }

    public void setCountryId(){
        this.countryId = countryId;
    }

    public void setCountry(){
        this.country = country;
    }
}
