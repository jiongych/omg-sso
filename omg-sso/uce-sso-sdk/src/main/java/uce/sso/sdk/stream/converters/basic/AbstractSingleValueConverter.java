/*
 * Copyright (C) 2006, 2007, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 15. February 2006 by Mauro Talevi
 */
package uce.sso.sdk.stream.converters.basic;

import uce.sso.sdk.stream.converters.SingleValueConverter;

/**
 * Base abstract implementation of  {@link uce.sso.sdk.stream.converters.SingleValueConverter}.
 *
 * <p>Subclasses should implement methods canConvert(Class) and fromString(String) for the conversion.</p>
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @author Mauro Talevi
 * @see uce.sso.sdk.stream.converters.SingleValueConverter
 */
public abstract class AbstractSingleValueConverter implements SingleValueConverter {

    public abstract boolean canConvert(Class type);

    public String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    public abstract Object fromString(String str);

}
