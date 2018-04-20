package BE.models.file.supportedviewinfoobjects;

import BE.entities.project.SupportedView;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TabularViewInfoModel extends BaseSupportedViewInfo {

    private Map<String,String> columns = new HashMap<>();
    private int rows;

    public TabularViewInfoModel() {
    }

    public TabularViewInfoModel(Map<String, String> columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void addColumn(String name, String type) {
        this.columns.put(name, type);
    }

    public void removeColumn(String name) {
        this.columns.remove(name);
    }

    @Override
    public String getName() {
        return SupportedView.TABULAR_VIEW;
    }

}
