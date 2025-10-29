package com.ashvin.nframework.common;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.google.gson.stream.*;
import java.io.IOException;

public class EnumTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) return null;

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                out.value(value == null ? null : ((Enum<?>) value).name());
            }

            @Override
            public T read(JsonReader in) throws IOException {
                String value = in.nextString();
                for (T constant : rawType.getEnumConstants()) {
                    if (((Enum<?>) constant).name().equals(value)) return constant;
                }
                return null;
            }
        };
    }
}

