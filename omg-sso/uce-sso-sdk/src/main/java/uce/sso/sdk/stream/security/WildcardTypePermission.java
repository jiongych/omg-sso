/** 
 * @项目名称: FSP
 * @文件名称: WildcardTypePermission extends RegExpTypePermission 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2014 XStream Committers.
 * All rights reserved.
 *
 * Created on 09. January 2014 by Joerg Schaible
 */
package uce.sso.sdk.stream.security;

/**
 * Permission for any type with a name matching one of the provided wildcard expressions.
 * 
 * <p>
 * Supported are patterns with path expressions using dot as separator:
 * </p>
 * <ul>
 * <li>?: one non-control character except separator, e.g. for 'java.net.Inet?Address'</li>
 * <li>*: arbitrary number of non-control characters except separator, e.g. for types in a package like 'java.lang.*'</li>
 * <li>**: arbitrary number of non-control characters including separator, e.g. for types in a package and subpackages like 'java.lang.**'</li>
 * </ul>
 * <p>
 * The complete range of UTF-8 characters is supported except control characters.
 * </p>
 * 
 * @author J&ouml;rg Schaible
 * @since 1.4.7
 */
public class WildcardTypePermission extends RegExpTypePermission {

    /**
     * @since 1.4.7
     */
    public WildcardTypePermission(final String[] patterns) {
        super(getRegExpPatterns(patterns));
    }

    private static String[] getRegExpPatterns(final String[] wildcards) {
        if (wildcards == null)
            return null;
        final String[] regexps = new String[wildcards.length];
        for (int i = 0; i < wildcards.length; ++i) {
            final String wildcardExpression = wildcards[i];
            final StringBuffer result = new StringBuffer(wildcardExpression.length() * 2);
            result.append("(?u)");
            final int length = wildcardExpression.length();
            for (int j = 0; j < length; j++) {
                final char ch = wildcardExpression.charAt(j);
                switch (ch) {
                case '\\':
                case '.':
                case '+':
                case '|':
                case '[':
                case ']':
                case '(':
                case ')':
                case '^':
                case '$':
                    result.append('\\').append(ch);
                    break;

                case '?':
                    result.append('.');
                    break;

                case '*':
                    // see "General Category Property" in http://www.unicode.org/reports/tr18/
                    if (j + 1 < length && wildcardExpression.charAt(j + 1) == '*') {
                        result.append("[\\P{C}]*");
                        j++;
                    } else {
                        result.append("[\\P{C}&&[^").append('.').append("]]*");
                    }
                    break;

                default:
                    result.append(ch);
                    break;
                }
            }
            regexps[i] = result.toString();
        }
        return regexps;
    }
}
