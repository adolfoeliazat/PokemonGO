package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public interface JsonArrayFormatVisitor extends JsonFormatVisitorWithSerializerProvider {

    public static class Base implements JsonArrayFormatVisitor {
        protected SerializerProvider _provider;

        public Base(SerializerProvider p) {
            this._provider = p;
        }

        public SerializerProvider getProvider() {
            return this._provider;
        }

        public void setProvider(SerializerProvider p) {
            this._provider = p;
        }

        public void itemsFormat(JsonFormatVisitable handler, JavaType elementType) throws JsonMappingException {
        }

        public void itemsFormat(JsonFormatTypes format) throws JsonMappingException {
        }
    }

    void itemsFormat(JsonFormatTypes jsonFormatTypes) throws JsonMappingException;

    void itemsFormat(JsonFormatVisitable jsonFormatVisitable, JavaType javaType) throws JsonMappingException;
}
