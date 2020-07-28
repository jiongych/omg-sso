/** 
 * @项目名称: FSP
 * @文件名称: PathTrackingWriter extends WriterWrapper 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.io.path;

import uce.sso.sdk.stream.io.AbstractWriter;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.io.WriterWrapper;

/**
 * Wrapper for HierarchicalStreamWriter that tracks the path (a subset of XPath) of the current node that is being written.
 *
 * @see PathTracker
 * @see Path
 *
 * @author Joe Walnes
 */
public class PathTrackingWriter extends WriterWrapper {

    private final PathTracker pathTracker;
    private final boolean isNameEncoding;

    public PathTrackingWriter(HierarchicalStreamWriter writer, PathTracker pathTracker) {
        super(writer);
        this.isNameEncoding = writer.underlyingWriter() instanceof AbstractWriter;
        this.pathTracker = pathTracker;
    }

    public void startNode(String name) {
        pathTracker.pushElement(isNameEncoding ? ((AbstractWriter)wrapped.underlyingWriter()).encodeNode(name) : name);
        super.startNode(name); 
    }

    public void startNode(String name, Class clazz) {
        pathTracker.pushElement(isNameEncoding ? ((AbstractWriter)wrapped.underlyingWriter()).encodeNode(name) : name);
        super.startNode(name, clazz);
    }

    public void endNode() {
        super.endNode();
        pathTracker.popElement();
    }
}
