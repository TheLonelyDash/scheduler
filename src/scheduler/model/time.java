package scheduler.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class time {

    /***
     * This method converts the local time into the office official time of Eastern Standard Time.
     * @param time Is the time passed for conversion
     * @return Is the EST version of the time passed in as an argument
     */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }


}
