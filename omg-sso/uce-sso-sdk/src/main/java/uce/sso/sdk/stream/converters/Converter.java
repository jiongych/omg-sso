/*
 * Copyright (C) 2003, 2004 Joe Walnes.
 * Copyright (C) 2006, 2007, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26. September 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.converters;

import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;

/**
 * Converter implementations are responsible marshalling Java objects
 * to/from textual data.
 * 
 * <p>If an exception occurs during processing, a {@link ConversionException}
 * should be thrown.</p>
 * 
 * <p>If working with the high level {@link uce.sso.sdk.stream.XStream} facade,
 * you can register new converters using the XStream.registerConverter() method.</p>
 * 
 * <p>If working with the lower level API, the
 * {@link uce.sso.sdk.stream.converters.ConverterLookup} implementation is
 * responsible for looking up the appropriate converter.</p>
 * 
 * <p>Converters for object that can store all information in a single value
 * should implement {@link uce.sso.sdk.stream.converters.SingleValueConverter}.
 * {@link uce.sso.sdk.stream.converters.basic.AbstractSingleValueConverter}
 * provides a starting point.</p>
 * 
 * <p>{@link uce.sso.sdk.stream.converters.collections.AbstractCollectionConverter}
 * provides a starting point for objects that hold a collection of other objects
 * (such as Lists and Maps).</p>
 *
 * @author Joe Walnes
 * @see uce.sso.sdk.stream.XStream
 * @see uce.sso.sdk.stream.converters.ConverterLookup
 * @see uce.sso.sdk.stream.converters.basic.AbstractSingleValueConverter
 * @see uce.sso.sdk.stream.converters.collections.AbstractCollectionConverter
 */
public interface Converter extends ConverterMatcher {

    /**
     * Convert an object to textual data.
     *
     * @param source  The object to be marshalled.
     * @param writer  A stream to write to.
     * @param context A context that allows nested objects to be processed by XStream.
     */
    void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context);

    /**
     * Convert textual data back into an object.
     *
     * @param reader  The stream to read the text from.
     * @param context
     * @return The resulting object.
     */
    Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context);

}
