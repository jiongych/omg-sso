/** 
 * @项目名称: FSP
 * @文件名称: CollectionConverter extends AbstractCollectionConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003, 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2010, 2011, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 01. October 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.collections;

import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.core.JVM;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.mapper.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Converts most common Collections (Lists and Sets) to XML, specifying a nested
 * element for each item.
 *
 * <p>Supports java.util.ArrayList, java.util.HashSet,
 * java.util.LinkedList, java.util.Vector and java.util.LinkedHashSet.</p>
 *
 * @author Joe Walnes
 */
public class CollectionConverter extends AbstractCollectionConverter {

    private final Class type;

    public CollectionConverter(Mapper mapper) {
        this(mapper, null);
    }

    /**
     * Construct a CollectionConverter for a special Collection type.
     * @param mapper the mapper
     * @param type the Collection type to handle
     * @since 1.4.5
     */
    public CollectionConverter(Mapper mapper, Class type) {
        super(mapper);
        this.type = type;
        if (type != null && !Collection.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException(type + " not of type " + Collection.class);
        }
    }

    public boolean canConvert(Class type) {
        if (this.type != null) {
            return type.equals(this.type);
        }
        return type.equals(ArrayList.class)
            || type.equals(HashSet.class)
            || type.equals(LinkedList.class)
            || type.equals(Vector.class) 
            || (JVM.is14() && type.getName().equals("java.util.LinkedHashSet"));
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Collection collection = (Collection) source;
        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            Object item = iterator.next();
            writeItem(item, context, writer);
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Collection collection = (Collection) createCollection(context.getRequiredType());
        populateCollection(reader, context, collection);
        return collection;
    }

    protected void populateCollection(HierarchicalStreamReader reader, UnmarshallingContext context, Collection collection) {
        populateCollection(reader, context, collection, collection);
    }

    protected void populateCollection(HierarchicalStreamReader reader, UnmarshallingContext context, Collection collection, Collection target) {
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            addCurrentElementToCollection(reader, context, collection, target);
            reader.moveUp();
        }
    }

    protected void addCurrentElementToCollection(HierarchicalStreamReader reader, UnmarshallingContext context,
        Collection collection, Collection target) {
        Object item = readItem(reader, context, collection);
        target.add(item);
    }

    protected Object createCollection(Class type) {
        return super.createCollection(this.type != null ? this.type : type);
    }
}
