/** 
 * @项目名称: FSP
 * @文件名称: GregorianCalendarConverter implements Converter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 24. July 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.extended;

import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.ExtendedHierarchicalStreamWriterHelper;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Converts a java.util.GregorianCalendar to XML. Note that although it currently only contains one field, it nests
 * it inside a child element, to allow for other fields to be stored in the future.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class GregorianCalendarConverter implements Converter {

    public boolean canConvert(Class type) {
        return type.equals(GregorianCalendar.class);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        GregorianCalendar calendar = (GregorianCalendar) source;
        ExtendedHierarchicalStreamWriterHelper.startNode(writer, "time", long.class);
        long timeInMillis = calendar.getTime().getTime(); // calendar.getTimeInMillis() not available under JDK 1.3
        writer.setValue(String.valueOf(timeInMillis));
        writer.endNode();
        ExtendedHierarchicalStreamWriterHelper.startNode(writer, "timezone", String.class);
        writer.setValue(calendar.getTimeZone().getID());
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        long timeInMillis = Long.parseLong(reader.getValue());
        reader.moveUp();
        final String timeZone;
        if (reader.hasMoreChildren()) {
            reader.moveDown();
            timeZone = reader.getValue();
            reader.moveUp();
        } else { // backward compatibility to XStream 1.1.2 and below
            timeZone = TimeZone.getDefault().getID();
        }

        GregorianCalendar result = new GregorianCalendar();
        result.setTimeZone(TimeZone.getTimeZone(timeZone));
        result.setTime(new Date(timeInMillis)); // calendar.setTimeInMillis() not available under JDK 1.3

        return result;
    }

}
