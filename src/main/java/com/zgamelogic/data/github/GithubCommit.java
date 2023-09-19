package com.zgamelogic.data.github;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        public GithubCommit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String authorName = node.get("commit").get("author").get("name").asText();
            String commitMessage = node.get("commit").get("message").asText();
            String url = node.get("html_url").asText();
            return new GithubCommit(authorName, commitMessage, url);
        }
    }
}
