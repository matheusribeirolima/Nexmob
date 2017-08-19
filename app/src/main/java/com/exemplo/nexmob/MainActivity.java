package com.exemplo.nexmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "BaixaEstabelecimentos";//identificação da requisição web
    private RequestQueue requisicoes;//fila de requisições
    private EstabelecimentoAdapter adapter;//adaptador customizado para cada item da lista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//vincula o layout à activity

        ListView lista = (ListView) findViewById(R.id.listview);//vincula a lista para este listview
        adapter = new EstabelecimentoAdapter(getApplicationContext(),R.layout.item_lista);//informa o contexto e o layout do adaptador
        lista.setAdapter(adapter);//atribui o adaptador customizado à lista

        requisicoes = Volley.newRequestQueue(this);//Instancia a fila de requisições
        String url ="http://guianex.com.br/api/v2/stores/search/a";//url que contém o webservice

        JsonArrayRequest requisita = new JsonArrayRequest(Request.Method.GET, url, null,//Requisita um Json Array à url passada
                new Response.Listener<JSONArray>(){//resposta da conexão vem em formato JSONArray
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){//percorre toda a resposta
                            String nome = null;
                            try {
                                JSONObject est = response.getJSONObject(i);//obtém cada objeto
                                nome = est.getString("name");
                                adapter.add(new Estabelecimento(nome, est.getJSONObject("avatars").getString("small"),
                                        est.getInt("average"), est.getString("lat"), est.getString("lng")));//adiciona cada objeto ao adaptador da lista
                            } catch (JSONException e) {//caso algum objeto Json esteja com problema
                                Toast.makeText(MainActivity.this, "Não foi possível retornar: "+nome, Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged();//informa que depois da inserção do ultimo dado, teve alteração na lista
                    }
                }, new Response.ErrorListener() {//caso o servidor retorne erro como resposta
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Erro ao contatar o WebService.", Toast.LENGTH_SHORT).show();
            }
        });

        requisicoes.add(requisita);//Adiciona a requisição à lista de requisições
    }

    @Override
    protected void onStop() {//quando a activity pára a execução
        super.onStop();
        if (requisicoes != null) {
            requisicoes.cancelAll(TAG);//cancela a conexão com o servidor
        }
    }
}
