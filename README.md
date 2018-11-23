# Kaptan Class Field Input Checker ![BuildStatus](https://api.travis-ci.com/burakim/Kaptan-Field-Checker.svg?token=UayyDWyqDWfpvheAzvhz&branch=master)
> *"Kaptan" is a Turkish word that means captain.*

Kaptan is a JAVA library that checks class variables in terms of input validation and verification. If it sees any variable violation, it immediately throws **FieldViolationException**.

## Getting Started
[Maven Central or BinTray]

### How Can I Use It?
Let's say that you have a model (POJO) class whose name is **User** and it has **username** and **password** String fields. Let's assume you don't want to get any null assignment for **username** field and empty value assignment for **password** field. You write an input validation mechanism on your own or you can use **Kaptan** for it. The below example shows a way to use **Kaptan** for it.
```java
public class User {
    @MustBeNonNull
    private String username;
    @MustBeNonEmpty
    private String password;
    }
```
Did you realise **@MustBeNonNull** and **@MustBeNonEmpty** annotations? 

Those are annotations to help **Kaptan** to understand your intentations and the structure of your class. Since you put **@MustBeNonNull** above of your **username** field, **Kaptan** checks **username** field if it is null. If it sees null assignment in **username** field, it throws **FieldViolationException** to inform you.

**Kaptan** supports following annotations:
* @MustBeNonNull
* @MustBeNull
* @MustBeNonEmpty
* @MustBeEmpty

#### To use **Kaptan** to check your classes' fields
You can initiliaze a **KaptanFieldChecker** object, then you should pass your model object into **check** function. Since it might throw **FieldViolationException**, you should write try&catch block and place **KaptanFieldChecker** in your try block. You can find an example below.
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
## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments
This is an open-source project, we appreciate any contribution from contributors. If you want to improve Kaptan Class Field Input Checker, feel free to fork and create a pull request!

## Support Me!
BTC: 3GaS3Vq3rowJQviRgfTBL8Qy8BMAHCpC9Q

ETH: 0x47f4b805B2927fae671751ECe7D88d2638063bE9
