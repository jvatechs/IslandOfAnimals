package readers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public interface Deserializable {
    ObjectMapper mapper = new ObjectMapper();
    void deserializeJSON() throws IOException;
}
