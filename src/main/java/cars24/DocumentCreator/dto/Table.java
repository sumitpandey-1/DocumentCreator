package cars24.DocumentCreator.dto;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String tableId;
    private String tableSource;
    private List<Column> columnsList;
}
