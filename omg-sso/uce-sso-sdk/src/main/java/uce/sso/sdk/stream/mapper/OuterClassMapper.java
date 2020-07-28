/** 
 * @项目名称: FSP
 * @文件名称: OuterClassMapper extends MapperWrapper 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 31. January 2005 by Joe Walnes
 */
package uce.sso.sdk.stream.mapper;

/**
 * Mapper that uses a more meaningful alias for the field in an inner class (this$0) that refers to the outer class.
 *
 * @author Joe Walnes
 */
public class OuterClassMapper extends MapperWrapper {

    private final String alias;

    public OuterClassMapper(Mapper wrapped) {
        this(wrapped, "outer-class");
    }

    public OuterClassMapper(Mapper wrapped, String alias) {
        super(wrapped);
        this.alias = alias;
    }

    public String serializedMember(Class type, String memberName) {
        if (memberName.equals("this$0")) {
            return alias;
        } else {
            return super.serializedMember(type, memberName);
        }
    }

    public String realMember(Class type, String serialized) {
        if (serialized.equals(alias)) {
            return "this$0";
        } else {
            return super.realMember(type, serialized);
        }
    }
}
