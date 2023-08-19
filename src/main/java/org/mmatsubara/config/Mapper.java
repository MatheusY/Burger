package org.mmatsubara.config;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Dependent
public class Mapper {

    @Produces
    @Singleton
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
