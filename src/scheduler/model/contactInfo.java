package scheduler.model;

/***
 * The contactInfo class is generally used as a model for the applications contacts.  It is useful for it's getters
 * of contact ID and contact name.
 */
public class contactInfo {
    private int contact_ID;
    private String contactName;
    private String contactEmail;

    public contactInfo (int contact_ID, String contactName, String contactEmail){
        this.contact_ID = contact_ID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContact_ID(){
        return contact_ID;
    }

    public String getContactName(){
        return contactName;
    }

    public String getContactEmail(){
        return contactEmail;
    }

    public void setContact_ID(){
        this.contact_ID = contact_ID;
    }

    public void setContactName(){
        this.contactName = contactName;
    }

    public void setContactEmail(){
        this.contactEmail = contactEmail;
    }


}
