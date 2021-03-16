package test.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.beans.ConstructorProperties;

public class Outer {

    public Wrapper getWrapper() {
        return wrapper;
    }

    public final Wrapper wrapper;

    @ConstructorProperties({"wrapper"})
    @JsonCreator
    public Outer(Wrapper wrapper) {
        this.wrapper = wrapper;
    }
}
