package com.github.gmazelier.plugins

import com.github.gmazelier.extension.JasperReportsExtension
import com.github.gmazelier.tasks.JasperReportsCompile
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class JasperReportsPluginTest extends GroovyTestCase {

    void testPluginAddsJasperReportsCompileTask() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'

        assert project.tasks.compileAllReports instanceof JasperReportsCompile
    }

    void testPluginAddsJasperReportsExtension() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'

        assert project.jasperreports instanceof JasperReportsExtension
    }

    void testPluginHasDefaultValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'

        project.afterEvaluate {
            def jasperreports = project.jasperreports as JasperReportsExtension
            assert jasperreports.srcDir == project.file('src/main/jasperreports')
            assert jasperreports.tmpDir == project.file("${project.buildDir}/jasperreports")
            assert !jasperreports.outDir
            assert !jasperreports.compiler
            assert !jasperreports.keepJava
            assert jasperreports.validateXml
        }
    }

    void testPluginSpreadsDirOptions() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'

        File src = project.file('src/jasperreports')
        File tmp = project.file('tmp/jasperreports')
        File out = project.file('out/jasperreports')

        project.jasperreports {
            srcDir = src
            tmpDir = tmp
            outDir = out
        }
        project.evaluate()

        assert src == project.jasperreports.srcDir
        assert src == project.tasks.compileAllReports.srcDir

        assert out == project.jasperreports.outDir
        assert out == project.tasks.compileAllReports.outDir

        assert tmp == project.jasperreports.tmpDir
        assert tmp == project.tasks.compileAllReports.tmpDir
    }

    void testPluginSpreadsCompilerOption() {
        String groovyCompiler = 'net.sf.jasperreports.compilers.JRGroovyCompiler'
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'
        project.jasperreports {
            compiler = groovyCompiler
        }
        project.evaluate()

        assert groovyCompiler == project.jasperreports.compiler
        assert groovyCompiler == project.tasks.compileAllReports.compiler
    }

    void testPluginSpreadsKeepJavaOption() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'
        project.jasperreports {
            keepJava = true
        }
        project.evaluate()

        assert project.jasperreports.keepJava
        assert project.tasks.compileAllReports.keepJava
    }

    void testPluginSpreadsValidateXmlOption() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.gmazelier.jasperreports'
        project.jasperreports {
            validateXml = false
        }
        project.evaluate()

        assert !project.jasperreports.validateXml
        assert !project.tasks.compileAllReports.validateXml
    }
}