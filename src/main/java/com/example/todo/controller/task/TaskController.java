package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor //finalが付いているフィールドを初期化するコンストラクタ
@RequestMapping("/tasks") // "/tasks"を省略できる
public class TaskController {
    // finalは初期化されないとエラーになる、beanの取得漏れを防ぐ
    private final TaskService taskService;

    /* 8080/tasksにGETリクエストが来たら、list()が動く */
    @GetMapping
    public String list(TaskSearchForm searchForm ,Model model) {
        /*TaskSearchEntityをTaskDTOに変換する*/
        var taskList = taskService.find(searchForm.toEntity())
                .stream()
                .map(TaskDTO::toDTO)
                .toList();

        /* taskListという名前で、taskServiceのfindメソッドで作成したtaskDTOが渡される */
        model.addAttribute("taskList", taskList);
        model.addAttribute("searchDTO", searchForm.toDTO());
        return "tasks/list";
    }

    /* 8080/tasks/detailにGETリクエストが来たら、showDetail()が動く */
    /* {id}と@PathValiable("id")が紐づいている*/
    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model){

        /* taskIdでレコードを取得するメソッド*/
        var taskDTO = taskService.findById(taskId)
                .map(TaskDTO::toDTO)
                // 戻り値がnullの場合、例外にスローする
                .orElseThrow(TaskNotFoundException::new);

        /* taskという名前で、taskDTOをhtmlに渡す*/
        model.addAttribute("task",taskDTO);
        return "tasks/detail";
    }

    /* 8080/tasks/creationFormにGETリクエストが来たら、showCreationFormが動く */
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form, Model model){
            //タスク一覧画面から作成ボタンが押された場合（@ModelAttributeで省略できる）
                //if(form == null){
                    //要素がnullのformを作成する（タスク作成画面に遷移する）
                    //form = new TaskForm(null,null, null);
                //}
        //taskFormという名前でformを渡す、@ModelAttributeで省略できる
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }

    //作成ボタンが押された時に発動するメソッド
    //TaskFormクラスに、summary,description,statusフィールドが設定されている
    //TaskFormでFormタグ内のname属性を受け取る
    //受け取った情報から新しくTaskEntityを作成する
    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model){
        //formのvalidationエラーが発生した場合（概要を空文字で作成した場合など）
        if(bindingResult.hasErrors()){
            //showCreationFormメソッドに、作成ボタンが押された時点のformを渡す
            return showCreationForm(form, model);
        }

    //formをTaskEntityクラスに変換して、新しいタスクを作成する
    taskService.create(form.toEntity());

        //２重サブミット防止(リロード時にタスクが作成されないようにする処理)
        //作成ボタンを押す、postリクエスト完了→302found→locationヘッダにあるURLをGETする→タスク一覧画面を表示する
        //redirect:をつけることで、リロード時にtask一覧画面を表示する
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model){
        var form = taskService.findById(id)
                .map(TaskForm::fromEntity)
                        //idがnullの場合例外をスローする,メソッド参照という書き方
                        .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("taskForm", form);
        model.addAttribute("mode", "EDIT");
        return "tasks/form";
    }

    // 編集formで入力された値を反映させるメソッド
    @PutMapping("{id}")
    public String update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute TaskForm form,
            BindingResult bindingResult,
            Model model
    ){
        // 編集内容にエラーがある場合、編集前のformを再表示する
        if(bindingResult.hasErrors()){
            //@ModelAttributeで省略できる↓
            //model.addAttribute("taskForm", form);
            model.addAttribute("mode","EDIT");
            return "tasks/form";
        }

        //idを指定してtaskFormをtaskEntityに変換する
        var entity = form.toEntity(id);
        taskService.update(entity);
        return "redirect:/tasks/{id}";
    }

    //削除ボタンが押された時のメソッド
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
