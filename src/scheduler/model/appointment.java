package scheduler.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/***
 * The appointment class is the model for all appointment objects.
 */
public class appointment {
    private int user_ID;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalDateTime startTime;
    private LocalDate endDate;
    private LocalDateTime endTime;
    private int customer_ID;
    private int contact_ID;
    private String contactName;
    private String title;
    private int appointment_ID;

    public appointment (int appointment_ID, String title, String description, String location, String type, LocalDate startDate, LocalDateTime startTime, LocalDate endDate, LocalDateTime endTime, int customer_ID, int user_ID, int contact_ID, String contactName){
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        this.contactName = contactName;
    }

    public int getUser_ID(){
        return user_ID;
    }

    public int getAppointment_ID(){
        return appointment_ID;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public String getType(){
        return type;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public int getCustomer_ID(){
        return customer_ID;
    }

    public int getContact_ID(){
        return contact_ID;
    }

    public String getContactName(){
        return contactName;
    }


    public void setDescription(){
        this.description = description;
    }

    public void setLocation(){
        this.location = location;
    }

    public void setType(){
        this.type = type;
    }

    public void setStartDate(){
        this.startDate = startDate;
    }

    public void setStartTime(){
        this.startTime = startTime;
    }

    public void setEndDate(){
        this.endDate = endDate;
    }

    public void setEndTime(){
        this.endTime = endTime;
    }

    public void setCustomer_ID(){
        this.customer_ID = customer_ID;
    }

    public void setContact_ID(){
        this.contact_ID = contact_ID;
    }

    public void setContactName(){
        this.contactName = contactName;
    }

    public void setTitle(){
        this.title = title;
    }

    public void setAppointment_ID(){
        this.appointment_ID = appointment_ID;
    }

    public void setUser_ID(){
        this.user_ID = user_ID;
    }
}
