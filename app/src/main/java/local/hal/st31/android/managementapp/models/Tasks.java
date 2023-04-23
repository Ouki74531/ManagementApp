package local.hal.st31.android.managementapp.models;

public class Tasks {
    String taskId;
    String name;
    String value;

    public Tasks(){

    }


    public Tasks(String taskId, String name, String value) {
        this.taskId = taskId;
        this.name = name;
        this.value = value;
    }

    public Tasks(String name, String value){
        this.name = name;
        this.value =value;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
