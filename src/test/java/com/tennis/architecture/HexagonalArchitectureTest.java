package com.tennis.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class HexagonalArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.tennis");

    @Test
    @DisplayName("Domain should not depend on Application or Infrastructure layers")
    void domainShouldNotDependOnOtherLayers() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("com.tennis.domain..")
                .should().dependOnClassesThat().resideInAnyPackage("com.tennis.service..", "com.tennis.infra..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Application Layer should only depend on Domain")
    void applicationShouldOnlyDependOnDomain() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("com.tennis.application..")
                .should().dependOnClassesThat().resideInAnyPackage("com.tennis.infra..");

        rule.check(importedClasses);
    }

}
