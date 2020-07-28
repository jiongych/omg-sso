/** 
 * @项目名称: FSP
 * @文件名称: EnumSingleValueConverter extends AbstractSingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2008, 2009, 2010, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 12. February 2008 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters.enums;

import uce.sso.sdk.stream.converters.basic.AbstractSingleValueConverter;


/**
 * A single value converter for a special enum type. Converter is internally automatically
 * instantiated for enum types.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.3
 */
public class EnumSingleValueConverter extends AbstractSingleValueConverter {

    private final Class<? extends Enum> enumType;

    public EnumSingleValueConverter(Class<? extends Enum> type) {
        if (!Enum.class.isAssignableFrom(type) && type != Enum.class) {
            throw new IllegalArgumentException("Converter can only handle defined enums");
        }
        enumType = type;
    }

    @Override
    public boolean canConvert(Class type) {
        return enumType.isAssignableFrom(type);
    }

    @Override
    public String toString(Object obj) {
        return Enum.class.cast(obj).name();
    }

    @Override
    public Object fromString(String str) {
        @SuppressWarnings("unchecked")
        Enum result = Enum.valueOf(enumType, str);
        return result;
    }
}
