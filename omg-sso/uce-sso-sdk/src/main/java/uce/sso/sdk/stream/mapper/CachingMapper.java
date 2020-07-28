/** 
 * @项目名称: FSP
 * @文件名称: CachingMapper extends MapperWrapper implements Caching 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009, 2011, 2013, 2014 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 22. January 2005 by Joe Walnes
 */
package uce.sso.sdk.stream.mapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uce.sso.sdk.stream.XStreamException;
import uce.sso.sdk.stream.core.Caching;
import uce.sso.sdk.stream.security.ForbiddenClassException;

/**
 * Mapper that caches which names map to which classes. Prevents repetitive searching and class loading.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class CachingMapper extends MapperWrapper implements Caching {

    private transient Map realClassCache;

    public CachingMapper(Mapper wrapped) {
        super(wrapped);
        readResolve();
    }

    public Class realClass(String elementName) {
        Object cached = realClassCache.get(elementName);
        if (cached != null) {
            if (cached instanceof Class) {
                return (Class)cached;
            }
            throw (XStreamException)cached;
        }

        try {
            Class result = super.realClass(elementName);
            realClassCache.put(elementName, result);
            return result;
        } catch (ForbiddenClassException e) {
            realClassCache.put(elementName, e);
            throw e;
        } catch (CannotResolveClassException e) {
            realClassCache.put(elementName, e);
            throw e;
        }
    }

    public void flushCache() {
        realClassCache.clear();
    }

    private Object readResolve() {
        realClassCache = Collections.synchronizedMap(new HashMap(128));
        return this;
    }
}
