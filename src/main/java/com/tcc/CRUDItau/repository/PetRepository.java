package com.tcc.CRUDItau.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.tcc.CRUDItau.model.Pet;
import com.tcc.CRUDItau.model.User;

public interface PetRepository extends CrudRepository<Pet, Long>,PetRepositoryCustom {
	public Pet findByUser(User user);
	public List<Pet> findAll(); 
	public List<Pet> findByLatitudeAndLongitude(Long Latitude, Long Longitude);
	public List<Pet> findByRemove(boolean remove); 
}
