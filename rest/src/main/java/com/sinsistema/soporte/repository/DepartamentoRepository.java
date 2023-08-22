package com.sinsistema.soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sinsistema.soporte.model.Departamento;

public interface DepartamentoRepository extends  JpaRepository<Departamento, Long> {
	
	
	
	
	
}
