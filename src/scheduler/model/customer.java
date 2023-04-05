package scheduler.model;

public class customer {

    private int customerID;
    private String customerName;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerCountry;
    //private String customerStateProvince;
    private int customerDivisionID;

    public customer(int customerID,
                    String customerName,
                    String customerPostalCode,
                    String customerPhoneNumber,
                    String customerAddress,
                    String customerCountry,
                    //String customerStateProvince,
                    int customerDivisionID
    ){
        this.customerAddress = customerAddress;
        this.customerCountry = customerCountry;
        this.customerDivisionID = customerDivisionID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerPostalCode = customerPostalCode;
        //this.customerStateProvince = customerStateProvince;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public void setCustomerID(){
        this.customerID = customerID;
    }

    public void setCustomerName(){
        this.customerName = customerName;
    }

    public void setCustomerPostalCode(){
        this.customerPostalCode = customerPostalCode;
    }

    public void setCustomerAddress(){
        this.customerAddress = customerAddress;
    }

    public void setCustomerPhoneNumber(){
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public void setCustomerCountry(){
        this.customerCountry = customerCountry;
    }

    /*
    public void setCustomerStateProvince(){
        this.customerStateProvince = customerStateProvince;
    }
    */

    public void setCustomerDivisionID(){
        this.customerDivisionID = customerDivisionID;
    }



    public Integer getCustomerID(){
        return customerID;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerPostalCode(){
        return customerPostalCode;
    }

    public String getCustomerPhoneNumber(){
        return customerPhoneNumber;
    }

    public String getCustomerAddress(){
        return customerAddress;
    }

    public String getCustomerCOuntry(){
        return customerCountry;
    }

    public int getCustomerDivisionID(){
       return customerDivisionID;
    }


}
