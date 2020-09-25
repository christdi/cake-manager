package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.model.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CakeRepository extends JpaRepository<CakeModel, UUID> {
}
