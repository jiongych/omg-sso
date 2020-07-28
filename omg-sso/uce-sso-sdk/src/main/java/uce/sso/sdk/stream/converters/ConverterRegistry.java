/** 
 * @项目名称: FSP
 * @文件名称: ConverterRegistry 
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
 * Created on 01. January 2008 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters;

/**
 * An interface for the converter management.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.3
 */
public interface ConverterRegistry {

    void registerConverter(Converter converter, int priority);

}
