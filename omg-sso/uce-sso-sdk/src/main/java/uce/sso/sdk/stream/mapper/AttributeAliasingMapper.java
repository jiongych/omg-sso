/** 
 * @项目名称: FSP
 * @文件名称: AttributeAliasingMapper extends AbstractAttributeAliasingMapper 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 27. March 2006 by Joerg Schaible
 */
package uce.sso.sdk.stream.mapper;



/**
 * Mapper that allows aliasing of attribute names.
 * 
 * @author J&ouml;rg Schaible
 * @author Guilherme Silveira
 * @since 1.2
 */
public class AttributeAliasingMapper extends AbstractAttributeAliasingMapper {

    public AttributeAliasingMapper(Mapper wrapped) {
        super(wrapped);
    }

    public String aliasForAttribute(String attribute) {
        String alias = (String)nameToAlias.get(attribute);
        return alias == null ? super.aliasForAttribute(attribute) : alias;
    }

    public String attributeForAlias(String alias) {
        String name = (String)aliasToName.get(alias);
        return name == null ? super.attributeForAlias(alias) : name;
    }
}
