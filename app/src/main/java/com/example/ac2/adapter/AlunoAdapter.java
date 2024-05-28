package com.example.ac2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ac2.R;
import com.example.ac2.model.Aluno;
import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {
    private List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.bind(aluno);
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRa, tvNome, tvCep, tvLogradouro, tvComplemento, tvBairro, tvCidade, tvUf;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRa = itemView.findViewById(R.id.tvRa);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvCep = itemView.findViewById(R.id.tvCep);
            tvLogradouro = itemView.findViewById(R.id.tvLogradouro);
            tvComplemento = itemView.findViewById(R.id.tvComplemento);
            tvBairro = itemView.findViewById(R.id.tvBairro);
            tvCidade = itemView.findViewById(R.id.tvCidade);
            tvUf = itemView.findViewById(R.id.tvUf);
        }

        public void bind(Aluno aluno) {
            tvRa.setText(String.valueOf(aluno.getRa()));
            tvNome.setText(aluno.getNome());
            tvCep.setText(aluno.getCep());
            tvLogradouro.setText(aluno.getLogradouro());
            tvComplemento.setText(aluno.getComplemento());
            tvBairro.setText(aluno.getBairro());
            tvCidade.setText(aluno.getCidade());
            tvUf.setText(aluno.getUf());
        }
    }
}
