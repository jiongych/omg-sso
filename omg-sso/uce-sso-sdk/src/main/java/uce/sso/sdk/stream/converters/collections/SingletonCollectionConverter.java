/** 
 * @项目名称: FSP
 * @文件名称: SingletonCollectionConverter extends CollectionConverter 
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
 * Created on 11. October 2011 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters.collections;

import java.util.Collections;

import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.mapper.Mapper;


/**
 * Converts singleton collections (list and set) to XML, specifying a nested element for the
 * item.
 * <p>
 * Supports Collections.singleton(Object) and Collections.singletonList(Object).
 * </p>
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.2
 */
public class SingletonCollectionConverter extends CollectionConverter {

    private static final Class LIST = Collections.singletonList(Boolean.TRUE).getClass();
    private static final Class SET = Collections.singleton(Boolean.TRUE).getClass();

    /**
     * Construct a SingletonCollectionConverter.
     * 
     * @param mapper the mapper
     * @since 1.4.2
     */
    public SingletonCollectionConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class type) {
        return LIST == type || SET == type;
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        Object item = readItem(reader, context, null);
        reader.moveUp();
        return context.getRequiredType() == LIST
            ? (Object)Collections.singletonList(item)
            : (Object)Collections.singleton(item);
    }
}
