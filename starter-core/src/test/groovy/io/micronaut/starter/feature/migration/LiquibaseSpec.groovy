package io.micronaut.starter.feature.migration

import io.micronaut.starter.ApplicationContextSpec
import io.micronaut.starter.BuildBuilder
import io.micronaut.starter.fixture.CommandOutputFixture
import io.micronaut.starter.options.BuildTool

class LiquibaseSpec extends ApplicationContextSpec  implements CommandOutputFixture {

    void 'test readme.md with feature liquibase contains links to micronaut docs'() {
        when:
        def output = generate(['liquibase'])
        def readme = output["README.md"]

        then:
        readme
        readme.contains("https://www.liquibase.org")
        readme.contains("https://micronaut-projects.github.io/micronaut-liquibase/latest/guide/index.html")
    }

    void "test the dependency is added to the gradle build"() {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.GRADLE)
                .features(['liquibase'])
                .render()

        then:
        template.contains('implementation("io.micronaut.liquibase:micronaut-liquibase")')
    }

    void "test the dependency is added to the maven build"() {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.MAVEN)
                .features(['liquibase'])
                .render()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut.liquibase</groupId>
      <artifactId>micronaut-liquibase</artifactId>
      <scope>compile</scope>
    </dependency>
""")
    }
}
