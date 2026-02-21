package normalize;


import com.fasterxml.jackson.databind.module.SimpleModule;

public class NormalizeModule extends SimpleModule {
    public NormalizeModule() {
        super("NormalizeModule");
        setDeserializerModifier(new NormalizeBeanDeserializerModifier());
    }
}
