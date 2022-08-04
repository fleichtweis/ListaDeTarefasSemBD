package com.fleichtweis.listadetarefassembd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var editTitulo: EditText
    private lateinit var editData: EditText
    private lateinit var radioGroupTipo: RadioGroup
    private lateinit var radioTrabalho: RadioButton
    private lateinit var radioPessoal: RadioButton
    private lateinit var textTarefas: TextView
    private lateinit var btnAdicionar: Button

    //remover tarefa
    private lateinit var editNumero: EditText
    private lateinit var textRemoverTarefa: TextView
    private lateinit var btnRemover: Button
    private lateinit var linearOpcoes: LinearLayout

    private val listaTarefas = mutableListOf<Tarefa>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTitulo = findViewById(R.id.edit_titulo)
        editData = findViewById(R.id.edit_data)
        radioGroupTipo = findViewById(R.id.radio_group)
        radioTrabalho = findViewById(R.id.radio_trabalho)
        radioPessoal = findViewById(R.id.radio_pessoal)
        textTarefas = findViewById(R.id.text_tarefas)
        btnAdicionar = findViewById(R.id.btn_adicionar)

        //remover tarefa
        editNumero = findViewById(R.id.edit_numero)
        textRemoverTarefa = findViewById(R.id.text_remover)
        btnRemover = findViewById(R.id.btn_remover)
        linearOpcoes = findViewById(R.id.linear_opcoes)


        //Salvar uma tarefa
        btnAdicionar.setOnClickListener {
            salvarTarefa()
        }

        //Remover tarefa
        textRemoverTarefa.setOnClickListener {
            exibirOcultarOpcoes()
        }

        btnRemover.setOnClickListener {
            removerTarefa()
        }

    }

    private fun exibirOcultarOpcoes(){
        if (linearOpcoes.visibility == View.GONE){
            linearOpcoes.visibility = View.VISIBLE
        } else{
            linearOpcoes.visibility = View.GONE
        }
    }

    private fun limparFormulario(){
        editTitulo.setText("")
        editData.setText("")
        radioGroupTipo.clearCheck()

    }

    private fun removerTarefa(){
        if (editNumero.text.isNotEmpty()){
            if (listaTarefas.size > 0) {
                val indice = editNumero.text.toString().toInt()
                if (indice > 0 && indice in 1..listaTarefas.size) {
                    listaTarefas.removeAt(indice - 1)
                    exibirTarefas()
                } else{
                    exibirMensagem("Número da tarefa a ser removida está incorreto.")
                }
            } else{
                exibirMensagem("Não existe item a ser removido.")
            }
        } else{
            exibirMensagem("Preencha um número para remover a tarefa.")
        }
    }

    private fun exibirTarefas(){
        var texto = ""
        listaTarefas.forEachIndexed { indice, it ->
            texto += "${indice+1}) ${it.titulo} - ${it.data} (${it.tipo})\n"
        }
        if (listaTarefas.size > 0) textTarefas.text = texto else textTarefas.text = "Nenhuma tarefa adicionada"
    }

    private fun salvarTarefa(){
        if (validarCampos()){

            //Mutablelist (Tarefas)
            val titulo = editTitulo.text.toString()
            val data = editData.text.toString()
            val tipo = if (radioTrabalho.isChecked) "trabalho" else "pessoal"
            val tarefa = Tarefa(titulo, data, tipo)

            listaTarefas.add(tarefa)

            exibirTarefas()
            limparFormulario()
        }
    }

    private fun validarCampos(): Boolean{
        if (editTitulo.text.isEmpty()){
            exibirMensagem("Preencha um título para a tarefa.")
            return false
        }
        if (editData.text.isEmpty()){
            exibirMensagem("Preencha uma data para a tarefa.")
            return false
        }
        if (!radioTrabalho.isChecked && !radioPessoal.isChecked ){
            exibirMensagem("Preencha um tipo para a tarefa.")
            return false
        }

        return true
    }

    private fun exibirMensagem(mensagem: String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }
}