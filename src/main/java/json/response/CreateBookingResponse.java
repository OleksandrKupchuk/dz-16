package json.response;

import json.request.CreateBookingBody;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookingResponse {
    private int bookingid;
    private CreateBookingBody booking;
}