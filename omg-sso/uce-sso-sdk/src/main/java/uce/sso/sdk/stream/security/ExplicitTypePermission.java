/** 
 * @项目名称: FSP
 * @文件名称: ExplicitTypePermission implements TypePermission 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2014 XStream Committers.
 * All rights reserved.
 *
 * Created on 09. January 2014 by Joerg Schaible
 */
package uce.sso.sdk.stream.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Explicit permission for a type with a name matching one in the provided list.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class ExplicitTypePermission implements TypePermission {

    final Set names;

    /**
     * @since 1.4.7
     */
    public ExplicitTypePermission(final Class[] types) {
        this(new Object() {
            public String[] getNames() {
                if (types == null)
                    return null;
                String[] names = new String[types.length];
                for (int i = 0; i < types.length; ++i)
                    names[i] = types[i].getName();
                return names;
            }
        }.getNames());
    }

    /**
     * @since 1.4.7
     */
    public ExplicitTypePermission(String[] names) {
        this.names = names == null ? Collections.EMPTY_SET : new HashSet(Arrays.asList(names));
    }

    public boolean allows(Class type) {
        if (type == null)
            return false;
        return names.contains(type.getName());
    }

}
