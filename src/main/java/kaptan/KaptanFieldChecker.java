package kaptan;

import kaptan.exception.FieldViolationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import kaptan.annotations.*;

public class KaptanFieldChecker {


    public KaptanFieldChecker(Object targetObject) throws FieldViolationException, IllegalArgumentException
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<AnotationType> checkIfThereIsAnyViolatedFields(Object o1) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Field[] fields = o1.getClass().getDeclaredFields();
        ArrayList<AnotationType> arrayList = new ArrayList<>();
        Object o2=null;
        for(Field field: fields)
        {
            Annotation[] annotations = field.getAnnotations();
            field.setAccessible(true);
            Object retrievedObject = field.get(o1);
            if(retrievedObject != null)
            o2= retrievedObject.getClass().getConstructor().newInstance();
            if(findAnnotation(annotations,AnotationType.Empty)>=0)
            {
               if((retrievedObject != null && retrievedObject.hashCode() != o2.hashCode()))
               {
                   arrayList.add(AnotationType.Empty);
               }
               if(retrievedObject == null)
                   arrayList.add(AnotationType.Empty);

            }
            if(findAnnotation(annotations,AnotationType.NonEmpty)>=0)
            {
                if((retrievedObject != null && retrievedObject.hashCode() == o2.hashCode()))
                {
                    arrayList.add(AnotationType.NonEmpty);
                }
                if(retrievedObject == null)
                    arrayList.add(AnotationType.NonEmpty);
            }
            if(findAnnotation(annotations,AnotationType.Null)>=0)
            {
                if(retrievedObject != null)
                {
                    arrayList.add(AnotationType.Null);
                }
            }
            if(findAnnotation(annotations,AnotationType.NonNull)>=0)
            {
                if(retrievedObject == null)
                {
                    arrayList.add(AnotationType.NonNull);
                }
            }


        }
        return arrayList;
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


//    }
}
