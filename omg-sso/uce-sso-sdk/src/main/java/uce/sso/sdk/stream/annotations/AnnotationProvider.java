/** 
 * @项目名称: FSP
 * @文件名称: AnnotationProvider 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
/*
 * Copyright (C) 2006, 2007, 2008, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 02. March 2006 by Mauro Talevi
 */
package uce.sso.sdk.stream.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * An utility class to provide annotations from different sources
 * 
 * @author Guilherme Silveira
 * @deprecated As of 1.3
 */
@Deprecated
public class AnnotationProvider {

    /**
     * Returns a field annotation based on an annotation type
     * 
     * @param field the annotation Field
     * @param annotationClass the annotation Class
     * @return The Annotation type
     * @deprecated As of 1.3
     */
    @Deprecated
    public <T extends Annotation> T getAnnotation(Field field, Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

}
