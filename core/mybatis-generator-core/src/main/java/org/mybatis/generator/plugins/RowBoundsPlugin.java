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
package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * This plugin will add selectByExample methods that include rowBounds parameters to the generated mapper interface.
 * This plugin is only valid for MyBatis3.
 * 
 * @author Jeff Butler
 */
public class RowBoundsPlugin extends PluginAdapter {

    private FullyQualifiedJavaType rowBounds = new FullyQualifiedJavaType("org.apache.ibatis.session.RowBounds"); //$NON-NLS-1$
    private Map<FullyQualifiedTable, List<XmlElement>> elementsToAdd = new HashMap<>();

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndAddMethod(method, interfaze);
        } else if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3_DSQL) {
            copyAndAddSelectByExampleMethodForDSQL(method, interfaze);
        }
        return true;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndAddMethod(method, interfaze);
        }
        return true;
    }

    @Override
    public boolean clientSelectPaginationByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndAddMethod(method, interfaze);
        } else if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3_DSQL) {
            copyAndAddSelectByExampleMethodForDSQL(method, interfaze);
        }
        return true;
    }

    @Override
    public boolean clientSelectPaginationByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndAddMethod(method, interfaze);
        }
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndSaveElement(element, introspectedTable.getFullyQualifiedTable());
        }
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndSaveElement(element, introspectedTable.getFullyQualifiedTable());
        }
        return true;
    }

    @Override
    public boolean sqlMapSelectPaginationByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndSaveElement(element, introspectedTable.getFullyQualifiedTable());
        }
        return true;
    }

    @Override
    public boolean sqlMapSelectPaginationByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            copyAndSaveElement(element, introspectedTable.getFullyQualifiedTable());
        }
        return true;
    }

    /**
     * We'll override this method and add any new elements generated by previous calls.
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<XmlElement> elements = elementsToAdd.get(introspectedTable.getFullyQualifiedTable());
        if (elements != null) {
            for (XmlElement element : elements) {
                document.getRootElement().addElement(element);
            }
        }

        return true;
    }

    @Override
    public boolean clientBasicSelectManyMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        copyAndAddSelectManyMethod(method, interfaze);

        addNewComposedFunction(interfaze, introspectedTable, method.getReturnType());
        return true;
    }

    private void addNewComposedFunction(Interface interfaze, IntrospectedTable introspectedTable,
            Optional<FullyQualifiedJavaType> baseMethodReturnType) {
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.function.Function")); //$NON-NLS-1$

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("Function<SelectStatementProvider, " //$NON-NLS-1$
                + baseMethodReturnType.get().getShortName() + ">"); //$NON-NLS-1$

        Method method = new Method("selectManyWithRowbounds"); //$NON-NLS-1$
        method.setDefault(true);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(rowBounds, "rowBounds")); //$NON-NLS-1$
        method.addBodyLine("return selectStatement -> selectManyWithRowbounds(selectStatement, rowBounds);"); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable,
                interfaze.getImportedTypes());
        interfaze.addMethod(method);
    }

    /**
     * Use the method copy constructor to create a new method, then add the rowBounds parameter.
     * 
     * @param interfaze
     *            the table
     * @param method
     *            the method
     */
    private void copyAndAddMethod(Method method, Interface interfaze) {
        Method newMethod = new Method(method);
        newMethod.setName(method.getName() + "WithRowbounds"); //$NON-NLS-1$
        newMethod.addParameter(new Parameter(rowBounds, "rowBounds")); //$NON-NLS-1$
        interfaze.addMethod(newMethod);
        interfaze.addImportedType(rowBounds);
    }

    private void copyAndAddSelectManyMethod(Method method, Interface interfaze) {
        List<String> annotations = new ArrayList<>(method.getAnnotations());

        // remove the @Results annotation and replace it with @ResultMap
        boolean inResultsAnnotation = false;
        String resultMapId = null;
        Iterator<String> iter = annotations.iterator();
        while (iter.hasNext()) {
            String annotation = iter.next();

            if (inResultsAnnotation) {
                if (annotation.equals("})")) { //$NON-NLS-1$
                    inResultsAnnotation = false;
                }
                iter.remove();
            } else if (annotation.startsWith("@Results(")) { //$NON-NLS-1$
                inResultsAnnotation = true;
                iter.remove();

                // now find the ID
                int index = annotation.indexOf("id=\""); //$NON-NLS-1$
                int startIndex = index + "id=\"".length(); //$NON-NLS-1$
                int endIndex = annotation.indexOf('\"', startIndex + 1); // $NON-NLS-1$
                resultMapId = annotation.substring(startIndex, endIndex);
            }
        }

        if (resultMapId != null) {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ResultMap")); //$NON-NLS-1$
            annotations.add("@ResultMap(\"" + resultMapId + "\")"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        Method newMethod = new Method(method);
        newMethod.getAnnotations().clear();
        for (String annotation : annotations) {
            newMethod.addAnnotation(annotation);
        }

        newMethod.setName(method.getName() + "WithRowbounds"); //$NON-NLS-1$
        newMethod.addParameter(new Parameter(rowBounds, "rowBounds")); //$NON-NLS-1$
        interfaze.addMethod(newMethod);
        interfaze.addImportedType(rowBounds);
    }

    private void copyAndAddSelectByExampleMethodForDSQL(Method method, Interface interfaze) {
        Method newMethod = new Method(method);
        newMethod.addParameter(new Parameter(rowBounds, "rowBounds")); //$NON-NLS-1$
        interfaze.addMethod(newMethod);
        interfaze.addImportedType(rowBounds);

        // replace the call to selectMany with the new call to selectManyWithRowbounds
        for (int i = 0; i < newMethod.getBodyLines().size(); i++) {
            String bodyLine = newMethod.getBodyLines().get(i);

            if (bodyLine.contains("this::selectMany")) { //$NON-NLS-1$
                bodyLine = bodyLine.replace("this::selectMany", //$NON-NLS-1$
                        "selectManyWithRowbounds(rowBounds)"); //$NON-NLS-1$
                newMethod.getBodyLines().set(i, bodyLine);
                break;
            }
        }
    }

    /**
     * Use the method copy constructor to create a new element.
     * 
     * @param fqt
     *            the table
     * @param element
     *            the method
     */
    private void copyAndSaveElement(XmlElement element, FullyQualifiedTable fqt) {
        XmlElement newElement = new XmlElement(element);

        // remove old id attribute and add a new one with the new name
        for (Iterator<Attribute> iterator = newElement.getAttributes().iterator(); iterator.hasNext();) {
            Attribute attribute = iterator.next();
            if ("id".equals(attribute.getName())) { //$NON-NLS-1$
                iterator.remove();
                Attribute newAttribute = new Attribute("id", attribute.getValue() + "WithRowbounds"); //$NON-NLS-1$ //$NON-NLS-2$
                newElement.addAttribute(newAttribute);
                break;
            }
        }

        // save the new element locally. We'll add it to the document
        // later
        List<XmlElement> elements = elementsToAdd.get(fqt);
        if (elements == null) {
            elements = new ArrayList<>();
            elementsToAdd.put(fqt, elements);
        }
        elements.add(newElement);
    }
}
