package normalize;

import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class NormalizeAutoConfiguration {
    @Bean
    public Module normalizeModule() {
        return new NormalizeModule();
    }
}
