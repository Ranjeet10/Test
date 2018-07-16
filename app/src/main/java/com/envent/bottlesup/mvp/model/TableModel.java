package com.envent.bottlesup.mvp.model;

/**
 * Created by ronem on 4/8/18.
 */

public class TableModel {
    private String tableId;
    private String tableName;

    public TableModel(String tableId, String tableName) {
        this.tableId = tableId;
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }
}
