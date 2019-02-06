package com.ftn.nc.NCBackend.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;

public interface IzdanjeRepository extends JpaRepository<Izdanje, Long>{

	List<Izdanje> findByCasopis(Casopis casopis);
	
	Page<Izdanje> findByCasopis(Casopis casopis, Pageable pageable);
	
}
