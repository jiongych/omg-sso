/** 
 * @项目名称: FSP
 * @文件名称: TypeHierarchyPermission implements TypePermission 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2014 XStream Committers.
 * All rights reserved.
 *
 * Created on 23. January 2014 by Joerg Schaible
 */
package uce.sso.sdk.stream.security;

/**
 * Permission for a type hierarchy with a name matching one in the provided list.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class TypeHierarchyPermission implements TypePermission {

    private Class type;

    /**
     * @since 1.4.7
     */
    public TypeHierarchyPermission(Class type) {
        this.type = type;
    }

    public boolean allows(Class type) {
        if (type == null)
            return false;
        return this.type.isAssignableFrom(type);
    }

}
