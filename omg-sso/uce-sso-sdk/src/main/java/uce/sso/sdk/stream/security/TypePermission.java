/** 
 * @项目名称: FSP
 * @文件名称: TypePermission 
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

/**
 * Definition of a type permission. 
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public interface TypePermission {
    /**
     * Check permission for a provided type.
     * 
     * @param type the type to check
     * @return <code>true</code> if provided type is allowed, <code>false</code> if permission does not handle the type
     * @throws ForbiddenClassException if provided type is explicitly forbidden
     * @since 1.4.7
     */
    boolean allows(Class type);
}
