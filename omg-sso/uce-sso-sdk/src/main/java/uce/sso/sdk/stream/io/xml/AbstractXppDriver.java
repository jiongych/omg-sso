/*
 * Copyright (C) 2009, 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 29. April 2009 by Joerg Schaible
 */
package uce.sso.sdk.stream.io.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import uce.sso.sdk.stream.core.util.XmlHeaderAwareReader;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.io.StreamException;
import uce.sso.sdk.stream.io.naming.NameCoder;
import uce.sso.sdk.xmlpull.XmlPullParser;
import uce.sso.sdk.xmlpull.XmlPullParserException;

/**
 * An abstract base class for a driver using an XPP implementation. 
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @since 1.4
 */
public abstract class AbstractXppDriver extends AbstractXmlDriver {

    /**
     * Construct an AbstractXppDriver.
     * 
     * @param nameCoder the replacer for XML friendly tag and attribute names
     * @since 1.4
     */
    public AbstractXppDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    /**
     * {@inheritDoc}
     */
    public HierarchicalStreamReader createReader(Reader in) {
        try {
            return new XppReader(in, createParser(), getNameCoder());
        } catch (XmlPullParserException e) {
            throw new StreamException("Cannot create XmlPullParser");
        }
    }

    /**
     * {@inheritDoc}
     */
    public HierarchicalStreamReader createReader(InputStream in) {
        try {
            return createReader(new XmlHeaderAwareReader(in));
        } catch (UnsupportedEncodingException e) {
            throw new StreamException(e);
        } catch (IOException e) {
            throw new StreamException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new PrettyPrintWriter(out, getNameCoder());
    }

    /**
     * {@inheritDoc}
     */
    public HierarchicalStreamWriter createWriter(OutputStream out) {
        return createWriter(new OutputStreamWriter(out));
    }

    /**
     * Create the parser of the XPP implementation.

     * @throws XmlPullParserException if the parser cannot be created
     * @since 1.4
     */
    protected abstract XmlPullParser createParser() throws XmlPullParserException;
}
