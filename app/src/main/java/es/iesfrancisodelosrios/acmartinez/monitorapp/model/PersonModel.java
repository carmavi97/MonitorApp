package es.iesfrancisodelosrios.acmartinez.monitorapp.model;

import java.util.ArrayList;

public class PersonModel {
    public PersonModel() {
    }

    public ArrayList<Person> getAllPersons(){
        ArrayList<Person> items=new ArrayList<Person>();
        Person person=new Person();
        person.setEmail("carmavi97@gmail.com");
        person.setFullname("Jose");
        person.setId(1);
        items.add(person);

        Person person2=new Person();
        person2.setEmail("acmartinez@isfranciscodelosrios.es");
        person2.setFullname("Carlos");
        person2.setId(2);
        items.add(person2);
        return items;
    }
}
