package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Tinh;
import com.globits.da.dto.TinhDto;

@Repository
public interface TinhRepository extends JpaRepository<Tinh, UUID> {

	@Query("select new com.globits.da.dto.TinhDto(ti) from Tinh ti")
	Page<TinhDto> getListPage(Pageable pageable);

	@Query("select new com.globits.da.dto.TinhDto(ti) from Tinh ti")
	List<TinhDto> getAllTinh();

	@Query("SELECT t.name FROM Tinh t where t.id = ?1 ")
	String getTinh(UUID id);
}
