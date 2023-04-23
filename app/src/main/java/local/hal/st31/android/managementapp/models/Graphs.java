package local.hal.st31.android.managementapp.models;

public class Graphs {
    String name;
    int value;
    String graphId;

    public Graphs(String name, int value, String graphId){
        this.name = name;
        this.value = value;
        this.graphId = graphId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getGraphId() {
        return graphId;
    }

    public void setGraphId(String graphId) {
        this.graphId = graphId;
    }
}
