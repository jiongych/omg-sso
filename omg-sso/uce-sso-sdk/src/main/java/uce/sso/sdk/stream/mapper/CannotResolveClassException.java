/** 
 * @项目名称: FSP
 * @文件名称: CannotResolveClassException extends XStreamException 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009, 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26. September 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.mapper;

import uce.sso.sdk.stream.XStreamException;

/**
 * Exception thrown if a mapper cannot locate the appropriate class for an element.
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @since 1.2
 */
public class CannotResolveClassException extends XStreamException {
    public CannotResolveClassException(String className) {
        super(className);
    }
    /**
     * @since 1.4.2
     */
    public CannotResolveClassException(String className, Throwable cause) {
        super(className, cause);
    }
}
