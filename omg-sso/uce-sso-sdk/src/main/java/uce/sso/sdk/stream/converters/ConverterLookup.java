/** 
 * @项目名称: FSP
 * @文件名称: ConverterLookup 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003, 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26. September 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.converters;

/**
 * Responsible for looking up the correct Converter implementation for a specific type.
 *
 * @author Joe Walnes
 * @see Converter
 */
public interface ConverterLookup {

    /**
     * Lookup a converter for a specific type.
     * <p>
     * This type may be any Class, including primitive and array types. It may also be null, signifying
     * the value to be converted is a null type.
     * </p>
     */
    Converter lookupConverterForType(Class type);
}
