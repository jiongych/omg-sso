/** 
 * @项目名称: FSP
 * @文件名称: NoTypePermission implements TypePermission 
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
 * No permission for any type.
 * <p>
 * Can be used to skip any existing default permission.
 * </p>
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class NoTypePermission implements TypePermission {

    /**
     * @since 1.4.7
     */
    public static final TypePermission NONE = new NoTypePermission();

    public boolean allows(Class type) {
        throw new ForbiddenClassException(type);
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == NoTypePermission.class;
    }
}
