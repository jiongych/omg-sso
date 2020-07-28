/** 
 * @项目名称: FSP
 * @文件名称: EnumMapConverter extends MapConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 06. April 2005 by Joe Walnes
 */

// ***** READ THIS *****
// This class will only compile with JDK 1.5.0 or above as it test Java enums.
// If you are using an earlier version of Java, just don't try to build this class. XStream should work fine without it.

package uce.sso.sdk.stream.converters.enums;

import uce.sso.sdk.stream.converters.collections.MapConverter;
import uce.sso.sdk.stream.converters.ConversionException;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.mapper.Mapper;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.core.util.Fields;

import java.util.EnumMap;
import java.lang.reflect.Field;

/**
 * Serializes an Java 5 EnumMap, including the type of Enum it's for. If a SecurityManager is set, the converter will only work with permissions
 * for SecurityManager.checkPackageAccess, SecurityManager.checkMemberAccess(this, EnumSet.MEMBER)
 * and ReflectPermission("suppressAccessChecks").
 *
 * @author Joe Walnes
 */
public class EnumMapConverter extends MapConverter {

    private final static Field typeField = Fields.locate(EnumMap.class, Class.class, false);

    public EnumMapConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class type) {
        return typeField != null && type == EnumMap.class;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Class type = (Class) Fields.read(typeField, source);
        String attributeName = mapper().aliasForSystemAttribute("enum-type");
        if (attributeName != null) {
            writer.addAttribute(attributeName, mapper().serializedClass(type));
        }
        super.marshal(source, writer, context);
    }

    @SuppressWarnings("unchecked")
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String attributeName = mapper().aliasForSystemAttribute("enum-type");
        if (attributeName == null) {
            throw new ConversionException("No EnumType specified for EnumMap");
        }
        Class type = mapper().realClass(reader.getAttribute(attributeName));
        EnumMap map = new EnumMap(type);
        populateMap(reader, context, map);
        return map;
    }
}
