package es.iesfrancisodelosrios.acmartinez.monitorapp.model;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;

public class Person {
    private Long id=null;
    private String photo=null;
    private String email=null;
    private String name=null;
    private String lastName=null;
    private Boolean mtl=false;
    private String section=null;
    private String birthDate=null;
    private String phone=null;
    private String nif=null;

    public Person(String email, String photo, String name,boolean mtl, Long id,String section){
        this.id=id;
        this.email=email;
        this.photo=photo;
        this.name=name;
        this.mtl=mtl;
        this.section=section;
    }

    public Person(String email, String photo, String name, String lastName,boolean mtl,String section,String birthDate, String phone, String nif){
        this.email=email;
        this.photo=photo;
        this.name=name;
        this.mtl=mtl;
        this.birthDate=birthDate;
        this.section=section;
        this.lastName=lastName;
        this.phone=phone;
        this.nif=nif;
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

    public boolean setId(Long id) {
        boolean added=false;
        Pattern pattern = Pattern.compile("^[0-9]*");
        Matcher matcher = pattern.matcher(id.toString());
        if (matcher.find()) {
            this.id=id;
            added=true;
        }
        return added;
    }

    public String getEmail() {

        return email;
    }

    public boolean setEmail(String email) {
        boolean added=false;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            this.email=email;
            added=true;
        }
        return  added;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String fullname) {
        boolean added=false;
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(fullname);
        if (matcher.find()) {
            this.name=fullname;
            added=true;
        }
        return added;
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

    public boolean setSection(String section){
        boolean added=false;
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(section);
        if (matcher.find()) {
            this.section=section;
            added=true;
        }
        return added;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean setBirthDate(String brithDate) {
        boolean added=false;
        Pattern pattern = Pattern.compile("([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$");
        Matcher matcher = pattern.matcher(brithDate);
        if (matcher.find()) {
            this.birthDate=brithDate;
            added=true;
        }
        return added;
    }

    public String getLastName(){
        return this.lastName;
    }

    public boolean setLastName(String lastName){
        boolean added=false;
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(lastName);
        if (matcher.find()) {
            this.lastName=lastName;
            added=true;
        }
        return added;
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        boolean added=false;
        Pattern pattern = Pattern.compile("[0-9]{9}");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.find()) {
            this.phone=phone;
            added=true;
        }
        return added;
    }

    public String getNif() {
        return nif;
    }

    public boolean setNif(String nif) {
        boolean added=false;
        Pattern pattern = Pattern.compile("[0-9]{8,8}[A-Za-z]");
        Matcher matcher = pattern.matcher(nif);
        if (matcher.find()) {
            this.nif=nif;
            added=true;
        }
        return added;
    }
}

