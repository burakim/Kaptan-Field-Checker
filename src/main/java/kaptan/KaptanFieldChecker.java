package kaptan;

import kaptan.exception.FieldViolationException;

import java.lang.annotation.Annotation;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
           if(checkIfThereIsAnyViolatedFields(targetObject).size()>0)
           {
               throw new FieldViolationException("");
           }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

//    private boolean isPassedFrom



    private ArrayList<AnotationType> checkIfThereIsAnyViolatedFields(Object o1) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Field[] fields = o1.getClass().getDeclaredFields();
        ArrayList<AnotationType> arrayList = new ArrayList<>();
        Object o2=null;
        for(Field field: fields)
        {
            Annotation[] annotations = field.getAnnotations();
            HashSet<Class<? extends Annotation>> annotationHashSet = retrieveAllAnnotations(annotations);
            field.setAccessible(true);
            Object retrievedObject = field.get(o1);
            if(retrievedObject == null)
            {
                if(annotationHashSet.contains(MustBeNonNull.class))
                {
                    arrayList.add(AnotationType.NonNull);
                }
                if(annotationHashSet.contains(MustBeNonEmpty.class))
                {
                    arrayList.add(AnotationType.NonEmpty);
                }
            }
            else
            {
                try {
                    o2 = retrievedObject.getClass().getConstructor().newInstance();

                    if (annotationHashSet.contains(MustBeNull.class)) {
                        arrayList.add(AnotationType.Null);
                    }

                    if (annotationHashSet.contains(MustBeEmpty.class)) {
                        if (retrievedObject.hashCode() != o2.hashCode()) {
                            arrayList.add(AnotationType.Empty);
                        }
                    }

                    if (annotationHashSet.contains(MustBeNonEmpty.class)) {
                        if (retrievedObject.hashCode() == o2.hashCode()) {
                            arrayList.add(AnotationType.NonEmpty);
                        }
                    }
                }
                catch (NoSuchMethodException e) {
                    // It doesnt have default constructor.
                    //e.printStackTrace();
                }
            }

        }
        return arrayList;
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


    private int findAnnotation(Annotation[] annotations, AnotationType anotationType)
    {


Object o1 = null;
        switch (anotationType)
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
        }

        for(int i = 0;i<annotations.length;i++)
        {
            if(annotations[i].annotationType().equals(o1))
            {
                return i;
            }
        }
        return -1;
    }


}
