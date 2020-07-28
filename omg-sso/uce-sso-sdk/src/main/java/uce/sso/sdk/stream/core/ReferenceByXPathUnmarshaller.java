/** 
 * @项目名称: FSP
 * @文件名称: ReferenceByXPathUnmarshaller extends AbstractReferenceUnmarshaller 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009, 2011 XStream Committers.
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
import uce.sso.sdk.stream.io.AbstractReader;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.path.Path;
import uce.sso.sdk.stream.io.path.PathTracker;
import uce.sso.sdk.stream.io.path.PathTrackingReader;
import uce.sso.sdk.stream.mapper.Mapper;

public class ReferenceByXPathUnmarshaller extends AbstractReferenceUnmarshaller {

    private PathTracker pathTracker = new PathTracker();
    protected boolean isNameEncoding;

    public ReferenceByXPathUnmarshaller(Object root, HierarchicalStreamReader reader,
                                        ConverterLookup converterLookup, Mapper mapper) {
        super(root, reader, converterLookup, mapper);
        this.reader = new PathTrackingReader(reader, pathTracker);
        isNameEncoding = reader.underlyingReader() instanceof AbstractReader;
    }

    protected Object getReferenceKey(String reference) {
        final Path path = new Path(isNameEncoding ? ((AbstractReader)reader.underlyingReader()).decodeNode(reference) : reference);
        // We have absolute references, if path starts with '/'
        return reference.charAt(0) != '/' ? pathTracker.getPath().apply(path) : path;
    }

    protected Object getCurrentReferenceKey() {
        return pathTracker.getPath();
    }

}
