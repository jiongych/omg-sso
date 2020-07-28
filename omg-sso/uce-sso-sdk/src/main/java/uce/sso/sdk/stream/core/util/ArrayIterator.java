/** 
 * @项目名称: FSP
 * @文件名称: ArrayIterator implements Iterator 
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
 * Created on 29.07.2011 by Joerg Schaible
 */
package uce.sso.sdk.stream.core.util;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * @author J&ouml;rg Schaible
 *
 * @since 1.4
 */
public class ArrayIterator implements Iterator {
    private final Object array;
    private int idx;
    private int length;
    public ArrayIterator(Object array) {
        this.array = array;
        length = Array.getLength(array);
    }

    public boolean hasNext() {
        return idx < length;
    }

    public Object next() {
        return Array.get(array, idx++);
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove from array"); 
    }
}
