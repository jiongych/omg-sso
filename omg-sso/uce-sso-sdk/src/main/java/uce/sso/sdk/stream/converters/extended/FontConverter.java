/** 
 * @项目名称: FSP
 * @文件名称: FontConverter implements Converter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2013 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 08. July 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.extended;

import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.SingleValueConverter;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.core.JVM;
import uce.sso.sdk.stream.io.ExtendedHierarchicalStreamWriterHelper;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;
import uce.sso.sdk.stream.mapper.Mapper;

import javax.swing.plaf.FontUIResource;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * FontConverter implements Converter  
 * @Description: FontConverter implements Converter  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class FontConverter implements Converter {

    private final SingleValueConverter textAttributeConverter;
    private final Mapper mapper;

    /**
     * Constructs a FontConverter.
     * @deprecated As of 1.4.5
     */
    public FontConverter() {
        this(null);
    }

    /**
     * Constructs a FontConverter.
     * @param mapper
     * @since 1.4.5
     */
    public FontConverter(Mapper mapper) {
        this.mapper = mapper;
        if (mapper == null) {
            textAttributeConverter = null;
        } else {
            textAttributeConverter = new TextAttributeConverter();
        }
    }
    
    public boolean canConvert(Class type) {
        // String comparison is used here because Font.class loads the class which in turns instantiates AWT,
        // which is nasty if you don't want it.
        return type.getName().equals("java.awt.Font") || type.getName().equals("javax.swing.plaf.FontUIResource");
    }

    public void marshal(Object source, HierarchicalStreamWriter writer,
        MarshallingContext context) {
        Font font = (Font)source;
        Map attributes = font.getAttributes();
        if (mapper != null) {
            String classAlias = mapper.aliasForSystemAttribute("class");
            for (Iterator iter = attributes.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry)iter.next();
                String name = textAttributeConverter.toString(entry.getKey());
                Object value = entry.getValue();
                Class type = value != null ? value.getClass() : Mapper.Null.class;
                ExtendedHierarchicalStreamWriterHelper.startNode(writer, name, type);
                writer.addAttribute(classAlias, mapper.serializedClass(type));
                if (value != null) {
                    context.convertAnother(value);
                }
                writer.endNode();
            }
        } else {
            writer.startNode("attributes"); // <attributes>
            context.convertAnother(attributes);
            writer.endNode(); // </attributes>
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        final Map attributes;
        if (reader.hasMoreChildren()) {
            reader.moveDown();
            if (!reader.getNodeName().equals("attributes")) {
                String classAlias = mapper.aliasForSystemAttribute("class");
                attributes = new HashMap();
                do {
                    if (!attributes.isEmpty()) {
                        reader.moveDown();
                    }
                    Class type = mapper.realClass(reader.getAttribute(classAlias));
                    TextAttribute attribute = (TextAttribute)textAttributeConverter.fromString(reader.getNodeName());
                    Object value = type == Mapper.Null.class ? null : context.convertAnother(null, type);
                    attributes.put(attribute, value);
                    reader.moveUp();
                } while(reader.hasMoreChildren());
            } else {
                // in <attributes>
                attributes = (Map)context.convertAnother(null, Map.class);
                reader.moveUp(); // out of </attributes>
            }
        } else {
            attributes = Collections.EMPTY_MAP;
        }
        if (!JVM.is16()) {
            for (Iterator iter = attributes.values().iterator(); iter.hasNext(); ) {
                if (iter.next() == null) {
                    iter.remove();
                }
            }
        }
        Font font = Font.getFont(attributes);
        if (context.getRequiredType() == FontUIResource.class) {
            return new FontUIResource(font);
        } else {
            return font;
        }
    }
}
