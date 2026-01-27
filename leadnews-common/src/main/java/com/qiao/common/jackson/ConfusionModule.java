package com.qiao.common.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfusionModule extends Module {

    public final static String MODULE_NAME = "jackson-confusion-encryption";
    public final static Version VERSION = new Version(1,0,0,null,"heima",MODULE_NAME);

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public Version version() {
        return VERSION;
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addBeanSerializerModifier(new ConfusionSerializerModifier());
        context.addBeanDeserializerModifier(new ConfusionDeserializerModifier());
    }

    /**
     * Register the confusion module to ObjectMapper
     * Note: Different naming strategies:
     * - CamelCase: personId -> personId
     * - PascalCase: personId -> PersonId
     * - SnakeCase: personId -> person_id
     * - KebabCase: personId -> person-id
     *
     * @param objectMapper ObjectMapper instance to configure
     * @return Configured ObjectMapper instance
     */
    public static ObjectMapper registerModule(ObjectMapper objectMapper){
        // Ignore unknown properties during deserialization
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper.registerModule(new ConfusionModule());
    }

}
