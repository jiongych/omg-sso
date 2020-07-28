/** 
 * @项目名称: FSP
 * @文件名称: CharArrayConverter implements Converter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 06. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.collections;

import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;

/**
 * Converts a char[] to XML, storing the contents as a single
 * String.
 *
 * @author Joe Walnes
 */
public class CharArrayConverter implements Converter {

    public boolean canConvert(Class type) {
        return type.isArray() && type.getComponentType().equals(char.class);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        char[] chars = (char[]) source;
        writer.setValue(new String(chars));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return reader.getValue().toCharArray();
    }
}
