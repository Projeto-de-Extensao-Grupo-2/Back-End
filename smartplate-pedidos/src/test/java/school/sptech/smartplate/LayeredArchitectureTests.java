package school.sptech.smartplate;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@DisplayName("3. Testes de Arquitetura em Camadas")
class LayeredArchitectureTests {

    private static final String BASE_PACKAGE = "school.sptech.smartplate";

    private final JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE);

    @Test
    @DisplayName("3.1 Controllers não devem acessar Repositories ou Entities diretamente")
    void controllers_nao_devem_acessar_repositories_ou_entities() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..repository..", "..entity..");

        rule.check(classes);
    }

    @Test
    @DisplayName("3.2 Services não devem acessar Controllers")
    void services_nao_devem_acessar_controllers() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat()
                .resideInAPackage("..controller..");

        rule.check(classes);
    }

    @Test
    @DisplayName("3.3 Services não devem acessar outras Services")
    void services_nao_devem_acessar_outras_services() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat()
                .resideInAPackage("..service..");

        rule.check(classes);
    }

    @Test
    @DisplayName("3.4 DTOs não devem depender de Entities, Repositories, Services ou Controllers")
    void dtos_nao_devem_depender_de_camadas_internas() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..dto..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..entity..", "..repository..", "..service..", "..controller..");

        rule.check(classes);
    }

    @Test
    @DisplayName("3.5 Repositories não devem depender de DTOs, Mappers, Services ou Controllers")
    void repositories_devem_trabalhar_apenas_com_entities() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..dto..", "..mapper..", "..service..", "..controller..");

        rule.check(classes);
    }
}
