package normalize;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class NormalizeStringDeserializer extends JsonDeserializer<String> {

    private final Normalize normalize;

    public NormalizeStringDeserializer(Normalize normalize) {
        this.normalize = normalize;
    }

    @Override
    public String deserialize(JsonParser p,
        DeserializationContext ctxt)
        throws IOException {

        String value = p.getValueAsString();

        return StringNormalizer.normalize(
            value,
            normalize.type(),
            NormalizeConfig.from(normalize)
        );
    }
}
