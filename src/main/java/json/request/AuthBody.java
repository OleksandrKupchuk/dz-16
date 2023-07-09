package json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthBody {
    private String username;
    private String password;
}