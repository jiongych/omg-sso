/** 
 * @项目名称: FSP
 * @文件名称: ReferenceByXPathMarshaller extends AbstractReferenceMarshaller 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 03. April 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.core;

import uce.sso.sdk.stream.converters.ConverterLookup;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.io.path.Path;
import uce.sso.sdk.stream.mapper.Mapper;

public class ReferenceByXPathMarshaller extends AbstractReferenceMarshaller {

    private final int mode;

    public ReferenceByXPathMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper, int mode) {
        super(writer, converterLookup, mapper);
        this.mode = mode;
    }

    protected String createReference(Path currentPath, Object existingReferenceKey) {
        Path existingPath = (Path)existingReferenceKey;
        Path referencePath = (mode & ReferenceByXPathMarshallingStrategy.ABSOLUTE) > 0 ? existingPath : currentPath.relativeTo(existingPath);
        return (mode & ReferenceByXPathMarshallingStrategy.SINGLE_NODE) > 0 ? referencePath.explicit() : referencePath.toString();
    }

    protected Object createReferenceKey(Path currentPath, Object item) {
        return currentPath;
    }

    protected void fireValidReference(Object referenceKey) {
        // nothing to do
    }
}
