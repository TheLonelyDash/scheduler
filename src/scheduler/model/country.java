package scheduler.model;

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
