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
//  mybatis-generator -- 2019-07-13 22:14
//  =============================================================================
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author resky
 * @version 1.0
 * @date 2019-07-13
 */
public class ProviderSelectPaginationByExampleWithBLOBsMethodGenerator extends ProviderSelectPaginationByExampleWithoutBLOBsMethodGenerator {
    public ProviderSelectPaginationByExampleWithBLOBsMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public List<IntrospectedColumn> getColumns() {
        return introspectedTable.getAllColumns();
    }

    @Override
    public String getMethodName() {
        return introspectedTable.getSelectPaginationByExampleWithBLOBsStatementId();
    }

    @Override
    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return context.getPlugins().providerSelectPaginationByExampleWithBLOBsMethodGenerated(method, topLevelClass,
                introspectedTable);
    }
}
