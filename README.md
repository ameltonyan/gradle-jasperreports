# Gradle JasperReports Plugin

Latest version: [![download latest version](https://api.bintray.com/packages/gmazelier/maven/com.github.gmazelier:jasperreports-gradle-plugin/images/download.png)](https://bintray.com/gmazelier/maven/com.github.gmazelier:jasperreports-gradle-plugin/_latestVersion)

Build status: [![Build Status](https://travis-ci.org/gmazelier/gradle-jasperreports.svg)](https://travis-ci.org/gmazelier/gradle-jasperreports)

## Description

Provides the capability to compile JasperReports design files. This plugin is designed to work like the Maven plugins [Maven 2 JasperReports Plugin](http://mojo.codehaus.org/jasperreports-maven-plugin) and [JasperReports-plugin](https://github.com/alexnederlof/Jasper-report-maven-plugin). Much of this was inspired by these two projects.

## Usage

This plugin provides two tasks, `compileAllReports` and `deleteJasperReports`. Adapt your build process to your own needs by defining the proper tasks depedencies (see *Custom Build Process* below).

If your designs compilation needs to run after Groovy compilation, running `compileAllReports` should give a similar output:

    $ gradle compileAllReports
    :compileJava UP-TO-DATE
    :compileGroovy UP-TO-DATE
    :prepareReportsCompilation
    :compileAllReports
    21 designs compiled in 2222 ms
    
    BUILD SUCCESSFUL
    
    Total time: 6.577 secs

To clean up and start fresh, simply run:

    $ gradle deleteJasperReports compileAllReports

### Installation

To use in Gradle 2.1 and later...

    plugins {
        id 'com.github.gmazelier.jasperreports' version '1.0.0'
    }

To use in earlier versions...

    buildscript {
        repositories {
            jcenter()
            maven {
                url 'http://jaspersoft.artifactoryonline.com/jaspersoft/third-party-ce-artifacts/'
            }
            maven {
                url 'http://jasperreports.sourceforge.net/maven2'
            }
            maven {
                url 'http://repository.jboss.org/maven2/'
            }
        }
        dependencies {
            classpath 'com.github.gmazelier:jasperreports-gradle-plugin:1.0.0'
        }
    }

    apply plugin: 'com.github.gmazelier.jasperreports'

### Configuration

Below are the parameters that can be used to configure the build:

| Parameter     | Type             | Description                                                                                   |
|---------------|------------------|-----------------------------------------------------------------------------------------------|
| `srcDir`      | `File`           | Design source files directory. Default value: `src/main/jasperreports`                        |
| `tmpDir`      | `File`           | Temporary files (`.java`) directory. Default value: `${project.buildDir}/jasperreports`       |
| `outDir`      | `File`           | Compiled reports file directory. Default value: `src/main/jasperreports`                      |
| `compiler`    | `String`         | The report compiler to use. Default value: Is dynamically resolved                            |
| `keepJava`    | `boolean`        | Keep temporary files after compiling. Default value: `false`                                  |
| `validateXml` | `boolean`        | Validate source files before compiling. Default value: `true`                                 |

### Example

Below is a complete example, with default values:

    jasperreports  {
         srcDir = file('src/main/jasperreports')
         tmpDir = file('${project.buildDir}/jasperreports')
         outDir = file('src/main/jasperreports')
         compiler
         keepJava = false
         validateXml = true
     }

### Custom Build Process

Adding a task dependency is very simple. For example, if you want to make sure that Groovy (and Java) compilation is successfully performed before JasperReports designs compilation, just add the following to your build script:

    compileAllReports.dependsOn compileGroovy

#### Adding dependencies

Here's a way to add dependencies (`joda-time` in this example)
    
    dependencies {
        jasperreports 'joda-time:joda-time:2.9.6'
    }

## Getting Help

To ask questions or report bugs, please use the [Github project](https://github.com/gmazelier/gradle/jasperreports/issues).

## Contributors

Patches are welcome. Thanks to:

* [Blake Jackson](https://github.com/blaketastic2)
* [Rankec](https://github.com/rankec)

## Change Log

### 0.3.2 (2015-12-07)

* Adds Microsoft OS support.

### 0.3.1 (2015-11-24)

* Fix an issue if there are multiple files in subdirectories when using `useRelativeOutDir`.

### 0.3.0 (2015-11-17)

* Adds Java 8 support.
* Configures Travis CI.
* Improves tests.

### 0.2.1 (2015-04-03)

* Adds `useRelativeOutDir` option.
* Enable Gradle wrapper for developers.

### 0.2.0 (2015-02-26)

* Adds `classpath` option.

### 0.1.0 (2014-08-24)

* Initial release.

## License
This plugin is licensed under [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
without warranties or conditions of any kind, either express or implied.
