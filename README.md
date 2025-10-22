# android-project-listas

Crie uma data class chamada Task com os campos:
id: Long → use System.currentTimeMillis() para gerar um ID único.
title: String → título da tarefa.
isCompleted: Boolean → indica se a tarefa foi concluída.
Estruture o layout de cada item da lista com:
Um CheckBox para marcar a tarefa como concluída.
Um TextView para exibir o título.
Um ImageButton (ícone de lixeira) para remover a tarefa.
Implemente um DiffUtil.ItemCallback<Task> para comparar itens e detectar mudanças na lista.
Crie um TaskAdapter herdando de ListAdapter<Task, ...>.
No ViewHolder, configure os listeners de clique para o CheckBox e o ImageButton.
Use no construtor para comunicar cliques de volta à Activity/Fragment, por exemplo:
class TaskAdapter(val onTaskClicked: (Task) -> Unit,
val onDeleteClicked: (Task) -> Unit)
Chame essas lambdas dentro dos listeners do ViewHolder.
Além do RecyclerView, adicione:
Um EditText para digitar o título da tarefa.
Um Button ("Adicionar") para inserir novas tarefas.
Mantenha uma lista mutável de tarefas:
private var taskList = mutableListOf<Task>()
Configure o RecyclerView com o TaskAdapter.
no clique do botão, crie uma nova Task, adicione à lista e chame:
adapter.submitList(taskList.toList())

(o .toList() é importante para o DiffUtil detectar mudanças).
na lambda onTaskClicked, atualize o campo isCompleted da tarefa e chame submitList.
na lambda onDeleteClicked, remova a tarefa da lista e chame submitList.

Resultado Esperado

Ao final, você terá uma aplicação funcional que demonstra o poder e a simplicidade do ListAdapter, com para adicionar, remover e atualizar itens da lista.
