package com.exemplo.nexmob;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class EstabelecimentoAdapter extends ArrayAdapter {

    private ArrayList estabelecimentos = new ArrayList();//lista de estabelecimentos que será preenchida na lista

    public EstabelecimentoAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    static class Handler {//helper para otimização do algoritmo
        ImageView imagem;
        TextView nome;
        TextView avaliacao;
        TextView distancia;
    }

    @Override
    public void add(@Nullable Object object) {//quando adicionado um objeto ao adapter, este também é adicionado à variavel interna
        super.add(object);
        estabelecimentos.add(object);
    }

    @Override
    public int getCount() {
        return estabelecimentos.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return estabelecimentos.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Handler handler;
        if (convertView == null){//caso não tenha sido construído
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);//infla o layout para podermos preencher os dados
            handler = new Handler();//cria objeto helper
            handler.imagem = (ImageView) convertView.findViewById(R.id.imagem);//vincula cada item do helper ao do layout dos itens da lista
            handler.nome = (TextView) convertView.findViewById(R.id.nome);
            handler.nome.setTypeface(null, Typeface.BOLD);//transforma em negrito o texto
            handler.avaliacao = (TextView) convertView.findViewById(R.id.avaliacao);
            handler.distancia = (TextView) convertView.findViewById(R.id.distancia);
            convertView.setTag(handler);//informa ao layout que o helper o preencherá
        } else {
            handler = (Handler) convertView.getTag();
        }
        Estabelecimento estab = (Estabelecimento) getItem(position);//objeto que irá preencher cada item da lista
        Picasso.with(getContext()).load(estab != null ? estab.getFoto() : null).into(handler.imagem);//biblioteca que irá realizar o download da imagem
        handler.nome.setText(estab != null ? estab.getNome() : null);//acesso a cada variável
        handler.avaliacao.setText(String.valueOf(estab != null ? estab.getAvaliacao() : 0));
        String resultado = (estab != null ? estab.getDistancia() : 0) + " m";
        handler.distancia.setText(resultado);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {//cada vez que os dados são alterados na lista
        Collections.sort(estabelecimentos);//chama a ordenação da mesma
        super.notifyDataSetChanged();
    }
}
