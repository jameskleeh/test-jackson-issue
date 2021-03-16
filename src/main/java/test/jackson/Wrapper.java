package test.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.beans.ConstructorProperties;
import java.util.List;

public class Wrapper {

    public List<String> getInner() {
        return inner;
    }

    public final List<String> inner;

    @ConstructorProperties({"inner" })
    @JsonCreator
    public Wrapper(List<String> inner) {
        this.inner = inner;
    }
}
