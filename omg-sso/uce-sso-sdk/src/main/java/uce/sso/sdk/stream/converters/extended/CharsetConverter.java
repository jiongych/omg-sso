/** 
 * @项目名称: FSP
 * @文件名称: CharsetConverter extends AbstractSingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. April 2006 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters.extended;

import uce.sso.sdk.stream.converters.basic.AbstractSingleValueConverter;

import java.nio.charset.Charset;

/**
 * Converts a java.nio.charset.Carset to a string.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.2
 */
public class CharsetConverter extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return Charset.class.isAssignableFrom(type);
    }

    public String toString(Object obj) {
        return obj == null ? null : ((Charset)obj).name();
    }


    public Object fromString(String str) {
        return Charset.forName(str);
    }
}