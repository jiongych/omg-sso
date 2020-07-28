/** 
 * @项目名称: FSP
 * @文件名称: BitSetConverter implements Converter 
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
 * Created on 07. March 2004 by Joe Walnes
 */
package uce.sso.sdk.stream.converters.collections;

import uce.sso.sdk.stream.converters.Converter;
import uce.sso.sdk.stream.converters.MarshallingContext;
import uce.sso.sdk.stream.converters.UnmarshallingContext;
import uce.sso.sdk.stream.io.HierarchicalStreamReader;
import uce.sso.sdk.stream.io.HierarchicalStreamWriter;

import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * Converts a java.util.BitSet to XML, as a compact
 * comma delimited list of ones and zeros.
 *
 * @author Joe Walnes
 */
public class BitSetConverter implements Converter {

    public boolean canConvert(Class type) {
        return type.equals(BitSet.class);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        BitSet bitSet = (BitSet) source;
        StringBuffer buffer = new StringBuffer();
        boolean seenFirst = false;
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                if (seenFirst) {
                    buffer.append(',');
                } else {
                    seenFirst = true;
                }
                buffer.append(i);
            }
        }
        writer.setValue(buffer.toString());
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        BitSet result = new BitSet();
        StringTokenizer tokenizer = new StringTokenizer(reader.getValue(), ",", false);
        while (tokenizer.hasMoreTokens()) {
            int index = Integer.parseInt(tokenizer.nextToken());
            result.set(index);
        }
        return result;
    }
}
