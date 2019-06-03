package com.ftn.nc.NCBackend.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Pretplata;

public interface PretplataRepository extends JpaRepository<Pretplata, Long> {

	List<Pretplata> findByAutorAndCasopis(Autor autor, Casopis casopis);
	
}
