/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
//  =============================================================================
//  Copyright (c) 2016~2019 Xi'an Linggu Software Co.Ltd. All rights reserved.
//  mybatis-generator -- 2019-07-12 12:46
//  =============================================================================
package org.mybatis.generator.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mybatis.generator.internal.util.EqualsUtil.areEqual;
import static org.mybatis.generator.internal.util.HashCodeUtil.SEED;
import static org.mybatis.generator.internal.util.HashCodeUtil.hash;
import static org.mybatis.generator.internal.util.StringUtility.*;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author resky
 * @version 1.0
 * @date 2019-07-12
 */
public class TableSetConfiguration extends PropertyHolder {
    public static String ATTR_SUFFIX_AS_PACKAGE = "suffixAsPackage";
    public static String SUFFIX_AS_PACKAGE_VALUE_TRUE = "true";
    private boolean suffixAsPackage;

    private boolean springEnabled;

    private boolean insertStatementEnabled;

    private boolean selectByPrimaryKeyStatementEnabled;

    private boolean selectByExampleStatementEnabled;

    private boolean updateByPrimaryKeyStatementEnabled;

    private boolean deleteByPrimaryKeyStatementEnabled;

    private boolean deleteByExampleStatementEnabled;

    private boolean countByExampleStatementEnabled;

    private boolean updateByExampleStatementEnabled;

    private List<ColumnOverride> columnOverrides;

    private Map<IgnoredColumn, Boolean> ignoredColumns;

    private GeneratedKey generatedKey;

    private String selectByPrimaryKeyQueryId;

    private String selectByExampleQueryId;

    private String catalog;

    private String schema;

    private String domainObjectName;

    private String alias;

    private ModelType modelType;

    private boolean wildcardEscapingEnabled;

    private boolean delimitIdentifiers;

    private DomainObjectRenamingRule domainObjectRenamingRule;

    private ColumnRenamingRule columnRenamingRule;

    private boolean isAllColumnDelimitingEnabled;

    private String mapperName;
    private String sqlProviderName;

    private List<IgnoredColumnPattern> ignoredColumnPatterns = new ArrayList<>();

    public TableSetConfiguration(Context context) {
        super();
        springEnabled = false;
        suffixAsPackage = false;
        this.modelType = context.getDefaultModelType();
        columnOverrides = new ArrayList<>();
        ignoredColumns = new HashMap<>();

        insertStatementEnabled = true;
        selectByPrimaryKeyStatementEnabled = true;
        selectByExampleStatementEnabled = true;
        updateByPrimaryKeyStatementEnabled = true;
        deleteByPrimaryKeyStatementEnabled = true;
        deleteByExampleStatementEnabled = true;
        countByExampleStatementEnabled = true;
        updateByExampleStatementEnabled = true;
    }

    public boolean isDeleteByPrimaryKeyStatementEnabled() {
        return deleteByPrimaryKeyStatementEnabled;
    }

    public void setDeleteByPrimaryKeyStatementEnabled(boolean deleteByPrimaryKeyStatementEnabled) {
        this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
    }

    public boolean isInsertStatementEnabled() {
        return insertStatementEnabled;
    }

    public void setInsertStatementEnabled(boolean insertStatementEnabled) {
        this.insertStatementEnabled = insertStatementEnabled;
    }

    public boolean isSelectByPrimaryKeyStatementEnabled() {
        return selectByPrimaryKeyStatementEnabled;
    }

    public void setSelectByPrimaryKeyStatementEnabled(boolean selectByPrimaryKeyStatementEnabled) {
        this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
    }

    public boolean isUpdateByPrimaryKeyStatementEnabled() {
        return updateByPrimaryKeyStatementEnabled;
    }

    public void setUpdateByPrimaryKeyStatementEnabled(boolean updateByPrimaryKeyStatementEnabled) {
        this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
    }

    public boolean isColumnIgnored(String columnName) {
        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
            if (entry.getKey().matches(columnName)) {
                entry.setValue(Boolean.TRUE);
                return true;
            }
        }

