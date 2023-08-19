package org.mmatsubara.controller;

import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class AbstractController {

    @Inject
    protected ModelMapper modelMapper;

    protected <D, T> List<D> convert(List<T> models, Class<D> clazz) {
        List<D> dtos = new ArrayList<>();
        for (T model : models) {
            dtos.add(modelMapper.map(model, clazz));
        }
        return dtos;
    }

    protected <D, T> D convert(T model, Class<D> clazz) {
        return modelMapper.map(model, clazz);
    }

}
