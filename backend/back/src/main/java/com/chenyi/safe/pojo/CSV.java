package com.chenyi.safe.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CSV {
        @JsonProperty("BENIGN")
        private int benign;

        @JsonProperty("Bot")
        private int bot;

        @JsonProperty("DDoS")
        private int ddos;

        @JsonProperty("DoS_GoldenEye")
        private int doSGoldenEye;

        @JsonProperty("DoS_Hulk")
        private int doSHulk;

        @JsonProperty("DoS_Slowhttptest")
        private int doSSlowhttptest;

        @JsonProperty("DoS_slowloris")
        private int doSSlowloris;

        @JsonProperty("FTPPatator")
        private int ftpPatator;

        @JsonProperty("Heartbleed")
        private int heartbleed;

        @JsonProperty("Infiltration")
        private int infiltration;

        @JsonProperty("PortScan")
        private int portScan;

        @JsonProperty("SSHPatator")
        private int sshPatator;

        @JsonProperty("Web_Attack_Brute_Force")
        private int webAttackBruteForce;

        @JsonProperty("Web_Attack_Sql_Injection")
        private int webAttackSqlInjection;

        @JsonProperty("Web_Attack_XSS")
        private int webAttackXSS;

        @JsonProperty("Unknow_attack")
        private int unknowAttack;

}
