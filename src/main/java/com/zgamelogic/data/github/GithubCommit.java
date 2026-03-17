package com.zgamelogic.data.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = GithubCommit.GithubCommitDeserializer.class)
public class GithubCommit {
    private String authorName;
    private String commitMessage;
    private String url;

    public static class GithubCommitDeserializer extends StdDeserializer<GithubCommit> {
        public GithubCommitDeserializer(){
            this(null);
        }

        protected GithubCommitDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public GithubCommit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
            JsonNode node = deserializationContext.readTree(jsonParser);
            String authorName = node.get("commit").get("author").get("name").asText();
            String commitMessage = node.get("commit").get("message").asText();
            String url = node.get("html_url").asText();
            return new GithubCommit(authorName, commitMessage, url);
        }
    }
}
