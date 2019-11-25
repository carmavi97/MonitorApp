package es.iesfrancisodelosrios.acmartinez.monitorapp.model;

import java.util.ArrayList;

public class PersonModel {
    public PersonModel() {
    }

    public ArrayList<Person> getAllPersons(){
        ArrayList<Person> items=new ArrayList<>();
        Person person=new Person();
        person.setEmail("carmavi97@gmail.com");
        person.setFullname("Carlos Martinez");
        person.setId(1);
        items.add(person);

        person.setEmail("acmartinez@isfranciscodelosrios.es");
        person.setFullname("Carlos Martinez");
        person.setId(2);
        items.add(person);
        return items;
    }
}
