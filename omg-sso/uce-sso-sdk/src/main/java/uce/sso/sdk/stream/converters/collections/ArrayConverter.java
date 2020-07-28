/** 
 * @项目名称: FSP
 * @文件名称: ArrayConverter extends AbstractCollectionConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003, 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 03. October 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.collections;

import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.mapper.Mapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Converts an array of objects or primitives to XML, using
 * a nested child element for each item.
 *
 * @author Joe Walnes
 */
public class ArrayConverter extends AbstractCollectionConverter {

    public ArrayConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class type) {
        return type.isArray();
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        int length = Array.getLength(source);
        for (int i = 0; i < length; i++) {
            Object item = Array.get(source, i);
            writeItem(item, context, writer);
        }

    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // read the items from xml into a list
        List items = new ArrayList();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            Object item = readItem(reader, context, null); // TODO: arg, what should replace null?
            items.add(item);
            reader.moveUp();
        }
        // now convertAnother the list into an array
        // (this has to be done as a separate list as the array size is not
        //  known until all items have been read)
        Object array = Array.newInstance(context.getRequiredType().getComponentType(), items.size());
        int i = 0;
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            Array.set(array, i++, iterator.next());
        }
        return array;
    }
}
