package ghelani.kshamina.sssc_android_app.model;

public class Term {
    private String terms_id;    //primary key
    private String terms_season;
    private String terms_year;

    //constructor
    public Term(String id, String season, String year){
        this.terms_id = id;
        this.terms_season = season;
        this.terms_year = year;
    }

    public void setTerms_id(String id){
        this.terms_id = id;
    }

    public void setTerms_season(String season){
        this.terms_season = season;
    }

    public void setTerms_year(String year){
        this.terms_year = year;
    }

    public String getTerms_id(){
        return terms_id;
    }

    public String getTerms_season(){
        return terms_season;
    }

    public String getTerms_year(){
        return terms_year;
    }
}
