/** 
 * @项目名称: FSP
 * @文件名称: PathTrackingReader extends ReaderWrapper 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 03. April 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.io.path;

import uce.sso.sdk.stream.converters.ErrorWriter;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.ReaderWrapper;

/**
 * Wrapper for HierarchicalStreamReader that tracks the path (a subset of XPath) of the current node that is being read.
 *
 * @see PathTracker
 * @see Path
 *
 * @author Joe Walnes
 */
public class PathTrackingReader extends ReaderWrapper {

    private final PathTracker pathTracker;

    public PathTrackingReader(HierarchicalStreamReader reader, PathTracker pathTracker) {
        super(reader);
        this.pathTracker = pathTracker;
        pathTracker.pushElement(getNodeName());
    }

    public void moveDown() {
        super.moveDown();
        pathTracker.pushElement(getNodeName());
    }

    public void moveUp() {
        super.moveUp();
        pathTracker.popElement();
    }

    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("path", pathTracker.getPath().toString());
        super.appendErrors(errorWriter);
    }

}
