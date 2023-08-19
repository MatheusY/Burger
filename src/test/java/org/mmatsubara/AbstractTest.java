package org.mmatsubara;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public abstract class AbstractTest {
    protected Faker faker;
    protected ObjectMapper mapper;

    public AbstractTest() {
        this.faker = new Faker();
        this.mapper = new ObjectMapper();
    }
}
