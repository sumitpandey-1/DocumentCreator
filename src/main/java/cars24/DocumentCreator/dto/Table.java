package cars24.DocumentCreator.dto;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String tableId;
    private List<Column> columnsList;
}
