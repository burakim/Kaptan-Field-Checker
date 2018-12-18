# Kaptan Class Field Value Checker [![Build Status](https://travis-ci.com/burakim/Kaptan-Field-Checker.svg?branch=master)](https://travis-ci.com/burakim/Kaptan-Field-Checker)
> *"Kaptan" is a Turkish word that means captain.*

Kaptan is a JAVA library that checks class fields in terms of input validation and verification. If it sees any variable value violation, it immediately throws **FieldViolationException**.

## Getting Started
### How Can I Get It?
#### Gradle
```compile 'me.yesilyurt.burak:kaptan-field-checker:1.0.1'```
#### Maven
```
<dependency>
  <groupId>me.yesilyurt.burak</groupId>
  <artifactId>kaptan-field-checker</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
### How Can I Use It? [![Javadocs](https://www.javadoc.io/badge/me.yesilyurt.burak/kaptan-field-checker.svg)](https://www.javadoc.io/doc/me.yesilyurt.burak/kaptan-field-checker)
Let's say that you have a model (POJO) class whose name is **User** and it has **username** and **password** String fields. Let's assume that you don't want to get any null assignment for the **username** field and empty value assignment for the **password** field. You can write an input validation mechanism on your own or you can use **Kaptan** for checking your class fields. The below example shows a way to use **Kaptan** for it.
```java
public class User {
    @MustBeNonNull
    private String username;
    @MustBeNonEmpty
    private String password;
    }
```
Did you realise **@MustBeNonNull** and **@MustBeNonEmpty** annotations? 

Those are annotations that help **Kaptan** for understanding what your intentions and the structure of your class are. Since you put **@MustBeNonNull** above of your **username** field, **Kaptan** checks the **username** field if it is null. If it sees a null assignment in the **username** field, it throws a **FieldViolationException** to inform you.

**Kaptan** supports following annotations:
* @MustBeNonNull
* @MustBeNull
* @MustBeNonEmpty
* @MustBeEmpty
* @EnforceSizeConstraint(min=\<value\>, max=\<value\>)
* @EnforceRegexRule(\<regex-string-value\>)
* @EnforceIntervalConstraint(min=\<value\>, max=\<value\>)

#### To use **Kaptan** for checking your class fields
You can initiliaze a **KaptanFieldChecker** object, then you can continue by passing your model object (e.g. User class) into the **check** function of the **KaptanFieldChecker** object. Since it might throw a **FieldViolationException**, you should write a try&catch block and place the **KaptanFieldChecker** related code lines in your try block. You can find an example below.
```java
try{
KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
kaptanFieldChecker.check(targetObject);
}
catch(FieldViolationException e){
    // You can place your exception handling codes.
    e.printStackTrace();
}

```

#### What does Kaptan support?
Kaptan supports primitives, data structures that are in the Java collection library, and Strings. If you want to Kaptan support your custom data structure, you can make it happen by implementing a **KaptanField** interface into your custom data structure.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments
This is an open-source project, we appreciate any contribution from contributors. If you want to improve Kaptan Class Field Input Checker, feel free to fork and create a pull request!

## Support Me!
BTC: 3GaS3Vq3rowJQviRgfTBL8Qy8BMAHCpC9Q

ETH: 0x47f4b805B2927fae671751ECe7D88d2638063bE9
