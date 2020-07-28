/** 
 * @项目名称: FSP
 * @文件名称: StreamException extends XStreamException 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009, 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.io;

import uce.sso.sdk.stream.XStreamException;

/**
 * StreamException extends XStreamException  
 * @Description: StreamException extends XStreamException  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class StreamException extends XStreamException {
    public StreamException(Throwable e) {
        super(e);
    }

    public StreamException(String message) {
        super(message);
    }

    /**
     * @since 1.4
     */
    public StreamException(String message, Throwable cause) {
        super(message, cause);
    }
}
