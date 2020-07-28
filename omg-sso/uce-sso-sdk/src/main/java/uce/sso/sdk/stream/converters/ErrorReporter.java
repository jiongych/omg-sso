/** 
 * @项目名称: FSP
 * @文件名称: ErrorReporter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 15.02.2011 by Joerg Schaible
 */
package uce.sso.sdk.stream.converters;

/**
 * To aid debugging, some components expose themselves as ErrorReporter
 * indicating that they can add information in case of an error..
 * 
 * @author Joerg Schaible
 *
 * @since 1.4
 */
public interface ErrorReporter {
    /**
     * Append context information to an {@link ErrorWriter}.
     * 
     * @param errorWriter the error writer
     * @since 1.4
     */
    void appendErrors(ErrorWriter errorWriter);
}
