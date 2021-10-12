package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Huyen;
import com.globits.da.dto.HuyenDto;

@Repository
public interface HuyenRepository extends JpaRepository<Huyen, UUID> {

	@Query("select new com.globits.da.dto.HuyenDto(h) from Huyen h")
	Page<HuyenDto> getListPage(Pageable pageable);

	@Query("select new com.globits.da.dto.HuyenDto(h) from Huyen h")
	List<HuyenDto> getAllHuyen();

	@Query("SELECT new com.globits.da.dto.HuyenDto(h) from Huyen h where h.id = ?1 ")
	String getHuyen(UUID id);
}
