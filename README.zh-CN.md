# spring-enhance

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://github.com/easy-4-java/spring-enhance) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

spring-enhance（"Spring Enhancement"）是面向纯 Spring Framework（不依赖 Spring Boot）的一组广泛扩展，让应用代码更便捷：面向模块化的 DispatcherServlet 与可插拔模块解析器、MVC 拦截器与基础控制器、基于 JDK 代理的...

## 目录

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

`spring-enhance`（"Spring Enhancement"）是面向纯 Spring Framework（不依赖 Spring Boot）的一组广泛扩展，让应用代码更便捷：面向模块化的 `DispatcherServlet` 与可插拔模块解析器、MVC 拦截器与基础控制器、基于 JDK 代理的 `EnhancedProxy`、带 AOP 集成的增强应用事件（`EnhancedEvent`、`EventAspect`）、上下文与 Bean 工厂辅助（`SpringContext`、`EnhancedBeanFactory`、`EnhancedBeanScannerConfigurer`）、属性占位符加密、可复用的属性编辑器、监听器、Quartz 任务基类以及庞大的工具包。

它是 Spring 应用的扩展库——不是框架，也不是 Spring Boot Starter。

典型场景：

| 场景 | 本模块提供的组件 |
|:---|:---|
| 基于模块的 Web 分发（多模块站点） | `ModuleDispatcherServlet`、`ModuleResolver`（fixed / session / accept-header） |
| MVC 拦截器与基础控制器 | `SpringMVCInterceptor`、`SpringMVCCORSInterceptor`、`SpringMVCPerformanceInterceptor`、`FileUploadAcceptInterceptor`、`AbstractBaseController`、`AbstractFreeMarkerController` |
| 无需代理类的代理 Bean | `EnhancedProxy`（JDK `InvocationHandler`）、`EnhancedProxyBeanFactory`、`EnhancedBeanFactory` |
| AOP 集成的应用事件 | `EnhancedEvent<T>`、`EventPoint`、`EventAspect`、`EnhancedEventHandleListener` |
| 任意位置访问 Bean/上下文 | `SpringContext`、`SpringContextUtils`、`AbstractSpringInstanceContext` |
| 加密属性占位符 | `EncryptPropertyPlaceholderConfigurer` |
| 属性编辑器、监听器、Quartz 任务、工具 | `*PropertyEditor`、`*InitializedListener`、`AbstractQuartzTask`、`WebUtils`、`SpringPropertiesUtils` 等 |

## 2. Features & Status

项目状态：`1.0.x.*` 预发布开发线（快照版本）；在首个正式 Release 标签之前，公开 API 仍在稳定过程中。

| 能力 | 状态 | 说明 |
|:---|:---|:---|
| 模块化 Web 层 | 稳定 | `ModuleDispatcherServlet` + `ModuleResolver` 实现（`FixedModuleResolver`、`SessionModuleResolver`、`AcceptHeaderModuleResolver`） |
| MVC 拦截器 | 稳定 | `SpringMVCInterceptor`、`SpringMVCCORSInterceptor`、`SpringMVCPerformanceInterceptor`、`FileUploadAcceptInterceptor`、`ModuleChangeInterceptor`、`SpringMVCWebRequestInterceptor` |
| 基础控制器 | 稳定 | `AbstractBaseController`、`AbstractCommandController`、`AbstractFreeMarkerController` |
| 视图 / 主题 | 稳定 | `HtmlResourceView`、`XMLResourceView`、`NestedThemeResolver`、`SpringAutowireServlet` |
| 增强事件 | 稳定 | `EnhancedEvent<T>`、`EventPoint`、`EventInvocation`、`EventAspect` + 连接点事件处理器（before/after/around/throwing） |
| 代理与 Bean 工厂辅助 | 稳定 | `EnhancedProxy`、`EnhancedProxyBeanFactory`、`EnhancedBeanFactory`、`EnhancedBeanScannerConfigurer`、`EnhancedClassPathMapperScanner`、`EnhancedSequenceFactory`、`EnhancedMessageFactory`、`MultipleMessageSourceFactory` |
| 上下文抽象 | 稳定 | `SpringContext`（接口）、`SpringContextUtils`、`SpringFileSystemXmlApplicationContext`、`SpringClassPathXmlInstanceContext`、`SpringWebInstanceContext`、`NestedMessageSource` |
| 配置辅助 | 稳定 | `EncryptPropertyPlaceholderConfigurer`、`Ini` |
| 属性编辑器 | 稳定 | String / Long / Integer / Float / Double / Date 编辑器 |
| 启动监听器 | 稳定 | `SpringContextInitializedListener`、`PropertiesSystemPropertyInitializedListener`、`PropertiesResourceInitializedListener`、`PropertiesBundleResourceInitializedListener` |
| 其他 | 稳定 | `DataSourceRoutingKeyHolder`、`InjectBeanSelfProcessor`、`AbstractQuartzTask`、响应式请求上下文过滤器、`EnhancedEventAspectInterceptor`、工具类（`WebUtils`、`WebRequestUtils`、`WebResponseUtils`、`SpringPropertiesUtils`、`MessageSourceHolder`、`LocaleUtils` 等） |

## 3. Requirements & Compatibility

| 要求 | 版本 |
|:---|:---|
| JDK | 17+ |
| Maven | 3.6+ |
| Spring Framework | 5.3.x（由 `spring-framework-bom` 管理：core、beans、context、web、webmvc、aop、aspects、jdbc、jms、orm、oxm、tx、messaging、webflux 等） |
| easy4j 兄弟模块 | `hitool-core`（同一 `1.0.x.*` 版本线） |
| 其他运行依赖 | freemarker、`com.jfinal:cos`、commons-fileupload、commons-io、commons-lang3、commons-codec、javax.servlet-api、validation-api |

