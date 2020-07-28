/** 
 * @项目名称: FSP
 * @文件名称: IntConverter extends AbstractSingleValueConverter 
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

/**
 * Converts an int primitive or java.lang.Integer wrapper to
 * a String.
 *
 * @author Joe Walnes
 */
public class IntConverter extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(int.class) || type.equals(Integer.class);
    }

    public Object fromString(String str) {
    	long value = Long.decode(str).longValue();
    	if(value < Integer.MIN_VALUE || value > 0xFFFFFFFFl) {
    		throw new NumberFormatException("For input string: \"" + str + '"');
    	}
        return new Integer((int)value);
    }

}
