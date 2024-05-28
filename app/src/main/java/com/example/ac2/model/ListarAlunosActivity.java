//Nome:Vanessa Cristina da Silva RA:223253

package com.example.ac2.model;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ac2.R;
import com.example.ac2.network.ApiService;
import com.example.ac2.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.ac2.adapter.AlunoAdapter;


public class ListarAlunosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlunoAdapter alunoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        recyclerView = findViewById(R.id.recyclerViewAlunos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = RetrofitClient.getClient("https://66527305813d78e6d6d58d81.mockapi.io/");
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Aluno>> call = apiService.listarAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful()) {
                    List<Aluno> alunos = response.body();
                    alunoAdapter = new AlunoAdapter(alunos);
                    recyclerView.setAdapter(alunoAdapter);
                } else {
                    Toast.makeText(ListarAlunosActivity.this, "Falha ao obter lista de alunos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListarAlunosActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Erro", "Erro ao obter lista de alunos: ", t);
            }
        });
    }
}
