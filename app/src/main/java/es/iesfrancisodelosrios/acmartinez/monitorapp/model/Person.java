package es.iesfrancisodelosrios.acmartinez.monitorapp.model;

public class Person {
    private Integer id=null;
    private String photo=null;
    private String email=null;
    private String fullname=null;

    public Person(String email, String photo, String fullname, Integer id){
        this.id=id;
        this.email=email;
        this.photo=photo;
        this.fullname=fullname;
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(String email, String fullname, Integer id){
        this.id=id;
        this.email=email;
        this.fullname=fullname;
    }

    public Person() {

    }

    public String getphoto() {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }
}

