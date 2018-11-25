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

package kaptan;

import kaptan.exception.FieldViolationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kaptan.annotations.*;

public class KaptanFieldChecker {


    /**
     * <p>It checks the function argument if it complies with class field rules. When it is called, it looks the target class to understand the defined rules.
     * </br>Then, if it see any violation, it saves all class field violation into the ArrayList.
     * </br>Before returning null value, it throws FieldViolationException if it sees any value in the ArrayList.</p>
     * If it received null value as a function argument, it throws IllegalArgumentException.
     * @since 1.0
     * @author Halim Burak Yesilyurt
     * @param targetObject
     * @throws FieldViolationException
     * @throws IllegalArgumentException
     */
    public void check(Object targetObject) throws FieldViolationException, IllegalArgumentException
    {
        if(targetObject == null)
            throw new IllegalArgumentException("Argument can not be null;");
           try {
               ArrayList<Class<? extends Annotation>> violations = checkIfThereIsAnyViolatedFields(targetObject);
               if (violations.size() > 0) {
                   String exceptionMessage = targetObject.getClass().getName()+" named class has "+violations.size()+" violations class field value.";
                   throw new FieldViolationException(exceptionMessage,violations);
               }
           }
           catch (IllegalAccessException e)
           {
               e.printStackTrace();
           }


    }

    /**
     *
     * @param o1
     * @return
     * @throws IllegalAccessException
     */
    private ArrayList<Class<? extends Annotation>> checkIfThereIsAnyViolatedFields(Object o1) throws IllegalAccessException {
        Field[] fields = o1.getClass().getDeclaredFields();
        ArrayList<Class<? extends Annotation>> arrayList = new ArrayList<>();
        Object o2=null;
        boolean didWeChangeAccesibility = false;
        for(Field field: fields)
        {
            Annotation[] annotations = field.getAnnotations();
            HashSet<Class<? extends Annotation>> annotationHashSet = retrieveAllAnnotations(annotations);
            if(!field.canAccess(o1)) {
                field.setAccessible(true);
                didWeChangeAccesibility = true;
            }
            Object retrievedObject = field.get(o1);
            if(retrievedObject == null)
            {

                if(annotationHashSet.contains(MustBeNonNull.class))
                {
                    arrayList.add(MustBeNonNull.class);
                }
                if(annotationHashSet.contains(MustBeNonEmpty.class))
                {
                    arrayList.add(MustBeNonEmpty.class);
                }
                if(annotationHashSet.contains(EnforceIntervalConstraint.class))
                {
                    arrayList.add(EnforceIntervalConstraint.class);
                }
                if(annotationHashSet.contains(EnforceRegexRule.class))
                {
                    arrayList.add(EnforceRegexRule.class);
                }
                if(annotationHashSet.contains(EnforceSizeConstraint.class))
                {
                    arrayList.add(EnforceSizeConstraint.class);
                }
            }
            else
            {

                    if (annotationHashSet.contains(MustBeNull.class)) {
                        arrayList.add(MustBeNull.class);
                    }

                    if (annotationHashSet.contains(MustBeEmpty.class)) {

                        if(isPassedSizeConstraint(retrievedObject,0,Integer.MAX_VALUE) == true){
                          arrayList.add(MustBeEmpty.class);
                      }
                    }

                    if (annotationHashSet.contains(MustBeNonEmpty.class)) {
                        if(isPassedSizeConstraint(retrievedObject,0,Integer.MAX_VALUE) == false)
                        {
                            arrayList.add(MustBeNonEmpty.class);
                        }
                    }

                    if(annotationHashSet.contains(EnforceIntervalConstraint.class))
                    {
                        EnforceIntervalConstraint annotation = (EnforceIntervalConstraint) findAnnotation(annotations,EnforceIntervalConstraint.class);
                        if(isPassedIntervalConstraint(annotation,retrievedObject) == false)
                            arrayList.add(EnforceIntervalConstraint.class);

                    }
                    if(annotationHashSet.contains(EnforceSizeConstraint.class))
                    {
                        EnforceSizeConstraint annotation = (EnforceSizeConstraint) findAnnotation(annotations,EnforceSizeConstraint.class);
                        int max =  annotation.max();
                        int min = annotation.min();
                        if(isPassedSizeConstraint(retrievedObject,min,max) == false)
                            arrayList.add(EnforceSizeConstraint.class);

                    }
                    if(annotationHashSet.contains(EnforceRegexRule.class))
                    {
                        EnforceRegexRule annotation = (EnforceRegexRule) findAnnotation(annotations,EnforceRegexRule.class);
                        if(isPassedRegexRule(annotation,retrievedObject) == false)
                        {
                            arrayList.add(EnforceRegexRule.class);
                        }
                    }


            }
            if (didWeChangeAccesibility) {
                field.setAccessible(false);
                didWeChangeAccesibility = false;
            }
        }
        return arrayList;
    }





