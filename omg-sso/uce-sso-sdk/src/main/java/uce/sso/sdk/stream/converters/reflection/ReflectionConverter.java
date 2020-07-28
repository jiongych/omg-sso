/** 
 * @项目名称: FSP
 * @文件名称: ReflectionConverter extends AbstractReflectionConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2013, 2014 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.reflection;

import uce.sso.sdk.stream.core.JVM;
import uce.sso.sdk.stream.mapper.Mapper;

/**
 * ReflectionConverter extends AbstractReflectionConverter  
 * @Description: ReflectionConverter extends AbstractReflectionConverter  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class ReflectionConverter extends AbstractReflectionConverter {

    // Might be missing in Android
    private final static Class eventHandlerType = JVM.loadClassForName("java.beans.EventHandler");
    private Class type;

    public ReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    /**
     * Construct a ReflectionConverter for an explicit type.
     * 
     * @param mapper the mapper in use
     * @param reflectionProvider the reflection provider in use
     * @param type the explicit type to handle
     * @since 1.4.7
     */
    public ReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider, Class type) {
        this(mapper, reflectionProvider);
        this.type = type;
    }

    public boolean canConvert(Class type) {
        return ((this.type != null && this.type == type) || (this.type == null && type != null && type != eventHandlerType))
            && canAccess(type);
    }
}
