package scheduler.model;

/***
 * The division class is the model for the applications divisions.  It is generally used for it's getter of division ID.
 */
public class division {

    private int divisionId;
    private String division;
    private int countryId;

    public division (int divisionId, String division, int countryId){
        this.countryId = countryId;
        this.division = division;
        this.divisionId = divisionId;
    }

    public int getDivisionId(){
        return divisionId;
    }

    public String getDivision(){
        return division;
    }

    public int getCountryId(){
        return countryId;
    }

    public void setCountryId(){
        this.countryId = countryId;
    }

    public void setDivisionId(){
        this.divisionId = divisionId;
    }

    public void setDivision(){
        this.division = division;
    }

}
