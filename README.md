# Reproducing failure with initialization in Abstract classes (JVM and KotlinNative)

## Problem statement

There is a demonstration of Kotlin's initialization order with abstract bases classes. The issue occurs when the parent
class attempts to `abstract val` in the `init` block, while the property is being overridden by subclass constructor.

## Description

This example is built using **Kotlin Multiplatform (KMP)** and targets 2 platforms: **JVM** and **Linux X64**. Testing
was performed on **Linux Mint**.

- `experimentConstructorKoin()` - the scheme of initialization from the *Problem Statement* is made by `Koin` framework
- `experimentConstructorMan()` - the scheme of initialization from the *Problem Statement* is made by manually
- `experimentFieldKoin()` - another approach with defining property by `inject()` of `Koin`

## Results

### Deliver dependencies by constructor

#### JVM

During the initialization process the code in the `init` block tries to get a variable that haven't existed yet (still
null). So, **JVM** check not-null variable and throws `NullPointerException`.

```terminaloutput
Hello world from JVM
AbstractComponent:init:start
Boom?
Cannot invoke "org.example.SomeDep.getValue()" because the return value of "org.example.AbstractComponentMan.getSomeDep()" is null
AbstractComponent:init:end
Dark Magic
finish
```

As you may see, if we catch the exception in `try` block, the next code will be executed correctly. Because an object
will be existed by that memory pointer.

#### Kotlin Native

The same issue - *pointer to null*. But unlike **JVM** environment the program is just stopped.

```terminaloutput
Hello world from Native
AbstractComponent:init:start
Boom?
```

The stacktrace from the **GDB**:

```terminaloutput
Thread 1 "app.kexe" received signal SIGSEGV, Segmentation fault.
0x0000000000247ebb in kfun:org.example.SomeDep#<get-value>(){}kotlin.String (_this=0x0)
```

Getting *pointer to null* provokes `SIGSEGV` signal.

### Deliver dependencies in property

The both environment have worked correctly.

## Summary

1. The order of calling: `primary constructor` -> `inner` -> inner variables
2. **JVM** is safer with explicit `NPE` instead of **Native** with implicit `SIGSEGV`
3. It makes sense to use **Kotlin Native** carefully :)