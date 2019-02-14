package com.ftn.nc.NCBackend.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Koautor;

public interface KoautorRepository extends JpaRepository<Koautor, Long> {

	public List<Koautor> findByEmail(String email);
}
