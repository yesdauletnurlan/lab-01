package org.example.config;

import org.example.domain.AssigneeRequiredRule;
import org.example.domain.Rule;
import org.example.domain.TransitionRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfig {

    @Bean
    public Rule transitionRule() {
        return new TransitionRule();
    }

    @Bean
    public Rule assigneeRequiredRule() {
        return new AssigneeRequiredRule(true);
    }
}