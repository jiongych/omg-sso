/** 
 * @项目名称: FSP
 * @文件名称: MarshallingStrategy 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2006 Joe Walnes.
 * Copyright (C) 2007, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 16. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream;

import uce.sso.sdk.stream.converters.ConverterLookup;
import uce.sso.sdk.stream.converters.DataHolder;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.mapper.Mapper;

public interface MarshallingStrategy {

    Object unmarshal(Object root, HierarchicalStreamReader reader, DataHolder dataHolder, ConverterLookup converterLookup, Mapper mapper);
    void marshal(HierarchicalStreamWriter writer, Object obj, ConverterLookup converterLookup, Mapper mapper, DataHolder dataHolder);
}