    private boolean doesItContainsFollowingDecimalValues(double[] values, Object retrievedObject)
    {
        double value = Double.MIN_VALUE;
        if(retrievedObject instanceof Number)
        {
            value = (Double) retrievedObject;
        }

        Arrays.sort(values);

        if(Arrays.binarySearch(values,value)>=0)
            return true;
        else
            return false;

    }

    private boolean doesItContainsFollowingStringValues(String[] values, Object retrievedObject)
    {
        Arrays.sort(values);
        if(retrievedObject instanceof  String)
        {
            String str = (String)retrievedObject;
            if(Arrays.binarySearch(values,str) >= 0)
            return true;
            else
                return false;
        }
        else
        {
            return false;
        }

    }

    /**
     *
     * @param annotation
     * @param retrievedObject
     * @return
     */
    private  boolean isPassedIntervalConstraint(EnforceIntervalConstraint annotation, Object retrievedObject)
    {
        double minVal = annotation.min();
        double maxVal = annotation.max();

        if(retrievedObject instanceof Number)
        {
            long retrievedNumber =((Number)retrievedObject).longValue();
            if(minVal<retrievedNumber && maxVal>= retrievedNumber)
            {
                return true;
            }

        }
        return false;
    }

    /**
     *
     * @param retrievedObject
     * @param min
     * @param max
     * @return
     */
    private boolean isPassedSizeConstraint(Object retrievedObject, int min, int max )
    {
        int size = Integer.MIN_VALUE;
        if((retrievedObject instanceof Map))
        {
         size = ((Map)retrievedObject).size();
        }
        else if(retrievedObject instanceof Collection)
        {
            size = ((Collection)retrievedObject).size();
        }
        else if(retrievedObject instanceof KaptanField)
        {
            size = ((KaptanField) retrievedObject).size();
        }
        else if(retrievedObject instanceof String)
        {
            size = ((String) retrievedObject).length();
        }
        if(size>=0 && ((min<size)&&(size<=max)))
        {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @param annotation
     * @param retrievedObject
     * @return
     */
    private boolean isPassedRegexRule (EnforceRegexRule annotation, Object retrievedObject)
    {
        String regex = annotation.value();
        String receivedString =null;
        if(retrievedObject instanceof String)
        {
            receivedString  = (String) retrievedObject;
        }
        else
        {
            receivedString = retrievedObject.toString(); // If they override toString function thats ok.
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(receivedString);
        if(m .find() != true)
        {
            return false;
        }
        else
            return true;
    }

    /**
     *
     * @param annotations
     * @return
     */
    private HashSet<Class<? extends Annotation>> retrieveAllAnnotations(Annotation[] annotations)
    {
        HashSet<Class<? extends Annotation> > annotationHashSet = new HashSet<>();

        for(Annotation a: annotations)
        {
            annotationHashSet.add(a.annotationType());
        }
        return annotationHashSet;

    }

    /**
     *
     * @param annotations
     * @param o1
     * @return
     */
    private Annotation findAnnotation(Annotation[] annotations, Object o1)
    {
        for(int i = 0;i<annotations.length;i++)
        {
            if(annotations[i].annotationType().equals(o1))
            {
                return annotations[i];
            }
        }
        return null;
    }






}
