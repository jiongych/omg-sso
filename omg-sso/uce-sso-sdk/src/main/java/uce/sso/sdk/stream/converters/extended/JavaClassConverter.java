/** 
 * @项目名称: FSP
 * @文件名称: JavaClassConverter extends AbstractSingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009, 2011, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 04. April 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.extended;

import uce.sso.sdk.stream.converters.ConversionException;
import uce.sso.sdk.stream.converters.basic.AbstractSingleValueConverter;
import uce.sso.sdk.stream.core.ClassLoaderReference;
import uce.sso.sdk.stream.mapper.CannotResolveClassException;
import uce.sso.sdk.stream.mapper.DefaultMapper;
import uce.sso.sdk.stream.mapper.Mapper;

/**
 * Converts a java.lang.Class to XML.
 * 
 * @author Aslak Helles&oslash;y
 * @author Joe Walnes
 * @author Matthew Sandoz
 * @author J&ouml;rg Schaible
 */
public class JavaClassConverter extends AbstractSingleValueConverter {

    private Mapper mapper;

    /**
     * Construct a JavaClassConverter.
     * @param classLoaderReference the reference to the {@link ClassLoader} of the XStream instance
     * @since 1.4.5
     */
    public JavaClassConverter(ClassLoaderReference classLoaderReference) {
        this(new DefaultMapper(classLoaderReference));
    }

    /**
     * @deprecated As of 1.4.5 use {@link #JavaClassConverter(ClassLoaderReference)}
     */
    public JavaClassConverter(ClassLoader classLoader) {
        this(new ClassLoaderReference(classLoader));
    }

    /**
     * Construct a JavaClassConverter that uses a provided mapper. Depending on the mapper
     * chain it will not only be used to load classes, but also to support type aliases.
     * @param mapper to use
     * @since 1.4.5
     */
    protected JavaClassConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    public boolean canConvert(Class clazz) {
        return Class.class.equals(clazz); // :)
    }

    public String toString(Object obj) {
        return mapper.serializedClass(((Class) obj));
    }

    public Object fromString(String str) {
        try {
            return mapper.realClass(str);
        } catch (CannotResolveClassException e) {
            throw new ConversionException("Cannot load java class " + str, e.getCause());
        }
    }
}
