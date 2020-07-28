/** 
 * @项目名称: FSP
 * @文件名称: DefaultConverterLookup implements ConverterLookup, ConverterRegistry, Caching 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009, 2011, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.core;

import uce.sso.sdk.stream.converters.ConversionException;
import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.ConverterLookup;
import uce.sso.sdk.stream.converters.ConverterRegistry;
import uce.sso.sdk.stream.core.util.PrioritizedList;
import uce.sso.sdk.stream.mapper.Mapper;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The default implementation of converters lookup.
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @author Guilherme Silveira
 */
public class DefaultConverterLookup implements ConverterLookup, ConverterRegistry, Caching {

    private final PrioritizedList converters = new PrioritizedList();
    private transient Map typeToConverterMap;

    public DefaultConverterLookup() {
    	readResolve();
    }

    /**
     * @deprecated As of 1.3, use {@link #DefaultConverterLookup()}
     */
    public DefaultConverterLookup(Mapper mapper) {
    }

    public Converter lookupConverterForType(Class type) {
        Converter cachedConverter = (Converter) typeToConverterMap.get(type);
        if (cachedConverter != null) {
            return cachedConverter;
        }
        Iterator iterator = converters.iterator();
        while (iterator.hasNext()) {
            Converter converter = (Converter) iterator.next();
            if (converter.canConvert(type)) {
                typeToConverterMap.put(type, converter);
                return converter;
            }
        }
        throw new ConversionException("No converter specified for " + type);
    }
    
    public void registerConverter(Converter converter, int priority) {
        converters.add(converter, priority);
        for (Iterator iter = typeToConverterMap.keySet().iterator(); iter.hasNext();) {
            Class type = (Class) iter.next();
            if (converter.canConvert(type)) {
                iter.remove();
            }
        }
    }
    
    public void flushCache() {
        typeToConverterMap.clear();
        Iterator iterator = converters.iterator();
        while (iterator.hasNext()) {
            Converter converter = (Converter) iterator.next();
            if (converter instanceof Caching) {
                ((Caching)converter).flushCache();
            }
        }
    }

    private Object readResolve() {
        typeToConverterMap = Collections.synchronizedMap(new WeakHashMap());
        return this;
    }
}
