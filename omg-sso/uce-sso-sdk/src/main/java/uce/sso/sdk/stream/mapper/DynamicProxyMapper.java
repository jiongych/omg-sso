/** 
 * @项目名称: FSP
 * @文件名称: DynamicProxyMapper extends MapperWrapper 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 22. January 2005 by Joe Walnes
 */
package uce.sso.sdk.stream.mapper;

import java.lang.reflect.Proxy;

/**
 * Mapper for handling special cases of aliasing dynamic proxies. The alias property specifies the name an instance
 * of a dynamic proxy should be serialized with.
 *
 * @author Joe Walnes
 */
public class DynamicProxyMapper extends MapperWrapper {

    private String alias;

    public DynamicProxyMapper(Mapper wrapped) {
        this(wrapped, "dynamic-proxy");
    }

    public DynamicProxyMapper(Mapper wrapped, String alias) {
        super(wrapped);
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String serializedClass(Class type) {
        if (Proxy.isProxyClass(type)) {
            return alias;
        } else {
            return super.serializedClass(type);
        }
    }

    public Class realClass(String elementName) {
        if (elementName.equals(alias)) {
            return DynamicProxy.class;
        } else {
            return super.realClass(elementName);
        }
    }

    /**
     * Place holder type used for dynamic proxies.
     */
    public static class DynamicProxy {}

}
