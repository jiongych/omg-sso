/** 
 * @项目名称: FSP
 * @文件名称: ConverterMatcher 
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
 * Created on 15. February 2006 by Mauro Talevi
 */
package uce.sso.sdk.stream.converters;

/**
 * ConverterMatcher allows to match converters to classes by 
 * determining if a given type can be converted by the converter instance.
 * ConverterMatcher is the base interface of any converter. 
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @author Mauro Talevi
 * @see uce.sso.sdk.stream.converters.Converter
 * @see uce.sso.sdk.stream.converters.SingleValueConverter
 * @since 1.2
 */
public interface ConverterMatcher {

    /**
     * Determines whether the converter can marshall a particular type.
     * @param type the Class representing the object type to be converted
     */
    boolean canConvert(Class type);

}