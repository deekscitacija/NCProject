package com.ftn.nc.NCBackend.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Pretplata;

public interface PretplataRepository extends JpaRepository<Pretplata, Long> {

	//@Query("from Pretplata as p where p.autor = ?1 and p.casopis = ?2 and (p.datumOd <= ?3 and p.datumDo >= ?3)")
	List<Pretplata> findByAutorAndCasopis(Autor autor, Casopis casopis);
	
}
