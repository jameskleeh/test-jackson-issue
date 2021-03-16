package test.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MyModule extends SimpleModule {

    public MyModule() {
        setDeserializerModifier(new MyDeserializerModifier());
    }

    private class MyDeserializerModifier extends BeanDeserializerModifier {

        @Override
        public BeanDeserializerBuilder updateBuilder(
                DeserializationConfig config,
                BeanDescription beanDesc,
                BeanDeserializerBuilder builder) {

            if (builder.getValueInstantiator().getDelegateType(config) != null) {
                return builder;
            }

            final Class<?> beanClass = beanDesc.getBeanClass();

            final TypeFactory typeFactory = config.getTypeFactory();
            ValueInstantiator defaultInstantiator = builder.getValueInstantiator();
            builder.setValueInstantiator(new StdValueInstantiator(config, typeFactory.constructType(beanClass)) {
                    @Override
                    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
                        return defaultInstantiator.getFromObjectArguments(config);
                    }

                    @Override
                    public boolean canInstantiate() {
                        return true;
                    }

                    @Override
                    public boolean canCreateUsingDefault() {
                        return false;
                    }

                    @Override
                    public boolean canCreateFromObjectWith() {
                        return true;
                    }

                    @Override
                    public boolean canCreateUsingArrayDelegate() {
                        return beanClass == Wrapper.class;
                    }

                    @Override
                    public boolean canCreateUsingDelegate() {
                        return true;
                    }


                    @Override
                    public JavaType getDelegateType(DeserializationConfig config) {
                        if (beanClass == Outer.class) {
                            return typeFactory.constructSimpleType(Wrapper.class, new JavaType[0]);
                        } else {
                            return typeFactory.constructCollectionType(List.class, String.class);
                        }
                    }

                    @Override
                    public JavaType getArrayDelegateType(DeserializationConfig config) {
                        return typeFactory.constructCollectionType(List.class, String.class);
                    }

                    @Override
                    public boolean canCreateFromString() {
                        return false;
                    }

                    @Override
                    public boolean canCreateFromInt() {
                        return false;
                    }

                    @Override
                    public boolean canCreateFromLong() {
                        return false;
                    }

                    @Override
                    public boolean canCreateFromDouble() {
                        return false;
                    }

                    @Override
                    public boolean canCreateFromBoolean() {
                        return false;
                    }

                    @Override
                    public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) throws IOException {
                        try {
                            return beanClass.getDeclaredConstructors()[0].newInstance(args);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException("failed to instantiate", e);
                        }
                    }

                    @Override
                    public Object createUsingDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
                        return createFromObjectWith(ctxt, new Object[] { delegate });
                    }

                    @Override
                    public Object createUsingArrayDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
                        return createFromObjectWith(ctxt, new Object[] { delegate });
                    }

                    @Override
                    public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object createFromInt(DeserializationContext ctxt, int value) throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object createFromLong(DeserializationContext ctxt, long value) throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object createFromDouble(DeserializationContext ctxt, double value) throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object createFromBoolean(DeserializationContext ctxt, boolean value) throws IOException {
                        throw new UnsupportedOperationException();
                    }

                });
            return builder;
        }
    }

}
