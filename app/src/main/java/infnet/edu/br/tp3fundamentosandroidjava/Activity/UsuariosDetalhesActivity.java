package infnet.edu.br.tp3fundamentosandroidjava.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import infnet.edu.br.tp3fundamentosandroidjava.R;
import infnet.edu.br.tp3fundamentosandroidjava.model.Usuario;

public class UsuariosDetalhesActivity extends AppCompatActivity {

    private TextView detalhes_nome;
    private TextView detalhes_email;
    private TextView detalhes_senha;
    private TextView detalhes_telefone;
    private TextView detalhes_celular;
    private TextView detalhes_cpf;
    private TextView detalhes_cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_detalhes);

        detalhes_nome = (TextView) findViewById(R.id.detalhes_nome);
        detalhes_email = (TextView) findViewById(R.id.detalhes_email);
        detalhes_senha = (TextView) findViewById(R.id.detalhes_senha);
        detalhes_telefone = (TextView) findViewById(R.id.detalhes_telefone);
        detalhes_celular = (TextView) findViewById(R.id.detalhes_celular);
        detalhes_cpf = (TextView) findViewById(R.id.detalhes_cpf);
        detalhes_cidade = (TextView) findViewById(R.id.detalhes_cidade);

        Intent intent = getIntent();
        Usuario usuarioSelecionado = (Usuario) intent.getSerializableExtra("detalhes");

        String contatoNome = usuarioSelecionado.getNome();
        String contatoEmail = usuarioSelecionado.getEmail();
        String contatoSenha = usuarioSelecionado.getSenha();
        String contatoTelefone = usuarioSelecionado.getTelefone();
        String contatoCelular = usuarioSelecionado.getCelular();
        String contatoCpf = usuarioSelecionado.getCpf();
        String contatoCidade = usuarioSelecionado.getCidade();

        detalhes_nome.setText(contatoNome);
        detalhes_email.setText(contatoEmail);
        detalhes_senha.setText(contatoSenha);
        detalhes_telefone.setText(contatoTelefone);
        detalhes_celular.setText(contatoCelular);
        detalhes_cpf.setText(contatoCpf);
        detalhes_cidade.setText(contatoCidade);
    }
}
