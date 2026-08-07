# spring-enhance

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-21-orange)](https://github.com/easy-4-java/spring-enhance) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

spring-enhance ("Spring Enhancement") is a broad set of Spring Framework extensions that make application code more convenient on the plain Spring Fra...

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

`spring-enhance` ("Spring Enhancement") is a broad set of Spring Framework extensions that make application code more convenient on the plain Spring Framework (no Spring Boot): a module-oriented `DispatcherServlet` with pluggable module resolvers, MVC interceptors and base controllers, a JDK proxy-based `EnhancedProxy`, enhanced application events with AOP integration (`EnhancedEvent`, `EventAspect`), context/bean-factory conveniences (`SpringContext`, `EnhancedBeanFactory`, `EnhancedBeanScannerConfigurer`), property placeholder encryption, reusable property editors, listeners, quartz task base class, and a large utility package.

It is an extension library for Spring applications — it is not a framework and not a Spring Boot starter.

Typical scenarios:

| Scenario | What this module contributes |
|:---|:---|
| Module-based web dispatch (multi-module sites) | `ModuleDispatcherServlet`, `ModuleResolver` (fixed / session / accept-header) |
| MVC interceptors and base controllers | `SpringMVCInterceptor`, `SpringMVCCORSInterceptor`, `SpringMVCPerformanceInterceptor`, `FileUploadAcceptInterceptor`, `AbstractBaseController`, `AbstractFreeMarkerController` |
| Proxy beans without proxies classes | `EnhancedProxy` (JDK `InvocationHandler`), `EnhancedProxyBeanFactory`, `EnhancedBeanFactory` |
| AOP-integrated application events | `EnhancedEvent<T>`, `EventPoint`, `EventAspect`, `EnhancedEventHandleListener` |
| Bean/context access anywhere | `SpringContext`, `SpringContextUtils`, `AbstractSpringInstanceContext` |
| Encrypted property placeholders | `EncryptPropertyPlaceholderConfigurer` |
| Property editors, listeners, quartz tasks, utilities | `*PropertyEditor`, `*InitializedListener`, `AbstractQuartzTask`, `WebUtils`, `SpringPropertiesUtils`, ... |

## 2. Features & Status

Project status: pre-release development line (`1.0.x.*` snapshots); public API is still stabilizing until the first tagged release.

| Capability | Status | Notes |
|:---|:---|:---|
| Module-oriented web layer | Stable | `ModuleDispatcherServlet` + `ModuleResolver` implementations (`FixedModuleResolver`, `SessionModuleResolver`, `AcceptHeaderModuleResolver`) |
| MVC interceptors | Stable | `SpringMVCInterceptor`, `SpringMVCCORSInterceptor`, `SpringMVCPerformanceInterceptor`, `FileUploadAcceptInterceptor`, `ModuleChangeInterceptor`, `SpringMVCWebRequestInterceptor` |
| Base controllers | Stable | `AbstractBaseController`, `AbstractCommandController`, `AbstractFreeMarkerController` |
| Views / themes | Stable | `HtmlResourceView`, `XMLResourceView`, `NestedThemeResolver`, `SpringAutowireServlet` |
| Enhanced events | Stable | `EnhancedEvent<T>`, `EventPoint`, `EventInvocation`, `EventAspect` + join-point event handlers (before/after/around/throwing) |
| Proxy & bean factory helpers | Stable | `EnhancedProxy`, `EnhancedProxyBeanFactory`, `EnhancedBeanFactory`, `EnhancedBeanScannerConfigurer`, `EnhancedClassPathMapperScanner`, `EnhancedSequenceFactory`, `EnhancedMessageFactory`, `MultipleMessageSourceFactory` |
| Context abstraction | Stable | `SpringContext` (interface), `SpringContextUtils`, `SpringFileSystemXmlApplicationContext`, `SpringClassPathXmlInstanceContext`, `SpringWebInstanceContext`, `NestedMessageSource` |
| Configuration helpers | Stable | `EncryptPropertyPlaceholderConfigurer`, `Ini` |
| Property editors | Stable | String / Long / Integer / Float / Double / Date editors |
| Bootstrap listeners | Stable | `SpringContextInitializedListener`, `PropertiesSystemPropertyInitializedListener`, `PropertiesResourceInitializedListener`, `PropertiesBundleResourceInitializedListener` |
| Misc | Stable | `DataSourceRoutingKeyHolder`, `InjectBeanSelfProcessor`, `AbstractQuartzTask`, reactive request context filters, `EnhancedEventAspectInterceptor`, utilities (`WebUtils`, `WebRequestUtils`, `WebResponseUtils`, `SpringPropertiesUtils`, `MessageSourceHolder`, `LocaleUtils`, ...) |

## 3. Requirements & Compatibility

| Requirement | Version |
|:---|:---|
| JDK | 21+ |
| Maven | 3.6+ |
| Spring Framework | 5.3.x (managed by `spring-framework-bom`: core, beans, context, web, webmvc, aop, aspects, jdbc, jms, orm, oxm, tx, messaging, webflux, ...) |
| easy4j sibling module | `hitool-core` (same `1.0.x.*` line) |
| Other runtime deps | freemarker, `com.jfinal:cos`, commons-fileupload, commons-io, commons-lang3, commons-codec, javax.servlet-api, validation-api |

Version lines:

| Branch | JDK | Version pattern | Notes |
|:---|:---|:---|:---|
| `feature/1.0.x` | 8 | `1.0.x.*` | Current line; Spring 5.x era |
| `feature/2.0.x` | 17 | `2.0.x.*` | Next line |
| `feature/3.0.x` | 21 | `3.0.x.*` | Future line |

## 4. Architecture & Modules

```
HTTP request
        |
        v
+------------------------------------+
| ModuleDispatcherServlet            |
|  ModuleResolver -> module          |
|  SpringMVC*Interceptor chain       |
+------------------------------------+
        |
        v
AbstractBaseController / generated MVC methods
        |
        v
+------------------------------------+
| EnhancedEvent + EventAspect        |
| EnhancedProxy / EnhancedBeanFactory|
| SpringContext / utils              |
+------------------------------------+
```

The project is a single jar module (`org.springframework.biz`), organized by concern:

| Package | Responsibility |
|:---|:---|
| `web` | `servlet` (module dispatcher, resolvers, interceptors, views, themes), `multipart`, `server` (reactive filters), `method`, `client` |
| `context` | `SpringContext` + implementations, `event` (enhanced events + aspects), `support`, `annotation` |
| `factory` | Bean factory, scanner, sequence/message factory helpers |
| `proxy` | `EnhancedProxy` and related |
| `config` | `EncryptPropertyPlaceholderConfigurer`, `Ini` |
| `propertyeditors` | Reusable Spring property editors |
| `listener` | Bootstrap / properties listeners |
| `jdbc` | `DataSourceRoutingKeyHolder` |
| `aop` | `InjectBeanSelfProcessor` |
| `quartz` / `scheduling` | `AbstractQuartzTask`, concurrent scheduling |
| `utils` | Web, request/response, properties, locale, message-source utilities |

## 5. Installation

Artifacts are published to the easy4j private repository and GitHub Releases; the project is not yet on Maven Central.

Maven:

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>spring-enhance</artifactId>
    <version>3.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

Gradle:

```groovy
implementation 'io.github.easy4j:spring-enhance:3.0.x.x.20260630-SNAPSHOT'
```

## 6. Quick Start

Publish an enhanced event and consume it with plain Spring `@EventListener`:

```java
import org.springframework.biz.context.event.EnhancedEvent;
import org.springframework.context.ApplicationEventPublisher;

// publish an event that carries a bound payload
ApplicationEventPublisher publisher = ...; // injected by Spring
publisher.publishEvent(new EnhancedEvent<>(this, order));
```

```java
import org.springframework.biz.context.event.EnhancedEvent;
import org.springframework.context.event.EventListener;

@EventListener
public void onOrderCreated(EnhancedEvent<Order> event) {
    Order order = event.getBind();   // the bound payload
    // handle order...
}
```

Expected result: the event flows through the standard Spring event infrastructure; listeners receive the `EnhancedEvent` and read the bound object via `getBind()`.

## 7. Configuration

Plain library — no Spring property prefixes are bound by the module itself. Configuration is done with the provided building blocks:

- `EncryptPropertyPlaceholderConfigurer` — encrypted property placeholders in `PropertyPlaceholderConfigurer` style.
- `Ini` — INI-style configuration parsing.
- `ModuleDispatcherServlet` / `ModuleResolver` — module resolution strategy (fixed, session, accept-header).
- `EnhancedBeanScannerConfigurer` / `EnhancedClassPathMapperScanner` — bean scanning conveniences.
- `SpringFileSystemXmlApplicationContext` / `SpringClassPathXmlInstanceContext` — context initialization variants.

## 8. Core Usage / API

Access Spring beans from plain (non-Spring) code:

```java
import org.springframework.biz.context.SpringContext;
import org.springframework.biz.utils.SpringContextUtils;

SpringContext context = SpringContextUtils.getContext();
MyService service = context.getInstance(MyService.class);
context.getInstance("myService");
```

Enhanced proxy for interface-based beans:

```java
import org.springframework.biz.proxy.EnhancedProxy;

EnhancedProxy handler = new EnhancedProxy();
MyService proxy = (MyService) java.lang.reflect.Proxy.newProxyInstance(
        getClass().getClassLoader(), new Class<?>[] { MyService.class }, handler);
```

## 9. Testing & Build

Build:

```bash
./mvnw clean verify
```

- The build is configured with the JaCoCo Maven plugin: a coverage report is generated at `target/site/jacoco/index.html` and a rule checks the bundle line coverage against a 90% minimum (`haltOnFailure=false`, so the check reports but does not fail the build).
- The repository currently ships no unit tests for this module; coverage is tracked via the JaCoCo report.
- The `central` Maven profile (`./mvnw -Pcentral deploy`) attaches GPG signatures, sources and Javadoc jars for publishing.

## 10. Versioning & Branches

Three parallel version lines are maintained:

| Branch | JDK | Version pattern |
|:---|:---|:---|
| `feature/1.0.x` | 8 | `1.0.x.*` |
| `feature/2.0.x` | 17 | `2.0.x.*` |
| `feature/3.0.x` | 21 | `3.0.x.*` |

Maintenance policy: the `1.0.x` line is the actively developed line (current snapshot `3.0.x.x.20260630-SNAPSHOT`); `2.0.x` and `3.0.x` are forward porting lines targeting newer JDKs. Snapshots are built on demand; tagged releases are distributed via GitHub Releases.

## 11. Contributing & License

- Fork the repository and open a pull request; keep the `1.0.x` line compatible with JDK 8.
- Bug reports and feature requests are tracked via GitHub Issues.
- Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
