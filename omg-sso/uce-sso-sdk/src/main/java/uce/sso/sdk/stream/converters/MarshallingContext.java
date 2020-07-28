/** 
 * @项目名称: FSP
 * @文件名称: MarshallingContext extends DataHolder 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 06. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters;

/**
 * MarshallingContext extends DataHolder  
 * @Description: MarshallingContext extends DataHolder  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface MarshallingContext extends DataHolder {

	/**
	 * Converts another object searching for the default converter
	 * @param nextItem	the next item to convert
	 */
    void convertAnother(Object nextItem);
    
    /**
     * Converts another object using the specified converter
     * @param nextItem	the next item to convert
     * @param converter	the Converter to use
     * @since 1.2
     */
    void convertAnother(Object nextItem, Converter converter);

}
