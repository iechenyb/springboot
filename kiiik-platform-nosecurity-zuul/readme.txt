springboot+springsecurity+jwt+ͳ��ִ��ʱ��+swagger+druid���
ͨ�õ�΢������������
common-operator.sh
./*.sh status
./*.sh stop
./*.sh restart
./*.sh start
 <!--离线文档-->
        <dependency>
            <groupId>org.springframework.restdocs</groupId>
            <artifactId>spring-restdocs-mockmvc</artifactId>
            <scope>test</scope>
        </dependency>

<!--测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!--springfox-staticdocs 生成静态文档-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-staticdocs</artifactId>
            <version>2.6.1</version>
        </dependency>
<!-- Run the generated asciidoc through Asciidoctor to generate
                 other documentation types, such as PDFs or HTML5 -->
            <!--通过Asciidoctor使得asciidoc生成其他的文档格式，例如：PDF 或者HTML5-->
            <plugin>
                <groupId>org.asciidoctor</groupId>
                <artifactId>asciidoctor-maven-plugin</artifactId>
                <version>1.5.3</version>
                <!-- Include Asciidoctor PDF for pdf generation -->
                <!--生成PDF-->
                <dependencies>
                    <dependency>
                        <groupId>org.asciidoctor</groupId>
                        <artifactId>asciidoctorj-pdf</artifactId>
                        <version>1.5.0-alpha.14</version>
                    </dependency>
                    <!-- Comment this section to use the default jruby artifact provided by the plugin -->
                    <dependency>
                        <groupId>org.jruby</groupId>
                        <artifactId>jruby-complete</artifactId>
                        <version>1.7.21</version>
                    </dependency>
                </dependencies>
 
                <!-- Configure generic document generation settings -->
                <!--文档生成配置-->
                <configuration>
                    <sourceDirectory>${asciidoctor.input.directory}</sourceDirectory>
                    <sourceDocumentName>index.adoc</sourceDocumentName>
                    <attributes>
                        <doctype>book</doctype>
                        <toc>left</toc>
                        <toclevels>3</toclevels>
                        <numbered></numbered>
                        <hardbreaks></hardbreaks>
                        <sectlinks></sectlinks>
                        <sectanchors></sectanchors>
                        <generated>${generated.asciidoc.directory}</generated>
                    </attributes>
                </configuration>
                <!-- Since each execution can only handle one backend, run
                   separate executions for each desired output type -->
                <!--因为每次执行只能处理一个后端，所以对于每个想要的输出类型，都是独立分开执行-->
                <executions>
                    <!--html5-->
                    <execution>
                        <id>output-html</id>
                        <phase>test</phase>
                        <goals>
                            <goal>process-asciidoc</goal>
                        </goals>
                        <configuration>
                            <backend>html5</backend>
                            <outputDirectory>${asciidoctor.html.output.directory}</outputDirectory>
                        </configuration>
                    </execution>
                    <!--pdf-->
                    <execution>
                        <id>output-pdf</id>
                        <phase>test</phase>
                        <goals>
                            <goal>process-asciidoc</goal>
                        </goals>
                        <configuration>
                            <backend>pdf</backend>
                            <outputDirectory>${asciidoctor.pdf.output.directory}</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
                </plugin>