/** 
 * @项目名称: FSP
 * @文件名称: AttributeNameIterator implements Iterator 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 24. April 2005 by Joe Walnes
 */
package uce.sso.sdk.stream.io;

import java.util.Iterator;

/**
 * Provide an iterator over the attribute names of the current node of a reader.
 *
 * @author Joe Walnes
 */
public class AttributeNameIterator implements Iterator {

    private int current;
    private final int count;
    private final HierarchicalStreamReader reader;

    public AttributeNameIterator(HierarchicalStreamReader reader) {
        this.reader = reader;
        count = reader.getAttributeCount();
    }

    public boolean hasNext() {
        return current < count;
    }

    public Object next() {
        return reader.getAttributeName(current++);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}