版本线：

| 分支 | JDK | 版本模式 | 说明 |
|:---|:---|:---|:---|
| `feature/1.0.x` | 8 | `1.0.x.*` | 当前开发线；Spring 5.x 时代 |
| `feature/2.0.x` | 17 | `2.0.x.*` | 下一条版本线 |
| `feature/3.0.x` | 21 | `3.0.x.*` | 未来版本线 |

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

本工程为单 jar 模块（`org.springframework.biz`），按关注点组织：

| 包 | 职责 |
|:---|:---|
| `web` | `servlet`（模块分发、解析器、拦截器、视图、主题）、`multipart`、`server`（响应式过滤器）、`method`、`client` |
| `context` | `SpringContext` 及实现、`event`（增强事件与切面）、`support`、`annotation` |
| `factory` | Bean 工厂、扫描器、序列/消息工厂辅助 |
| `proxy` | `EnhancedProxy` 及相关 |
| `config` | `EncryptPropertyPlaceholderConfigurer`、`Ini` |
| `propertyeditors` | 可复用的 Spring 属性编辑器 |
| `listener` | 启动 / 属性监听器 |
| `jdbc` | `DataSourceRoutingKeyHolder` |
| `aop` | `InjectBeanSelfProcessor` |
| `quartz` / `scheduling` | `AbstractQuartzTask`、并发调度 |
| `utils` | Web、请求/响应、属性、Locale、消息源工具 |

## 5. Installation

制品发布到 easy4j 私有仓库与 GitHub Releases，暂未发布 Maven Central。

Maven：

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>spring-enhance</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

Gradle：

```groovy
implementation 'io.github.easy4j:spring-enhance:2.0.x.x.20260630-SNAPSHOT'
```

## 6. Quick Start

发布增强事件并使用 Spring 原生 `@EventListener` 消费：

```java
import org.springframework.biz.context.event.EnhancedEvent;
import org.springframework.context.ApplicationEventPublisher;

// 发布携带绑定载荷的事件
ApplicationEventPublisher publisher = ...; // 由 Spring 注入
publisher.publishEvent(new EnhancedEvent<>(this, order));
```

```java
import org.springframework.biz.context.event.EnhancedEvent;
import org.springframework.context.event.EventListener;

@EventListener
public void onOrderCreated(EnhancedEvent<Order> event) {
    Order order = event.getBind();   // 绑定载荷
    // 处理订单...
}
```

预期结果：事件经由标准 Spring 事件机制流转；监听器收到 `EnhancedEvent` 并通过 `getBind()` 读取绑定对象。

## 7. Configuration

纯库——模块本身不绑定 Spring 属性前缀，配置通过以下积木完成：

- `EncryptPropertyPlaceholderConfigurer`——`PropertyPlaceholderConfigurer` 风格的加密属性占位符；
- `Ini`——INI 风格配置解析；
- `ModuleDispatcherServlet` / `ModuleResolver`——模块解析策略（fixed、session、accept-header）；
- `EnhancedBeanScannerConfigurer` / `EnhancedClassPathMapperScanner`——Bean 扫描便捷类；
- `SpringFileSystemXmlApplicationContext` / `SpringClassPathXmlInstanceContext`——上下文初始化变体。

## 8. Core Usage / API

在普通（非 Spring）代码中访问 Spring Bean：

```java
import org.springframework.biz.context.SpringContext;
import org.springframework.biz.utils.SpringContextUtils;

SpringContext context = SpringContextUtils.getContext();
MyService service = context.getInstance(MyService.class);
context.getInstance("myService");
```

面向接口的增强代理：

```java
import org.springframework.biz.proxy.EnhancedProxy;

EnhancedProxy handler = new EnhancedProxy();
MyService proxy = (MyService) java.lang.reflect.Proxy.newProxyInstance(
        getClass().getClassLoader(), new Class<?>[] { MyService.class }, handler);
```

## 9. Testing & Build

构建：

```bash
./mvnw clean verify
```

- 构建配置了 JaCoCo Maven 插件：覆盖率报告生成于 `target/site/jacoco/index.html`，并配置了 BUNDLE 行覆盖率 90% 的校验规则（`haltOnFailure=false`，即只报告不阻断构建）；
- 当前仓库本模块暂无单元测试，覆盖率以 JaCoCo 报告为准；
- `central` Maven Profile（`./mvnw -Pcentral deploy`）附加 GPG 签名、源码包与 Javadoc 包用于发布。

## 10. Versioning & Branches

维护三条并行版本线：

| 分支 | JDK | 版本模式 |
|:---|:---|:---|
| `feature/1.0.x` | 8 | `1.0.x.*` |
| `feature/2.0.x` | 17 | `2.0.x.*` |
| `feature/3.0.x` | 21 | `3.0.x.*` |

维护策略：`1.0.x` 为当前活跃开发线（当前快照 `2.0.x.x.20260630-SNAPSHOT`）；`2.0.x` 与 `3.0.x` 为面向更新 JDK 的前向移植线。快照按需构建，正式 Release 通过 GitHub Releases 分发。

## 11. Contributing & License

- Fork 仓库并提交 Pull Request；`1.0.x` 版本线保持 JDK 8 兼容；
- Bug 反馈与功能建议通过 GitHub Issues 跟踪；
- 基于 [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) 开源。
