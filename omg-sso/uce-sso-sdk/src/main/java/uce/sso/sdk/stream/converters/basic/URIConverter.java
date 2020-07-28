/** 
 * @项目名称: FSP
 * @文件名称: URIConverter extends AbstractSingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2010 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 3. August 2010 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters.basic;

import java.net.URI;
import java.net.URISyntaxException;

import uce.sso.sdk.stream.converters.ConversionException;


/**
 * Converts a java.net.URI to a string.
 * 
 * @author Carlos Roman
 */
public class URIConverter extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(URI.class);
    }

    public Object fromString(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            throw new ConversionException(e);
        }
    }
}
