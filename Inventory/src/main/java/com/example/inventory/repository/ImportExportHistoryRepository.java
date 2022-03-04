package com.example.inventory.repository;

import com.example.inventory.entity.ImportExportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportExportHistoryRepository extends JpaRepository<ImportExportHistory,Long>, JpaSpecificationExecutor<ImportExportHistory> {
}
