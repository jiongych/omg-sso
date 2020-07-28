/** 
 * @项目名称: FSP
 * @文件名称: StringBuilderConverter extends AbstractSingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 04. January 2008 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters.basic;

/**
 * Converts the contents of a StringBuilder to XML.
 *
 * @author J&ouml;rg Schaible
 */
public class StringBuilderConverter extends AbstractSingleValueConverter {

    public Object fromString(String str) {
        return new StringBuilder(str);
    }

    public boolean canConvert(Class type) {
        return type.equals(StringBuilder.class);
    }
}
