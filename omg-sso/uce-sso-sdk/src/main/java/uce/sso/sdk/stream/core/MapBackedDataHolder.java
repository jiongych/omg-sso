/** 
 * @项目名称: FSP
 * @文件名称: MapBackedDataHolder implements DataHolder 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 04. October 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.core;

import uce.sso.sdk.stream.converters.DataHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapBackedDataHolder implements DataHolder {
    private final Map map;

    public MapBackedDataHolder() {
        this(new HashMap());
    }

    public MapBackedDataHolder(Map map) {
        this.map = map;
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public void put(Object key, Object value) {
        map.put(key, value);
    }

    public Iterator keys() {
        return Collections.unmodifiableCollection(map.keySet()).iterator();
    }
}
