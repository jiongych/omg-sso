/** 
 * @项目名称: FSP
 * @文件名称: MissingFieldException extends ObjectAccessException 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 01. October 2011 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters.reflection;

/**
 * Indicates a missing field or property creating an object.
 *
 * @author Nikita Levyankov
 * @author Joerg Schaible
 * @since 1.4.2
 */
public class MissingFieldException extends ObjectAccessException {

    private final String fieldName;
    private final String className;
    
    /**
     * Construct a MissingFieldException.
     * @param className the name of the class missing the field
     * @param fieldName the name of the missed field
     * @since 1.4.2
     */
    public MissingFieldException(final String className, final String fieldName) {
        super("No field '" + fieldName + "' found in class '" + className + "'");
        this.className = className;
        this.fieldName = fieldName;
    }

    /**
     * Retrieve the name of the missing field.
     * @return the field name
     * @since 1.4.2
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Retrieve the name of the class with the missing field.
     * @return the class name
     * @since 1.4.2
     */
    protected String getClassName() {
        return className;
    }
}
