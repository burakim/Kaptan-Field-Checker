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

/**
 * KaptanFieldChecker is the main class that checks the target object in terms of field value assignment violations.
 */
public class KaptanFieldChecker {


    /**
     * <p> It checks the function argument if it complies with class field rules. When it is called, it looks the target class to understand the defined rules.
     * <br> Then, if it see any violation, it saves all class field violation into the ArrayList.
     * <br> Before returning null value, it throws FieldViolationException if it sees any value in the ArrayList.</p>
     * If it received null value as a function argument, it throws IllegalArgumentException.
     * @since 1.0
     * @author Halim Burak Yesilyurt
     * @param targetObject A target object that is going to be checked in terms of variable value assignment violations.
     * @throws FieldViolationException If it see any class field value assignment violation, FieldViolationException is thrown.
     * @throws IllegalArgumentException If it see function parameter as a null value, IllegalArgumentException is thrown.
     */
    public void check(Object targetObject) throws FieldViolationException, IllegalArgumentException
    {
        if(targetObject == null)
            throw new IllegalArgumentException("Argument can not be null;");
           try {
               ArrayList<Class<? extends Annotation>> violations = checkIfThereIsAnyViolatedFields(targetObject);
               if (violations.size() > 0) {
                   String exceptionMessage = targetObject.getClass().getName()+" named class has "+violations.size()+" class field value assignment violations.";
                   throw new FieldViolationException(exceptionMessage,violations);
               }
           }
           catch (IllegalAccessException e)
           {
               e.printStackTrace();
           }


    }

    /**
     *It gets target object to check if it contains any class field violation. If it encounter any illegal access attempts to class fields, it throws IllegalAccessException.
     * @param o1 Target object
     * @return ArrayList that contains violated annotation rules.
     * @throws IllegalAccessException If Kaptan can not access assigned value of class variable, IllegalAccessException is thrown.
     */
    private ArrayList<Class<? extends Annotation>> checkIfThereIsAnyViolatedFields(Object o1) throws IllegalAccessException {
        Field[] fields = o1.getClass().getDeclaredFields();
        ArrayList<Class<? extends Annotation>> arrayList = new ArrayList<>();
        boolean didWeChangeAccesibility = false;
        for(Field field: fields)
        {
            Annotation[] annotations = field.getAnnotations();
            HashSet<Class<? extends Annotation>> annotationHashSet = retrieveAllAnnotations(annotations);
            if(!field.isAccessible()) {
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

                    if (annotationHashSet.contains(MustBeEmpty.class) && isPassedSizeConstraint(retrievedObject,0,Integer.MAX_VALUE)) {
                        arrayList.add(MustBeEmpty.class);
                    }

                    if (annotationHashSet.contains(MustBeNonEmpty.class) && !isPassedSizeConstraint(retrievedObject,0,Integer.MAX_VALUE)) {
                            arrayList.add(MustBeNonEmpty.class);
                    }

                    if(annotationHashSet.contains(EnforceIntervalConstraint.class))
                    {
                        EnforceIntervalConstraint annotation = (EnforceIntervalConstraint) findAnnotation(annotations,EnforceIntervalConstraint.class);
                        if(annotation != null && !isPassedIntervalConstraint(annotation, retrievedObject)) {
                                arrayList.add(EnforceIntervalConstraint.class);
                        }

                    }
                    if(annotationHashSet.contains(EnforceSizeConstraint.class))
                    {
                        EnforceSizeConstraint annotation = (EnforceSizeConstraint) findAnnotation(annotations,EnforceSizeConstraint.class);
                        if(annotation != null) {
                            int max = annotation.max();
                            int min = annotation.min();
                            if (!isPassedSizeConstraint(retrievedObject, min, max))
                                arrayList.add(EnforceSizeConstraint.class);
                        }
                    }
                    if(annotationHashSet.contains(EnforceRegexRule.class))
                    {
                        EnforceRegexRule annotation = (EnforceRegexRule) findAnnotation(annotations,EnforceRegexRule.class);
                        if(annotation != null && !isPassedRegexRule(annotation, retrievedObject)) {
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





//    private boolean doesItContainsFollowingDecimalValues(double[] values, Object retrievedObject)
//    {
//        double value = Double.MIN_VALUE;
//        if(retrievedObject instanceof Number)
//        {
//            value = (Double) retrievedObject;
//        }
//
//        Arrays.sort(values);
//
//        if(Arrays.binarySearch(values,value)>=0)
//            return true;
//        else
//            return false;
//
//    }
//
//    private boolean doesItContainsFollowingStringValues(String[] values, Object retrievedObject)
//    {
//        Arrays.sort(values);
//        if(retrievedObject instanceof  String)
//        {
//            String str = (String)retrievedObject;
//            if(Arrays.binarySearch(values,str) >= 0)
//            return true;
//            else
//                return false;
//        }
//        else
//        {
//            return false;
//        }
//
//    }

    /**
     * It checks EnforceIntervalConstraint annotated fields of target object in terms of compatibility of defined interval constraint.
     * @param annotation EnforceIntervalConstraint annotation
     * @param retrievedObject A target object that contains EnforceIntervalConstraint annotation.
     * @return boolean It refers if the field pass interval constraint test.
     */
    private  boolean isPassedIntervalConstraint(EnforceIntervalConstraint annotation, Object retrievedObject)
    {
        double minVal = annotation.min();
        double maxVal = annotation.max();

        if(retrievedObject instanceof Number)
        {
            long retrievedNumber =((Number)retrievedObject).longValue();
            return (minVal<retrievedNumber && maxVal>= retrievedNumber);


        }
        return false;
    }

    /**
     *It checks EnforceSizeConstraint annotated fields of target object in terms of having elements in minimum and maximum size boundaries.
     * @param retrievedObject A target object that contains EnforceSizeConstraint annotation.
     * @param min It is one less of minimum value that can be assigned to this field seamlessly.
     * @param max It is maximum value that can be assigned to this field seamlessly.
     * @return boolean It refers if the field pass size constraint test.
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
        else if(retrievedObject instanceof Enum)
        {
            size = retrievedObject.toString().length();
        }
        return (size>=0 && ((min<size)&&(size<=max)));
    }

    /**
     *It checks EnforceSizeConstraint annotated fields of target object in terms of complying given regular expression.
     * @param annotation EnforceRegexRule annotation
     * @param retrievedObject A target object that contains EnforceRegexRule annotation.
     * @return boolean
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
        if(!m .find())
        {
            return false;
        }
        else
            return true;
    }

    /**
     * Since looking an element if it is in hashset is O(n) complexity, it returns with a hashset that contains annotations.
     * @param annotations An array that annotations
     * @return HashSet
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
     * It accepts two argument: an annotation array and a target annotation, then it finds target annotation in the annotation array.
     * @param annotations It is an array that contains annotations.
     * @param o1 Target annotation which is going to be searched in annotations array.
     * @return Annotation object that is found annotation in the annotation array argument.
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
