package com.example.inventory.mappers;

import com.example.inventory.dto.ImportExportHistoryDto;
import com.example.inventory.entity.ImportExportHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImportExportHistoryMapper {
    ImportExportHistoryMapper INSTANCE = Mappers.getMapper(ImportExportHistoryMapper.class);
    ImportExportHistoryDto importExportHistoryToImportExportHistoryDto(ImportExportHistory importExportHistory);
}
