package ucatolica.edu.co.audichek.domain.notificationBuilder;

import java.util.List;

public class EmailBuilder {
    private final String to;
    private final String subject;
    private final String body;
    private final List<String> cc;

    private EmailBuilder(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.body = builder.body;
        this.cc = builder.cc;
    }

    public static class Builder {
        private String to;
        private String subject = "";
        private String body = "";
        private List<String> cc = List.of();

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder cc(List<String> cc) {
            this.cc = cc;
            return this;
        }

        public EmailBuilder build() {
            return new EmailBuilder(this);
        }
    }
}
