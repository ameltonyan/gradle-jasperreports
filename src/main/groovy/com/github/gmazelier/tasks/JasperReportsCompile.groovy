package com.github.gmazelier.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.*

class JasperReportsCompile extends DefaultTask {
    final static String NAME = "compileAllReports"

    @Optional
    @InputDirectory
    File srcDir

    @Optional
    @OutputDirectory
    File outDir

    @Optional
    @OutputDirectory
    File tmpDir

    @Input
    @Optional
    String compiler

    @Input
    @Optional
    boolean keepJava

    @Input
    @Optional
    boolean validateXml

    JasperReportsCompile() {
        group = "Jasper Reports"
        description = "Compile Jasper Reports design source files"
        outputs.upToDateWhen { false }
    }

    @TaskAction
    void compileAllReports() {
        Project gradleProject = project

        def jasperOptions = [srcdir: srcDir, tempdir: tmpDir, keepjava: keepJava, xmlvalidation: validateXml]
        if (outDir) {
            jasperOptions << [destdir: outDir]
        }
        if (compiler) {
            jasperOptions << [compiler: compiler]
        }

        logger.info("jasper options: {}", jasperOptions)

        project.ant {
            taskdef(name: 'jasperCompiler', classpath: gradleProject.configurations.jasperreports.asPath, classname: 'net.sf.jasperreports.ant.JRAntCompileTask')

            jasperCompiler(jasperOptions) {
                classpath(path: gradleProject.configurations.jasperreports.asPath)
                include(name: '**/*.jrxml')
            }
        }
    }
}