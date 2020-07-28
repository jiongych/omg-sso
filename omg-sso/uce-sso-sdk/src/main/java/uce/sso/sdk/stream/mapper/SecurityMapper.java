/** 
 * @项目名称: FSP
 * @文件名称: SecurityMapper extends MapperWrapper 
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
package uce.sso.sdk.stream.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uce.sso.sdk.stream.security.AnyTypePermission;
import uce.sso.sdk.stream.security.ForbiddenClassException;
import uce.sso.sdk.stream.security.NoTypePermission;
import uce.sso.sdk.stream.security.TypePermission;


/**
 * A Mapper implementation injecting a security layer based on permission rules for any type required in the
 * unmarshalling process.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class SecurityMapper extends MapperWrapper {

    private final List permissions;

    /**
     * Construct a SecurityMapper.
     * 
     * @param wrapped the mapper chain
     * @since 1.4.7
     */
    public SecurityMapper(final Mapper wrapped) {
        this(wrapped, (TypePermission[])null);
    }

    /**
     * Construct a SecurityMapper.
     * 
     * @param wrapped the mapper chain
     * @param permissions the predefined permissions
     * @since 1.4.7
     */
    public SecurityMapper(final Mapper wrapped, final TypePermission[] permissions) {
        super(wrapped);
        this.permissions = permissions == null //
            ? new ArrayList()
            : new ArrayList(Arrays.asList(permissions));
    }

    /**
     * Add a new permission.
     * <p>
     * Permissions are evaluated in the added sequence. An instance of {@link NoTypePermission} or
     * {@link AnyTypePermission} will implicitly wipe any existing permission.
     * </p>
     * 
     * @param permission the permission to add.
     * @since 1.4.7
     */
    public void addPermission(final TypePermission permission) {
        if (permission.equals(NoTypePermission.NONE) || permission.equals(AnyTypePermission.ANY))
            permissions.clear();
        permissions.add(0, permission);
    }

    public Class realClass(final String elementName) {
        final Class type = super.realClass(elementName);
        for (int i = 0; i < permissions.size(); ++i) {
            final TypePermission permission = (TypePermission)permissions.get(i);
            if (permission.allows(type))
                return type;
        }
        throw new ForbiddenClassException(type);
    }
}
