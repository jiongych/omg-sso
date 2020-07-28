/** 
 * @项目名称: FSP
 * @文件名称: FieldKey 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 10. April 2007 by Guilherme Silveira
 */
package uce.sso.sdk.stream.converters.reflection;

/**
 * A field key.
 * 
 * @author Guilherme Silveira
 * @author J&ouml;rg Schaible
 */
public class FieldKey {
    final private String fieldName;
    final private Class declaringClass;
    final private int depth;
    final private int order;

    public FieldKey(String fieldName, Class declaringClass, int order) {
        if (fieldName == null || declaringClass == null) {
            throw new IllegalArgumentException("fieldName or declaringClass is null");
        }
        this.fieldName = fieldName;
        this.declaringClass = declaringClass;
        this.order = order;
        Class c = declaringClass;
        int i = 0;
        while (c.getSuperclass() != null) {
            i++;
            c = c.getSuperclass();
        }
        depth = i;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Class getDeclaringClass() {
        return this.declaringClass;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getOrder() {
        return this.order;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldKey)) return false;

        final FieldKey fieldKey = (FieldKey)o;

        if (!declaringClass.equals(fieldKey.declaringClass)) 
            return false;
        if (!fieldName.equals(fieldKey.fieldName)) 
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = fieldName.hashCode();
        result = 29 * result +declaringClass.hashCode();
        return result;
    }

    public String toString() {
        return "FieldKey{"
            + "order="
            + order
            + ", writer="
            + depth
            + ", declaringClass="
            + declaringClass
            + ", fieldName='"
            + fieldName
            + "'"
            + "}";
    }

}