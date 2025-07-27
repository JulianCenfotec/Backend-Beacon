package cr.ac.ucenfotec.waddle.beacon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;

public class GrantedAuthorityDeserializer extends JsonDeserializer<GrantedAuthority> {

    @Override
    public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String authority = p.getValueAsString();
        return new SimpleGrantedAuthority(authority);
    }
}