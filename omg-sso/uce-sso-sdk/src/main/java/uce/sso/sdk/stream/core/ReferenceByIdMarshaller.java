/** 
 * @项目名称: FSP
 * @文件名称: ReferenceByIdMarshaller extends AbstractReferenceMarshaller 
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
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.io.path.Path;
import uce.sso.sdk.stream.mapper.Mapper;

public class ReferenceByIdMarshaller extends AbstractReferenceMarshaller {

    private final IDGenerator idGenerator;

    public static interface IDGenerator {
        String next(Object item);
    }

    public ReferenceByIdMarshaller(HierarchicalStreamWriter writer,
                                   ConverterLookup converterLookup,
                                   Mapper mapper,
                                   IDGenerator idGenerator) {
        super(writer, converterLookup, mapper);
        this.idGenerator = idGenerator;
    }

    public ReferenceByIdMarshaller(HierarchicalStreamWriter writer,
                                   ConverterLookup converterLookup,
                                   Mapper mapper) {
        this(writer, converterLookup, mapper, new SequenceGenerator(1));
    }

    protected String createReference(Path currentPath, Object existingReferenceKey) {
        return existingReferenceKey.toString();
    }

    protected Object createReferenceKey(Path currentPath, Object item) {
        return idGenerator.next(item);
    }

    protected void fireValidReference(Object referenceKey) {
        String attributeName = getMapper().aliasForSystemAttribute("id");
        if (attributeName != null) {
            writer.addAttribute(attributeName, referenceKey.toString());
        }
    }
}
