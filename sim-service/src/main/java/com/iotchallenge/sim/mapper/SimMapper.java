package com.iotchallenge.sim.mapper;

import com.iotchallenge.sim.dto.SimDto;
import com.iotchallenge.sim.model.Sim;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SimMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(SimDto dto, @MappingTarget Sim entity);
}