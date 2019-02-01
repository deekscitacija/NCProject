package com.ftn.nc.NCBackend.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Recenzent;

public interface RecenzentRepository extends JpaRepository<Recenzent, Long>{

	List<Recenzent> findByCasopisiContaining(Casopis casopis);
	
}
