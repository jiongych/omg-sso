/** 
 * @项目名称: FSP
 * @文件名称: InitializationException extends XStream.InitializationException 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2007, 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 22. October 2007 by Joerg Schaible
 */
package uce.sso.sdk.stream;

/**
 * Exception thrown configuring an XStream instance.
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @since 1.3
 */
/**
 * InitializationException extends XStream.InitializationException  
 * @Description: InitializationException extends XStream.InitializationException  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class InitializationException extends XStream.InitializationException {
    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializationException(String message) {
        super(message);
    }
}
