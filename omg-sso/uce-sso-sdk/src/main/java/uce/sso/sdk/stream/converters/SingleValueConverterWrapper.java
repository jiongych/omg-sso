/*
 * Copyright (C) 2006, 2007, 2011, 2014 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 20. February 2006 by Mauro Talevi
 */
package uce.sso.sdk.stream.converters;

import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;

/**
 * Wrapper to convert a  {@link uce.sso.sdk.stream.converters.SingleValueConverter} into a
 * {@link uce.sso.sdk.stream.converters.Converter}.
 *
 * @author J&ouml;rg Schaible
 * @see uce.sso.sdk.stream.converters.Converter
 * @see uce.sso.sdk.stream.converters.SingleValueConverter
 */
public class SingleValueConverterWrapper implements Converter, SingleValueConverter, ErrorReporter {

    private final SingleValueConverter wrapped;

    public SingleValueConverterWrapper(SingleValueConverter wrapped) {
        this.wrapped = wrapped;
    }

    public boolean canConvert(Class type) {
        return wrapped.canConvert(type);
    }

    public String toString(Object obj) {
        return wrapped.toString(obj);
    }

    public Object fromString(String str) {
        return wrapped.fromString(str);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.setValue(toString(source));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return fromString(reader.getValue());
    }

    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("wrapped-converter", wrapped == null ? "(null)" : wrapped.getClass().getName());
        if (wrapped instanceof ErrorReporter) {
            ((ErrorReporter)wrapped).appendErrors(errorWriter);
        }
    }
}
