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
//  mybatis-generator -- 2019-07-13 16:29
//  =============================================================================
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author resky
 * @version 1.0
 * @date 2019-07-13
 */
public class SelectPaginationByExampleWithBLOBsElementGenerator extends AbstractXmlElementGenerator {

    public SelectPaginationByExampleWithBLOBsElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        String fqjt = introspectedTable.getExampleType(); // fqjt

        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", introspectedTable.getSelectPaginationByExampleWithBLOBsStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultMap", introspectedTable.getResultMapWithBLOBsId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", "map")); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select")); //$NON-NLS-1$
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "example.distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        answer.addElement(ifElement);

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable.getSelectPaginationByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectPaginationByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            answer.addElement(new TextElement(sb.toString()));
        }

        answer.addElement(getBaseColumnListElement());
        answer.addElement(new TextElement(",")); //$NON-NLS-1$
        answer.addElement(getBlobColumnListElement());

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement());

        ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "example.orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("order by ${example.orderByClause}")); //$NON-NLS-1$
        answer.addElement(ifElement);

        answer.addElement(new TextElement("limit ${pageNumber*pageSize}, ${pageSize}"));
        if (context.getPlugins().sqlMapSelectPaginationByExampleWithBLOBsElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
