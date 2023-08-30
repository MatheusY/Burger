package org.mmatsubara;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;

public abstract class AbstractTest {
    protected Faker faker;
    protected ObjectMapper mapper;

    protected ModelMapper modelMapper;

    public AbstractTest() {
        this.faker = new Faker();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.modelMapper = new ModelMapper();
    }
}
