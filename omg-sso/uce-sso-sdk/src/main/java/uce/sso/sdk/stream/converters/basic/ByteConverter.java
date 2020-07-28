/** 
 * @项目名称: FSP
 * @文件名称: ByteConverter extends AbstractSingleValueConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003, 2004, 2005, 2006 Joe Walnes.
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

/**
 * Converts a byte primitive or java.lang.Byte wrapper to
 * a String.
 *
 * @author Joe Walnes
 */
public class ByteConverter extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(byte.class) || type.equals(Byte.class);
    }

    public Object fromString(String str) {
    	int value = Integer.decode(str).intValue();
    	if(value < Byte.MIN_VALUE || value > 0xFF) {
    		throw new NumberFormatException("For input string: \"" + str + '"');
    	}
        return new Byte((byte)value);
    }

}
