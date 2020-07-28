/** 
 * @项目名称: FSP
 * @文件名称: ReferenceByIdUnmarshaller extends AbstractReferenceUnmarshaller 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 15. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.core;

import uce.sso.sdk.stream.converters.ConverterLookup;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.mapper.Mapper;

public class ReferenceByIdUnmarshaller extends AbstractReferenceUnmarshaller {

    public ReferenceByIdUnmarshaller(Object root, HierarchicalStreamReader reader,
                                     ConverterLookup converterLookup, Mapper mapper) {
        super(root, reader, converterLookup, mapper);
    }

    protected Object getReferenceKey(String reference) {
        return reference;
    }

    protected Object getCurrentReferenceKey() {
        String attributeName = getMapper().aliasForSystemAttribute("id");
        return attributeName == null ? null : reader.getAttribute(attributeName);
    }
}
