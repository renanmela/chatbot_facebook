package com.example.chatbottest;

public class Regex {

    private String idade = "(?i)sua idade|(?i)(voc[eê] tem).*idade|(?i)idade.*(voc[eê] tem)|(?i)(voc[eê] tem).*anos|(?i)anos.*(tem voc[eê])|(?i)anos.*(voc[eê] tem)";
    private String nome = "(?i)seu nome|(?i)voc[eê]\\sse\\schama|(?i)nome\\s(tem\\s)voc[eê]|(?i)nome\\svoc[eê]\\stem";
    private String oi = "\"(?i)oi|(?i)ol[aá]|(?i)esta\\\\sai?\"";

    public String getNome() {
        return nome;
    }

    public String getOi() {
        return oi;
    }

    public String getIdade() {
        return idade;
    }
}
