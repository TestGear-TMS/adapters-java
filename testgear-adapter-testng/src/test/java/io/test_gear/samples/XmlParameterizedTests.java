package io.test_gear.samples;

import io.test_gear.annotations.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.test_gear.models.LinkType;

public class XmlParameterizedTests {

    @Test
    @Parameters({"title", "number", "url"})
    @ExternalId("Parameterized_test_with_xml_parameters_{number}")
    @DisplayName("Test with title = {title}, number = {number} parameters")
    @WorkItemIds("{number}")
    @Title("Title in the autotest card {number}")
    @Description("{title}")
    @Labels({"Tag{number}"})
    @Links(links = {
            @Link(url = "https://dumps.example.com/module/repository", title = "{title} Repository", description = "Example of repository", type = LinkType.REPOSITORY),
            @Link(url = "https://dumps.example.com/module/projects", title = "{title} Projects", type = LinkType.REQUIREMENT),
            @Link(url = "https://dumps.example.com/module/", type = LinkType.BLOCKED_BY),
            @Link(url = "https://dumps.example.com/module/docs", title = "{title} Documentation", type = LinkType.RELATED),
            @Link(url = "https://dumps.example.com/module/JCP-777", title = "{title} JCP-777", type = LinkType.DEFECT),
            @Link(url = "https://dumps.example.com/module/issue/5", title = "{title} Issue-5", type = LinkType.ISSUE),
    })
    void testWithXmlParameters(String title, int number, String url) {

    }
}
