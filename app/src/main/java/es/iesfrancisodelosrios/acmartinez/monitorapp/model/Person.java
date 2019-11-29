package es.iesfrancisodelosrios.acmartinez.monitorapp.model;

public class Person {
    private Integer id=null;
    private String photo=null;
    private String email=null;
    private String name=null;


    public Person(String email, String photo, String name, Integer id){
        this.id=id;
        this.email=email;
        this.photo=photo;
        this.name=name;
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(String email, String fullname, Integer id){
        this.id=id;
        this.email=email;
        this.name=fullname;
    }

    public Person(String email, String fullname, Integer id,String img){
        this.id=id;
        this.email=email;
        this.name=fullname;
    }


    public Person() {

    }

    public String getPhoto() {
        return photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFullname() {
        return name;
    }

    public void setFullname(String fullname) {
        this.name = fullname;
    }


}

