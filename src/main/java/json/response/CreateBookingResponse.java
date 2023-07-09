package json.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import json.request.CreateBookingBody;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookingResponse {
    private int bookingid;
    private CreateBookingBody booking;
}