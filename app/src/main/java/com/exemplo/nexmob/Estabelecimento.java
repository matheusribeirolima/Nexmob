package com.exemplo.nexmob;

import android.location.Location;
import android.support.annotation.NonNull;

public class Estabelecimento implements Comparable<Estabelecimento>{//implementa Comparable para o objeto poder ser ordenado
    private String nome;
    private String foto;
    private int avaliacao;
    private String lat;
    private String lng;
    private int distancia;

    public Estabelecimento(String nome, String foto, int avaliacao, String lat, String lng) {
        this.nome = nome;
        this.foto = foto;
        this.avaliacao = avaliacao;
        this.lat = lat;
        this.lng = lng;
        float[] dist = new float[3];//variável necessária para receber os resultados da distância
        Location.distanceBetween(-18.9234085,-48.2832788,Double.valueOf(lat),Double.valueOf(lng),dist);//método que retorna a distância em metros de dois pontos geográficos
        this.distancia = (int) dist[0];//pega a parte inteira do valor
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public int compareTo(@NonNull Estabelecimento o) {//método necessário para realizar a comparação customizada do objeto
        if (distancia > o.getDistancia()) {
            return 1;
        }
        else if (distancia <  o.getDistancia()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
