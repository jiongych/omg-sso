/** 
 * @项目名称: FSP
 * @文件名称: ImmutableFieldKeySorter implements FieldKeySorter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 10. April 2007 by Guilherme Silveira
 */
package uce.sso.sdk.stream.converters.reflection;

import java.util.Map;

/**
 * Does not change the order of the fields.
 *
 * @author Guilherme Silveira
 * @since 1.2.2
 */
public class ImmutableFieldKeySorter implements FieldKeySorter {

	public Map sort(Class type, Map keyedByFieldKey) {
		return keyedByFieldKey;
	}

}
