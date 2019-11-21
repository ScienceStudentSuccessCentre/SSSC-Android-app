package ghelani.kshamina.sssc_android_app.model;

public class Weight {
    private String weights_id;  //primary key
    private String weights_name;
    private Double weights_value;
    private String weights_courseId;    //foreign key refer to Course:courses_id

    public Weight(String id, String name, Double value, String courseId){
        this.weights_courseId = courseId;
        this.weights_id = id;
        this.weights_name = name;
        this.weights_value = value;
    }

    public void setWeights_id(String id){ this.weights_id = id; }

    public void setWeights_name(String name){
        this.weights_name = name;
    }

    public void setWeights_value(Double value){
        this.weights_value = value;
    }

    public void setWeights_courseId(String courseId){
        this.weights_courseId = courseId;
    }

    public String getWeights_id(){
        return weights_id;
    }

    public String getWeights_name(){
        return weights_name;
    }

    public Double getWeights_value(){
        return weights_value;
    }

    public String getWeights_courseId(){
        return weights_courseId;
    }
}
