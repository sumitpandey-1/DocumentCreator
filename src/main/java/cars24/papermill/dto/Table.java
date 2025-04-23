package cars24.papermill.dto;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String tableId;
    private List<Column> columnsList;
}
