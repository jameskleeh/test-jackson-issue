package test.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new MyModule());
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try {
            Outer outer = objectMapper.readValue("{\"wrapper\":{\"inner\":[]}}", Outer.class);
            System.out.println(outer.getWrapper().getInner());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
