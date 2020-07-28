/** 
 * @项目名称: FSP
 * @文件名称: ErrorWriter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 08. May 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters;

import java.util.Iterator;

/**
 * To aid debugging, some components are passed an ErrorWriter
 * when things go wrong, allowing them to add information
 * to the error message that may be helpful to diagnose problems.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public interface ErrorWriter {

    /**
     * Add some information to the error message. The information will be added even
     * if the identifier is already in use.
     *
     * @param name        something to identify the type of information (e.g. 'XPath').
     * @param information detail of the message (e.g. '/blah/moo[3]'
     */
    void add(String name, String information);

    /**
     * Set some information to the error message. If the identifier is already in use, the
     * new information will replace the old one.
     *
     * @param name        something to identify the type of information (e.g. 'XPath').
     * @param information detail of the message (e.g. '/blah/moo[3]'
     * @since 1.4
     */
    void set(String name, String information);

    /**
     * Retrieve information of the error message.
     * 
     * @param errorKey the key of the message
     * @return the value
     * @since 1.3
     */
    String get(String errorKey);

    /**
     * Retrieve an iterator over all keys of the error message.
     * 
     * @return an Iterator
     * @since 1.3
     */
    Iterator keys();
}
