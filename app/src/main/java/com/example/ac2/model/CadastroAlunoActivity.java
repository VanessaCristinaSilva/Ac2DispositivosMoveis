//Nome:Vanessa Cristina da Silva RA:223253

package com.example.ac2.model;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ac2.R;
import com.example.ac2.network.ApiService;
import com.example.ac2.network.RetrofitClient;
import com.example.ac2.network.ViaCepService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroAlunoActivity extends AppCompatActivity {
    private EditText etRa, etNome, etCep, etLogradouro, etComplemento, etBairro, etCidade, etUf;
    private Button btnBuscarCep, btnSalvar;

    private ApiService apiService;
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        etRa = findViewById(R.id.etRa);
        etNome = findViewById(R.id.etNome);
        etCep = findViewById(R.id.etCep);
        etLogradouro = findViewById(R.id.etLogradouro);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUf = findViewById(R.id.etUf);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        btnSalvar = findViewById(R.id.btnSalvar);

        // API principal
        Retrofit retrofit = RetrofitClient.getClient("https://66527305813d78e6d6d58d81.mockapi.io/");
        apiService = retrofit.create(ApiService.class);

        // API ViaCep
        Retrofit retrofitViaCep = RetrofitClient.getClient("https://viacep.com.br/");
        viaCepService = retrofitViaCep.create(ViaCepService.class);

        btnBuscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = etCep.getText().toString();
                obterDadosEndereco(cep);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });
    }

    private void obterDadosEndereco(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        new Thread(() -> {
            try {
                java.net.URL viacepUrl = new java.net.URL(url);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) viacepUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();

                java.io.InputStream inputStream = connection.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                org.json.JSONObject jsonObject = new org.json.JSONObject(response.toString());
                String logradouro = jsonObject.getString("logradouro");
                String complemento = jsonObject.optString("complemento", "");
                String bairro = jsonObject.getString("bairro");
                String cidade = jsonObject.getString("localidade");
                String uf = jsonObject.getString("uf");

                runOnUiThread(() -> {
                    etLogradouro.setText(logradouro);
                    etComplemento.setText(complemento);
                    etBairro.setText(bairro);
                    etCidade.setText(cidade);
                    etUf.setText(uf);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void salvarAluno() {
        String raText = etRa.getText().toString().trim();
        String nome = etNome.getText().toString().trim();
        String cepText = etCep.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String complemento = etComplemento.getText().toString().trim();
        String bairro = etBairro.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String uf = etUf.getText().toString().trim();

        if (raText.isEmpty() || nome.isEmpty() || cepText.isEmpty() || logradouro.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || uf.isEmpty()) {
            Toast.makeText(CadastroAlunoActivity.this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        int ra;
        try {
            ra = Integer.parseInt(raText);
        } catch (NumberFormatException e) {
            Toast.makeText(CadastroAlunoActivity.this, "RA inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Aluno aluno = new Aluno(ra, nome, cepText, logradouro, complemento, bairro, cidade, uf);

        Call<Void> call = apiService.salvarAluno(aluno);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroAlunoActivity.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
                    etRa.setText("");
                    etNome.setText("");
                    etCep.setText("");
                    etLogradouro.setText("");
                    etComplemento.setText("");
                    etBairro.setText("");
                    etCidade.setText("");
                    etUf.setText("");
                } else {
                    Toast.makeText(CadastroAlunoActivity.this, "Erro ao salvar aluno: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("Erro", "Erro ao salvar aluno: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao salvar aluno: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Erro", "Erro ao salvar aluno: ", t);
            }
        });
    }
}
