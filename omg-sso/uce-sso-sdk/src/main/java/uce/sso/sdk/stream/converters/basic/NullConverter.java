/** 
 * @项目名称: FSP
 * @文件名称: NullConverter implements Converter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2003, 2004 Joe Walnes.
 * Copyright (C) 2006, 2007, 2012 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 03. October 2003 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.basic;

import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.io.ExtendedHierarchicalStreamWriterHelper;
import uce.sso.sdk.stream.mapper.Mapper;

/**
 * Special converter to signify nulls at the root level.
 *
 * @author Joe Walnes
 */
public class NullConverter implements Converter {

    public boolean canConvert(Class type) {
        return type == null || Mapper.Null.class.isAssignableFrom(type);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        ExtendedHierarchicalStreamWriterHelper.startNode(writer, "null", Mapper.Null.class);
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }
}