        for (IgnoredColumnPattern ignoredColumnPattern : ignoredColumnPatterns) {
            if (ignoredColumnPattern.matches(columnName)) {
                return true;
            }
        }

        return false;
    }

    public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
        ignoredColumns.put(ignoredColumn, Boolean.FALSE);
    }

    public void addIgnoredColumnPattern(IgnoredColumnPattern ignoredColumnPattern) {
        ignoredColumnPatterns.add(ignoredColumnPattern);
    }

    public void addColumnOverride(ColumnOverride columnOverride) {
        columnOverrides.add(columnOverride);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TableSetConfiguration)) {
            return false;
        }

        TableSetConfiguration other = (TableSetConfiguration) obj;

        return areEqual(this.catalog, other.catalog) && areEqual(this.schema, other.schema);
    }

    @Override
    public int hashCode() {
        int result = SEED;
        result = hash(result, catalog);
        result = hash(result, schema);
        return result;
    }

    public boolean isSelectByExampleStatementEnabled() {
        return selectByExampleStatementEnabled;
    }

    public void setSelectByExampleStatementEnabled(boolean selectByExampleStatementEnabled) {
        this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
    }

    /**
     * May return null if the column has not been overridden.
     *
     * @param columnName
     *            the column name
     * @return the column override (if any) related to this column
     */
    public ColumnOverride getColumnOverride(String columnName) {
        for (ColumnOverride co : columnOverrides) {
            if (co.isColumnNameDelimited()) {
                if (columnName.equals(co.getColumnName())) {
                    return co;
                }
            } else {
                if (columnName.equalsIgnoreCase(co.getColumnName())) {
                    return co;
                }
            }
        }

        return null;
    }

    public GeneratedKey getGeneratedKey() {
        return generatedKey;
    }

    public String getSelectByExampleQueryId() {
        return selectByExampleQueryId;
    }

    public void setSelectByExampleQueryId(String selectByExampleQueryId) {
        this.selectByExampleQueryId = selectByExampleQueryId;
    }

    public String getSelectByPrimaryKeyQueryId() {
        return selectByPrimaryKeyQueryId;
    }

    public void setSelectByPrimaryKeyQueryId(String selectByPrimaryKeyQueryId) {
        this.selectByPrimaryKeyQueryId = selectByPrimaryKeyQueryId;
    }

    public boolean isDeleteByExampleStatementEnabled() {
        return deleteByExampleStatementEnabled;
    }

    public void setDeleteByExampleStatementEnabled(boolean deleteByExampleStatementEnabled) {
        this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
    }

    public boolean areAnyStatementsEnabled() {
        return selectByExampleStatementEnabled || selectByPrimaryKeyStatementEnabled || insertStatementEnabled
                || updateByPrimaryKeyStatementEnabled || deleteByExampleStatementEnabled
                || deleteByPrimaryKeyStatementEnabled || countByExampleStatementEnabled
                || updateByExampleStatementEnabled;
    }

    public void setGeneratedKey(GeneratedKey generatedKey) {
        this.generatedKey = generatedKey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    /**
     * Returns a List of Strings. The values are the columns that were specified to be ignored in the table, but do not
     * exist in the table.
     *
     * @return a List of Strings - the columns that were improperly configured as ignored columns
     */
    public List<String> getIgnoredColumnsInError() {
        List<String> answer = new ArrayList<>();

        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                answer.add(entry.getKey().getColumnName());
            }
        }

        return answer;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setConfiguredModelType(String configuredModelType) {
        this.modelType = ModelType.getModelType(configuredModelType);
    }

    public boolean isWildcardEscapingEnabled() {
        return wildcardEscapingEnabled;
    }

    public void setWildcardEscapingEnabled(boolean wildcardEscapingEnabled) {
        this.wildcardEscapingEnabled = wildcardEscapingEnabled;
    }

    @Override
    public String toString() {
        return "TableSetConfiguration{" + "suffixAsPackage=" + suffixAsPackage + ", springEnabled=" + springEnabled
                + ", insertStatementEnabled=" + insertStatementEnabled + ", selectByPrimaryKeyStatementEnabled="
                + selectByPrimaryKeyStatementEnabled + ", selectByExampleStatementEnabled="
                + selectByExampleStatementEnabled + ", updateByPrimaryKeyStatementEnabled="
                + updateByPrimaryKeyStatementEnabled + ", deleteByPrimaryKeyStatementEnabled="
                + deleteByPrimaryKeyStatementEnabled + ", deleteByExampleStatementEnabled="
                + deleteByExampleStatementEnabled + ", countByExampleStatementEnabled=" + countByExampleStatementEnabled
                + ", updateByExampleStatementEnabled=" + updateByExampleStatementEnabled + ", columnOverrides="
                + columnOverrides + ", ignoredColumns=" + ignoredColumns + ", generatedKey=" + generatedKey
                + ", selectByPrimaryKeyQueryId='" + selectByPrimaryKeyQueryId + '\'' + ", selectByExampleQueryId='"
                + selectByExampleQueryId + '\'' + ", catalog='" + catalog + '\'' + ", schema='" + schema + '\''
                + ", domainObjectName='" + domainObjectName + '\'' + ", alias='" + alias + '\'' + ", modelType="
                + modelType + ", wildcardEscapingEnabled=" + wildcardEscapingEnabled + ", delimitIdentifiers="
                + delimitIdentifiers + ", domainObjectRenamingRule=" + domainObjectRenamingRule
                + ", columnRenamingRule=" + columnRenamingRule + ", isAllColumnDelimitingEnabled="
                + isAllColumnDelimitingEnabled + ", mapperName='" + mapperName + '\'' + ", sqlProviderName='"
                + sqlProviderName + '\'' + ", ignoredColumnPatterns=" + ignoredColumnPatterns + '}';
    }

    public boolean isDelimitIdentifiers() {
        return delimitIdentifiers;
    }

    public void setDelimitIdentifiers(boolean delimitIdentifiers) {
        this.delimitIdentifiers = delimitIdentifiers;
    }

    public boolean isCountByExampleStatementEnabled() {
        return countByExampleStatementEnabled;
    }

    public void setCountByExampleStatementEnabled(boolean countByExampleStatementEnabled) {
        this.countByExampleStatementEnabled = countByExampleStatementEnabled;
    }

    public boolean isUpdateByExampleStatementEnabled() {
        return updateByExampleStatementEnabled;
    }

    public void setUpdateByExampleStatementEnabled(boolean updateByExampleStatementEnabled) {
        this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
    }

    public DomainObjectRenamingRule getDomainObjectRenamingRule() {
        return domainObjectRenamingRule;
    }

    public void setDomainObjectRenamingRule(DomainObjectRenamingRule domainObjectRenamingRule) {
        this.domainObjectRenamingRule = domainObjectRenamingRule;
    }

    public ColumnRenamingRule getColumnRenamingRule() {
        return columnRenamingRule;
    }

    public void setColumnRenamingRule(ColumnRenamingRule columnRenamingRule) {
        this.columnRenamingRule = columnRenamingRule;
    }

    public boolean isAllColumnDelimitingEnabled() {
        return isAllColumnDelimitingEnabled;
    }

    public void setAllColumnDelimitingEnabled(boolean isAllColumnDelimitingEnabled) {
        this.isAllColumnDelimitingEnabled = isAllColumnDelimitingEnabled;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getSqlProviderName() {
        return sqlProviderName;
    }

    public void setSqlProviderName(String sqlProviderName) {
        this.sqlProviderName = sqlProviderName;
    }

    public boolean isSuffixAsPackage() {
        return suffixAsPackage;
    }

    public void setSuffixAsPackage(boolean suffixAsPackage) {
        this.suffixAsPackage = suffixAsPackage;
    }

    public boolean isSpringEnabled() {
        return springEnabled;
    }

    public void setSpringEnabled(boolean springEnabled) {
        this.springEnabled = springEnabled;
    }
}
