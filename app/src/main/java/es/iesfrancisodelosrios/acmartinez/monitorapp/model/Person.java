package es.iesfrancisodelosrios.acmartinez.monitorapp.model;

public class Person {
    private Long id=null;
    private String photo=null;
    private String email=null;
    private String name=null;
    private String lastName=null;
    private Boolean mtl=false;
    private String section=null;
    private String birthDate=null;

    public Person(String email, String photo, String name,boolean mtl, Long id,String section){
        this.id=id;
        this.email=email;
        this.photo=photo;
        this.name=name;
        this.mtl=mtl;
        this.section=section;
    }

    public Person(String email, String photo, String name, String lastName,boolean mtl,String section,String birthDate){
        this.email=email;
        this.photo=photo;
        this.name=name;
        this.mtl=mtl;
        this.birthDate=birthDate;
        this.section=section;
        this.lastName=lastName;
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(String email, String fullname,boolean mtl, Long id,String section){
        this.id=id;
        this.email=email;
        this.name=fullname;
        this.mtl=mtl;
        this.section=section;
    }

    public Person(String email, String fullname, Long id,String img,boolean mtl,String section){
        this.id=id;
        this.email=email;
        this.name=fullname;
        this.photo=img;
        this.mtl=mtl;
        this.section=section;
    }


    public Person() {

    }

    public String getPhoto() {
        return photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String fullname) {
        this.name = fullname;
    }


    public boolean getMtl(){
        return this.mtl;
    }

    public void setMtl(Boolean mtl) {
        this.mtl = mtl;
    }

    public String getSection(){
        return this.section;
    }

    public void setSection(String section){
        this.section=section;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String brithDate) {
        this.birthDate = brithDate;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }
}

