package com.example.inventory.service.importExportHistory;

import com.example.inventory.dto.ImportExportHistoryDto;
import com.example.inventory.entity.ImportExportHistory;
import com.example.inventory.specification.ParamField;
import org.springframework.data.domain.Page;

public interface importExportHistoryService {
    Page<ImportExportHistory> getImportExportHistories(ParamField field);
    ImportExportHistoryDto save(ImportExportHistory importExportHistory);
    ImportExportHistoryDto getImportExportHistory(Long id);
    ImportExportHistoryDto edit(Long id,ImportExportHistory importExportHistory);
    String delete(Long importExportHistoryID);
}
