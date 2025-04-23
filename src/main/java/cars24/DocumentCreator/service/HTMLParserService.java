package cars24.DocumentCreator.service;

import cars24.DocumentCreator.dto.Column;
import cars24.DocumentCreator.dto.Table;
import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.utility.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class HTMLParserService {

    private ObjectMapper objectMapper = new ObjectMapper();
    public String replacePlaceholders(String data, Template template) throws JsonProcessingException {
        Map<String,Object> templateFillData = objectMapper.readValue(data,Map.class);
        String html = insertTemplateTables(templateFillData,template);

        //Replace Placeholders
        StringBuilder result = new StringBuilder();
        int i = 0;
        int length = html.length();
        while (i < length) {
            if (i + 1 < length && html.charAt(i) == '(' && html.charAt(i + 1) == '(') {
                i += 2; // Skip "(("
                StringBuilder keyBuilder = new StringBuilder();

                while (i + 1 < length && !(html.charAt(i) == ')' && html.charAt(i + 1) == ')')) {
                    keyBuilder.append(html.charAt(i));
                    i++;
                }

                if (i + 1 < length && html.charAt(i) == ')' && html.charAt(i + 1) == ')') {
                    String key = keyBuilder.toString();
                    Object value = templateFillData.get(key);
                    if (value != null && DataUtils.isFlatDataType(value)) {
                        result.append(value.toString());
                    } else {
                        result.append("((").append(key).append("))"); // Keep original if missing or invalid
                    }
                    i += 2; // Skip "))"
                } else {
                    // Unclosed ((... append it literally
                    result.append("((").append(keyBuilder);
                    break;
                }
            } else {
                result.append(html.charAt(i));
                i++;
            }
        }
        return result.toString();
    }

    public String insertTemplateTables(Map<String,Object> data, Template template) {
        List<Table> tables = template.getTables();
        if (Objects.isNull(tables) || tables.isEmpty()) return template.getHtmlTemplate();
        Document doc = Jsoup.parse(template.getHtmlTemplate());
        for(Table table : tables){
            List<Map<String,Object>> tableEntries = (List<Map<String, Object>>) data.get(table.getTableId());
            if (Objects.isNull(tableEntries) || tableEntries.isEmpty()) continue;
            Element tableBody = doc.select("table#" + table.getTableId() + " tbody").first();
            if (tableBody != null) {
                List<Column> columns = table.getColumnsList();
                for (Map<String,Object> entry : tableEntries) {
                    tableBody.appendElement("tr");
                    for (Column column : columns) {
                        tableBody.appendElement("td").text(entry.get(column.getField()) != null ? String.valueOf(entry.get(column.getField())) : "");
                    }
                }
            }
        }
        return doc.outerHtml();
    }

    public List<Table> getTablesFromTemplate(String htmlContent){
        Document doc = Jsoup.parse(htmlContent);
        List<Table> tables = new ArrayList<>();

        Elements tableElements = doc.select("table[id]");

        for (Element tableElement : tableElements) {
            String tableId = tableElement.id();
            Table tableMapping = new Table();
            tableMapping.setTableId(tableId);
            List<Column> columns = new ArrayList<>();
            Elements headerCells = tableElement.select("thead th");
            if (headerCells.isEmpty()) {
                headerCells = tableElement.select("tr:first-child th");
            }
            for (int i = 0; i < headerCells.size(); i++) {
                Element cell = headerCells.get(i);
                Column column = new Column();
                String columnId = cell.hasAttr("id") ? cell.attr("id") :
                        generateColumnId(i, cell.text());
                column.setColumnId(columnId);
                column.setField(columnId);
                column.setHeaderText(cell.text());
                columns.add(column);
            }
            tableMapping.setColumnsList(columns);
            tables.add(tableMapping);
        }
        return tables;
    }

    /*
     * Generates a column ID from header text if id doesn't exist
     * Logic : Remove the spaces between the Column Text and Convert it in camelCase
     * Column : Product Name
     * ColumnId : productName
     */
    private String generateColumnId(int position, String headerText) {
        if (headerText != null && !headerText.trim().isEmpty()) {
            String cleaned = headerText.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
            String[] parts = cleaned.split("\\s+");
            StringBuilder result = new StringBuilder(parts[0].toLowerCase());
            for (int i = 1; i < parts.length; i++) {
                if (!parts[i].isEmpty()) {
                    result.append(Character.toUpperCase(parts[i].charAt(0)));
                    if (parts[i].length() > 1) {
                        result.append(parts[i].substring(1).toLowerCase());
                    }
                }
            }
            return result.toString();
        } else {
            return "column" + position;
        }
    }
}
