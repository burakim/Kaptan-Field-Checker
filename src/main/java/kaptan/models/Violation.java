/*
 * Copyright (c) 2018.
 * Author: Halim Burak Yesilyurt (h.burakyesilyurt@gmail.com)
 * This library is distributed with MIT licence.
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package kaptan.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * Violation is a model object to represent a result of mismatched class field value and provided allowed value rule.
 * For example, it might contains MustBeEmpty value violation to provide human readable result to user in the FieldViolationException class.
 * @since 1.1.0
 */
public class Violation {

    private Class<? extends Annotation> annotation;
    private Field field;


    /**
     * It is initialized a Violation model object with an annotation value and related field object.
     * @param annotation It contains an annotation that is subjected to the class field value violation.
     * @param field It is a field that has field value violation.
     */
    public Violation(Class<? extends Annotation> annotation,Field field)
    {
        this.field = field;
        this.annotation = annotation;
    }

    /**
     * It returns an annotation value of the violation object.
     */
    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    /**
     * It returns a field value of the violation object.
     */
    public Field getField() {
        return field;
    }

}
