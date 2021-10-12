package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Xa;
import com.globits.da.dto.XaDto;

@Repository
public interface XaRepository extends JpaRepository<Xa, UUID> {

	@Query("select new com.globits.da.dto.XaDto(x) from Xa x")
	Page<XaDto> getListPage(Pageable pageable);

	@Query("select new com.globits.da.dto.XaDto(x) from Xa x")
	List<XaDto> getAllXa();

	@Query("SELECT new com.globits.da.dto.XaDto(x) from Xa x where x.id = ?1 ")
	String getXa(UUID id);

}
