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
//  mybatis-generator -- 2019-07-13 14:55
//  =============================================================================
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author resky
 * @version 1.0
 * @date 2019-07-13
 */
public class SelectPaginationByExampleWithoutBLOBsMethodGenerator extends AbstractJavaMapperMethodGenerator {

    public SelectPaginationByExampleWithoutBLOBsMethodGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(type);
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method(introspectedTable.getSelectPaginationByExampleStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType;
        if (introspectedTable.getRules().generateBaseRecordClass()) {
            listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            listType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
        } else {
            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
        }

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);

        method.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "pageSize", "@Param(\"pageSize\")"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "pageNumber", "@Param(\"pageNumber\")"));
        method.addParameter(new Parameter(type, "example", "@Param(\"example\")")); //$NON-NLS-1$

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        addMapperAnnotations(interfaze, method);

        if (context.getPlugins().clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze,
                introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    }

    public void addExtraImports(Interface interfaze) {
    }
}
