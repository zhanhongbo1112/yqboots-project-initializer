/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.initializer.core;

import java.io.Serializable;

/**
 * The domain metadata properties.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class DomainMetadataProperty implements Serializable {
    /**
     * Field name in the DB table.
     */
    private String dbColumn;

    /**
     * Field name in the Java class.
     */
    private String classField;

    /**
     * Field type, such as String, Boolean, Integer, etc.
     */
    private String fieldType = "String";

    /**
     * If the field is nullable.
     */
    private boolean nullable = true;

    /**
     * If the field is unique.
     */
    private boolean unique = false;

    /**
     * The default value for the field.
     */
    private String defaultValue;

    /**
     * Gets the field name in the DB
     *
     * @return the db field name
     */
    public String getDbColumn() {
        return dbColumn;
    }

    /**
     * Sets the field name in the DB
     *
     * @param dbColumn the db field name
     */
    public void setDbColumn(final String dbColumn) {
        this.dbColumn = dbColumn;
    }

    /**
     * Gets the field name in the Java class.
     *
     * @return field name in the Java class.
     */
    public String getClassField() {
        return classField;
    }

    /**
     * Sets the field name in the Java class.
     *
     * @param classField field name in the Java class.
     */
    public void setClassField(final String classField) {
        this.classField = classField;
    }

    /**
     * Gets the field type.
     *
     * @return the field type
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * Sets the field type.
     *
     * @param fieldType the field type
     */
    public void setFieldType(final String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Checks if the field can be nullable.
     *
     * @return nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * Sets the nullable.
     *
     * @param nullable nullable
     */
    public void setNullable(final boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * Checks if the field can be unique.
     *
     * @return unique
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * Sets the unique.
     *
     * @param unique unique
     */
    public void setUnique(final boolean unique) {
        this.unique = unique;
    }

    /**
     * Gets the default value.
     *
     * @return the default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value.
     *
     * @param defaultValue the default value
     */
    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
