package infnet.edu.br.tp3fundamentosandroidjava.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import infnet.edu.br.tp3fundamentosandroidjava.R;
import infnet.edu.br.tp3fundamentosandroidjava.config.ConfiguracaoFirebase;
import infnet.edu.br.tp3fundamentosandroidjava.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editSenha;
    private EditText editEmail;
    private EditText editTelefone;
    private EditText editCelular;
    private EditText editCpf;
    private EditText editCidade;
    private Button btnSalvar;
    private Button btnLimpar;
    private Button btnVisualizarUsuarios;
    private ProgressBar progressBar;

    private Usuario usuario;
    private FirebaseAuth autentificao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.edit_nome);
        editSenha = (EditText) findViewById(R.id.edit_senha);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editTelefone = (EditText) findViewById(R.id.edit_telefone);
        editCelular = (EditText) findViewById(R.id.edit_celular);
        editCpf = (EditText) findViewById(R.id.edit_cpf);
        editCidade = (EditText) findViewById(R.id.edit_cidade);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnVisualizarUsuarios = (Button) findViewById(R.id.btnVisualizarUsuarios);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String nome = editNome.getText().toString();
                String senha = editSenha.getText().toString();
                String email = editEmail.getText().toString();
                String telefone = editTelefone.getText().toString();
                String celular = editCelular.getText().toString();
                String cpf = editCpf.getText().toString();
                String cidade = editCidade.getText().toString();

                // Verifica se algum campo esta vazio
                if (nome.isEmpty() || senha.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
                        celular.isEmpty() || cpf.isEmpty() || cidade.isEmpty()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),
                            "Todos os campos devem ser preenchidos",
                            Toast.LENGTH_SHORT)
                            .show();

                    // Se estiver tudo ok passa os dados para a classe usuário
                } else {

                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setSenha(senha);
                    usuario.setEmail(email);
                    usuario.setTelefone(telefone);
                    usuario.setCelular(celular);
                    usuario.setCpf(cpf);
                    usuario.setCidade(cidade);
                    // chama o método que fará o procedimento de adicionar ao Firebase
                    cadastrar();
                    //cadastrarUsuario();

                    // esconde o teclado
                    esconderTeclado(MainActivity.this);
                }
            }

        }); // Fim btnSalvar OnClickListener

        btnVisualizarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UsuariosActivity.class);
                startActivity(intent);
            }
        }); // Fim btnVisualizarContatos OnClickListener

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Apagar usuários");
                alertDialog.setMessage("Deseja apagar todos os usuários salvos?");
                alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
                        databaseReference.child("usuarios").removeValue();

                        Toast.makeText(
                                getApplicationContext(),
                                "Todos os usuários foram apagados",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }); // Fim SetPositive Button

                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }); // Fim NegativeButton

                alertDialog.create();
                alertDialog.show();
            }
        });
    } // Fim onCreate

    private void cadastrarUsuario(){
        autentificao = ConfiguracaoFirebase.getFirebaseAuth();
        autentificao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            usuario.setId(task.getResult().getUser().getUid());

                            DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("usuarios");

                            databaseReference.child(usuario.getId())
                                             .child("nome")
                                             .setValue(usuario.getNome());

                            databaseReference.child(usuario.getId())
                                             .child("senha")
                                             .setValue(usuario.getSenha());

                            databaseReference.child(usuario.getId())
                                             .child("email")
                                             .setValue(usuario.getEmail());

                            databaseReference.child(usuario.getId())
                                             .child("telefone")
                                             .setValue(usuario.getTelefone());

                            databaseReference.child(usuario.getId())
                                             .child("celular")
                                             .setValue(usuario.getCelular());

                            databaseReference.child(usuario.getId())
                                             .child("cpf")
                                             .setValue(usuario.getCpf());

                            databaseReference.child(usuario.getId())
                                             .child("cidade")
                                             .setValue(usuario.getCidade());

                        Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                        } // Fim isSuccessfull
                        else {
                            String erroExecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroExecao = "Digite uma senha mais forte de pelo menos 6 digitos, cntendo mais caracteres com letras e números.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExecao = "E-mail ou senha inválidos.";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erroExecao = "E-mail já está cadastrado!";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), "Erro: " + erroExecao, Toast.LENGTH_SHORT).show();
                        }

                    }
                }); // fim OnCompleteListener
    }

    private void cadastrar(){

        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("usuarios");
        DatabaseReference newUser = databaseReference.push();
        newUser.setValue("usuarios", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), "Não foi possível realizar cadastro", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("nome")
                            .setValue(usuario.getNome());

                    databaseReference.child("senha")
                            .setValue(usuario.getSenha());

                    databaseReference.child("email")
                            .setValue(usuario.getEmail());

                    databaseReference.child("telefone")
                            .setValue(usuario.getTelefone());

                    databaseReference.child("celular")
                            .setValue(usuario.getCelular());

                    databaseReference.child("cpf")
                            .setValue(usuario.getCpf());

                    databaseReference.child("cidade")
                            .setValue(usuario.getCidade());

                    Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                    editNome.setText("");
                    editEmail.setText("");
                    editSenha.setText("");
                    editTelefone.setText("");
                    editCelular.setText("");
                    editCpf.setText("");
                    editCidade.setText("");

                    progressBar.setVisibility(View.INVISIBLE);

                } // Fim else
            } // Fim onComplete
        }); // Fim CompletionListener()

    } // Fim cadastrar()

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
