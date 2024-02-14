package com.iotchallenge.sim.controller;

import com.iotchallenge.sim.dto.SimDto;
import com.iotchallenge.sim.mapper.SimMapper;
import com.iotchallenge.sim.model.Sim;
import com.iotchallenge.sim.repository.SimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/sims")
public class SimController {

    @Autowired
    private SimRepository repository;

    @Autowired
    private SimMapper mapper;

    @GetMapping("")
    public Page<SimDto> findAllSims(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @PostMapping("")
    public SimDto create(@RequestBody SimDto dto) {

        Sim sim = new Sim(dto.simId(), dto.country(), dto.active());
        repository.save(sim);
        return new SimDto(sim.isActive(), sim.getCountry(), sim.getSimId());
    }

    @GetMapping(value = "/{simId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SimDto get(@PathVariable("simId") String simId) {

        Sim sim = repository.findSimBySimId(simId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Sim not found"));
        return new SimDto(sim.isActive(), sim.getCountry(), sim.getSimId());
    }

    @PatchMapping("/{simId}")
    public SimDto update(@PathVariable("simId") String simId, @RequestBody SimDto dto) {

        Sim sim = repository.findSimBySimId(simId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Sim not found"));
        mapper.update(dto, sim);
        return new SimDto(sim.isActive(), sim.getCountry(), sim.getSimId());
    }

}
