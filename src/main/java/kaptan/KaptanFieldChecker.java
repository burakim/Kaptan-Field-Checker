package kaptan;

import kaptan.exception.FieldViolationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kaptan.annotations.*;

public class KaptanFieldChecker {


    public KaptanFieldChecker()
    {

    }

    public void check(Object targetObject) throws FieldViolationException, IllegalArgumentException
    {
        if(targetObject == null)
            throw new IllegalArgumentException("Argument can not be null;");
           try {
               ArrayList<AnnotationType> violations = checkIfThereIsAnyViolatedFields(targetObject);
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

//    private boolean isPassedFrom



    private ArrayList<AnnotationType> checkIfThereIsAnyViolatedFields(Object o1) throws IllegalAccessException {
        Field[] fields = o1.getClass().getDeclaredFields();
        ArrayList<AnnotationType> arrayList = new ArrayList<>();
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
                    arrayList.add(AnnotationType.NonNull);
                }
                if(annotationHashSet.contains(MustBeNonEmpty.class))
                {
                    arrayList.add(AnnotationType.NonEmpty);
                }
                if(annotationHashSet.contains(EnforceIntervalConstraint.class))
                {
                    arrayList.add(AnnotationType.IntervalConstraint);
                }
                if(annotationHashSet.contains(EnforceRegexRule.class))
                {
                    arrayList.add(AnnotationType.RegexRule);
                }
                if(annotationHashSet.contains(EnforceSizeConstraint.class))
                {
                    arrayList.add(AnnotationType.SizeConstraint);
                }
            }
            else
            {

                    if (annotationHashSet.contains(MustBeNull.class)) {
                        arrayList.add(AnnotationType.Null);
                    }

                    if (annotationHashSet.contains(MustBeEmpty.class)) {

                        if(isPassedSizeConstraint(retrievedObject,0,Integer.MAX_VALUE) == true){
                          arrayList.add(AnnotationType.Empty);
                      }
                    }

                    if (annotationHashSet.contains(MustBeNonEmpty.class)) {
                        if(isPassedSizeConstraint(retrievedObject,0,Integer.MAX_VALUE) == false)
                        {
                            arrayList.add(AnnotationType.NonEmpty);
                        }
                    }

                    if(annotationHashSet.contains(EnforceIntervalConstraint.class))
                    {
                        EnforceIntervalConstraint annotation = (EnforceIntervalConstraint) findAnnotation(annotations,AnnotationType.IntervalConstraint);
                        if(isPassedIntervalConstraint(annotation,retrievedObject) == false)
                            arrayList.add(AnnotationType.IntervalConstraint);

                    }
                    if(annotationHashSet.contains(EnforceSizeConstraint.class))
                    {
                        EnforceSizeConstraint annotation = (EnforceSizeConstraint) findAnnotation(annotations,AnnotationType.SizeConstraint);
                        int max =  annotation.max();
                        int min = annotation.min();
                        if(isPassedSizeConstraint(retrievedObject,min,max) == false)
                            arrayList.add(AnnotationType.SizeConstraint);

                    }
                    if(annotationHashSet.contains(EnforceRegexRule.class))
                    {
                        EnforceRegexRule annotation = (EnforceRegexRule) findAnnotation(annotations,AnnotationType.RegexRule);
                        if(isPassedRegexRule(annotation,retrievedObject) == false)
                        {
                            arrayList.add(AnnotationType.RegexRule);
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


    private HashSet<Class<? extends Annotation>> retrieveAllAnnotations(Annotation[] annotations)
    {
        HashSet<Class<? extends Annotation> > annotationHashSet = new HashSet<>();

        for(Annotation a: annotations)
        {
            annotationHashSet.add(a.annotationType());
        }
        return annotationHashSet;

    }


    private Annotation findAnnotation(Annotation[] annotations, AnnotationType annotationType)
    {
        Object o1 = null;
        switch (annotationType)
        {
            case Null:
            {
                o1 = MustBeNull.class;
                break;
            }
            case NonNull:
            {
                o1 = MustBeNonNull.class;
                break;
            }
            case NonEmpty:
            {
                o1 = MustBeNonEmpty.class;
                break;
            }
            case Empty:
            {
                o1 = MustBeEmpty.class;
                break;
            }
            case RegexRule:
            {
                o1 = EnforceRegexRule.class;
                break;
            }
            case IntervalConstraint: {
                o1 = EnforceIntervalConstraint.class;
                break;
            }
            case SizeConstraint: {
                o1 = EnforceSizeConstraint.class;
                break;
            }
        }

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
