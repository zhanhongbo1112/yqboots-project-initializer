package com.yqboots.project.initializer.core;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-18.
 */
public class DomainMetadataProperty implements Serializable {
    private String dbColumn;

    private String classField;

    private String fieldType = "String";

    private int length = 255;

    private boolean nullable = true;

    private boolean unique = false;

    private String defaultValue;

    public String getDbColumn() {
        return dbColumn;
    }

    public void setDbColumn(final String dbColumn) {
        this.dbColumn = dbColumn;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(final String classField) {
        this.classField = classField;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(final String fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(final boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(final boolean unique) {
        this.unique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
