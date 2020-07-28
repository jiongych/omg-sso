/** 
 * @项目名称: FSP
 * @文件名称: CharConverter implements Converter, SingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003, 2004 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26. September 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.basic;

import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.SingleValueConverter;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;

/**
 * Converts a char primitive or java.lang.Character wrapper to
 * a String. If char is \0 the representing String is empty.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class CharConverter implements Converter, SingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(char.class) || type.equals(Character.class);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.setValue(toString(source));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String nullAttribute = reader.getAttribute("null");
        if (nullAttribute != null && nullAttribute.equals("true")) {
            return new Character('\0');
        } else {
            return fromString(reader.getValue());
        }
    }

    public Object fromString(String str) {
        if (str.length() == 0) {
            return new Character('\0');
        } else {
            return new Character(str.charAt(0));
        }
    }

    public String toString(Object obj) {
        char ch = ((Character)obj).charValue();
        return ch == '\0' ? "" : obj.toString();
    }

}
