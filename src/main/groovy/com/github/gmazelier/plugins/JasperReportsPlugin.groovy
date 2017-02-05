package com.github.gmazelier.plugins

import com.github.gmazelier.extension.JasperReportsExtension
import com.github.gmazelier.tasks.JasperReportsCompile
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

class JasperReportsPlugin implements Plugin<Project> {
    JasperReportsExtension jasperReportsExtension
    JasperReportsCompile compileAllReports

    @Override
    void apply(Project project) {
        project.with {
            configurations {
                jasperreports {
                    transitive = true
                }
            }

            dependencies {
                jasperreports localGroovy()
                jasperreports 'net.sf.jasperreports:jasperreports:6.4.0'
                jasperreports 'com.lowagie:itext:4.2.2'
                jasperreports 'org.olap4j:olap4j:1.2.0'
            }

            jasperReportsExtension = extensions.create(JasperReportsExtension.NAME, JasperReportsExtension)
            compileAllReports = tasks.create(JasperReportsCompile.NAME, JasperReportsCompile)

            afterEvaluate {
                compileAllReports.with {
                    srcDir = jasperReportsExtension.srcDir ?: file('src/main/jasperreports')
                    outDir = jasperReportsExtension.outDir
                    tmpDir = jasperReportsExtension.tmpDir ?: file("${buildDir}/jasperreports")
                    compiler = jasperReportsExtension.compiler
                    keepJava = jasperReportsExtension.keepJava
                    validateXml = jasperReportsExtension.validateXml
                }

                tasks.create("deleteJasperReports", Delete) {
                    group = "Jasper Reports"
                    description = "Delete all jasper reports files"
                    doLast {
                        project.delete fileTree(dir: jasperReportsExtension.outDir ?: compileAllReports.srcDir, include: '**/*.jasper')
                    }
                }
            }
        }
    }
}