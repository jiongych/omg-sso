/** 
 * @项目名称: FSP
 * @文件名称: AnyTypePermission implements TypePermission 
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
 * Permission for any type and <code>null</code>.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class AnyTypePermission implements TypePermission {
    /**
     * @since 1.4.7
     */
    public static final TypePermission ANY = new AnyTypePermission();

    public boolean allows(Class type) {
        return true;
    }

    public int hashCode() {
        return 3;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == AnyTypePermission.class;
    }
}
