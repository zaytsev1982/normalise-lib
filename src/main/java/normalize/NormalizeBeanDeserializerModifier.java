package normalize;


import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import java.util.List;

public class NormalizeBeanDeserializerModifier extends BeanDeserializerModifier {


    @Override
    public BeanDeserializerBuilder updateBuilder(
        DeserializationConfig config,
        BeanDescription beanDesc,
        BeanDeserializerBuilder builder) {

        List<BeanPropertyDefinition> properties =
            beanDesc.findProperties();

        for (BeanPropertyDefinition prop : properties) {

            if (prop.getField() != null &&
                prop.getField().getRawType() == String.class &&
                prop.getField().hasAnnotation(Normalize.class)) {

                Normalize normalize =
                    prop.getField().getAnnotation(Normalize.class);

                builder.addOrReplaceProperty(
                    builder.findProperty(PropertyName.construct(prop.getName()))
                        .withValueDeserializer(
                            new NormalizeStringDeserializer(normalize)
                        ),
                    true
                );
            }
        }

        return builder;
    }
}
