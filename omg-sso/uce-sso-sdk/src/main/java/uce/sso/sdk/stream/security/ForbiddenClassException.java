/** 
 * @项目名称: FSP
 * @文件名称: ForbiddenClassException extends XStreamException 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2014 XStream Committers.
 * All rights reserved.
 *
 * Created on 08. January 2014 by Joerg Schaible
 */
package uce.sso.sdk.stream.security;

import uce.sso.sdk.stream.XStreamException;

/**
 * Exception thrown for a forbidden class.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class ForbiddenClassException extends XStreamException {

    /**
     * Construct a ForbiddenClassException.
     * @param type the forbidden class
     * @since 1.4.7
     */
    public ForbiddenClassException(Class type) {
        super(type == null ? "null" : type.getName());
    }
}
