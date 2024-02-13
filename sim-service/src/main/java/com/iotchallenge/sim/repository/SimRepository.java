package com.iotchallenge.sim.repository;

import com.iotchallenge.sim.model.Sim;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimRepository extends ListCrudRepository<Sim, Long>, PagingAndSortingRepository<Sim, Long> {

    Optional<Sim> findSimBySimId(String simId);
}
