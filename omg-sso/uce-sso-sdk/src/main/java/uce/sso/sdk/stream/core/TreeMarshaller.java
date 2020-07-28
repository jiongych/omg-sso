/** 
 * @项目名称: FSP
 * @文件名称: TreeMarshaller implements MarshallingContext 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009, 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 15. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.core;

import java.util.Iterator;

import uce.sso.sdk.stream.converters.ConversionException;
import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.ConverterLookup;
import uce.sso.sdk.stream.converters.DataHolder;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.core.util.ObjectIdDictionary;
import uce.sso.sdk.stream.io.ExtendedHierarchicalStreamWriterHelper;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.mapper.Mapper;


public class TreeMarshaller implements MarshallingContext {

    protected HierarchicalStreamWriter writer;
    protected ConverterLookup converterLookup;
    private Mapper mapper;
    private ObjectIdDictionary parentObjects = new ObjectIdDictionary();
    private DataHolder dataHolder;

    public TreeMarshaller(
        HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
        this.writer = writer;
        this.converterLookup = converterLookup;
        this.mapper = mapper;
    }

    public void convertAnother(Object item) {
        convertAnother(item, null);
    }

    public void convertAnother(Object item, Converter converter) {
        if (converter == null) {
            converter = converterLookup.lookupConverterForType(item.getClass());
        } else {
            if (!converter.canConvert(item.getClass())) {
                ConversionException e = new ConversionException(
                    "Explicit selected converter cannot handle item");
                e.add("item-type", item.getClass().getName());
                e.add("converter-type", converter.getClass().getName());
                throw e;
            }
        }
        convert(item, converter);
    }

    protected void convert(Object item, Converter converter) {
        if (parentObjects.containsId(item)) {
            ConversionException e = new CircularReferenceException(
                "Recursive reference to parent object");
            e.add("item-type", item.getClass().getName());
            e.add("converter-type", converter.getClass().getName());
            throw e;
        }
        parentObjects.associateId(item, "");
        converter.marshal(item, writer, this);
        parentObjects.removeId(item);
    }

    public void start(Object item, DataHolder dataHolder) {
        this.dataHolder = dataHolder;
        if (item == null) {
            writer.startNode(mapper.serializedClass(null));
            writer.endNode();
        } else {
            ExtendedHierarchicalStreamWriterHelper.startNode(writer, mapper
                .serializedClass(item.getClass()), item.getClass());
            convertAnother(item);
            writer.endNode();
        }
    }

    public Object get(Object key) {
        lazilyCreateDataHolder();
        return dataHolder.get(key);
    }

    public void put(Object key, Object value) {
        lazilyCreateDataHolder();
        dataHolder.put(key, value);
    }

    public Iterator keys() {
        lazilyCreateDataHolder();
        return dataHolder.keys();
    }

    private void lazilyCreateDataHolder() {
        if (dataHolder == null) {
            dataHolder = new MapBackedDataHolder();
        }
    }

    protected Mapper getMapper() {
        return this.mapper;
    }

    public static class CircularReferenceException extends ConversionException {

        public CircularReferenceException(String msg) {
            super(msg);
        }
    }
}
