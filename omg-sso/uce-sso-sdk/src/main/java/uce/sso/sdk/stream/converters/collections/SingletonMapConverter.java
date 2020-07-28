/** 
 * @项目名称: FSP
 * @文件名称: SingletonMapConverter extends MapConverter 
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
 * Converts a singleton map to XML, specifying an 'entry'
 * element with 'key' and 'value' children.
 * <p>Note: 'key' and 'value' is not the name of the generated tag. The
 * children are serialized as normal elements and the implementation expects
 * them in the order 'key'/'value'.</p>
 * <p>Supports Collections.singletonMap.</p>
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.2
 */
public class SingletonMapConverter extends MapConverter {

    private static final Class MAP = Collections.singletonMap(Boolean.TRUE, null).getClass();

    /**
     * Construct a SingletonMapConverter.
     * @param mapper
     * @since 1.4.2
     */
    public SingletonMapConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class type) {
        return MAP == type;
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        reader.moveDown();
        Object key = readItem(reader, context, null);
        reader.moveUp();

        reader.moveDown();
        Object value = readItem(reader, context, null);
        reader.moveUp();
        reader.moveUp();
        
        return Collections.singletonMap(key, value); 
    }

}
