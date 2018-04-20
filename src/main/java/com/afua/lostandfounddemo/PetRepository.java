package com.afua.lostandfounddemo;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<LostPet,Long> {
    Iterable <LostPet> findAllByLost(boolean lost);
}
