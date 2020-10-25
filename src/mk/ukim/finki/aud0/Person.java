package mk.ukim.finki.aud0;

import java.util.Objects;

public class Person implements Comparable<Person>{
    private String name;
    private String lastName;
    private int age;

    public Person(){}

    public Person(String name, String lastName, int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.age = 18;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
                name.equals(person.name) &&
                lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age,o.age);
    }

    public static void main(String[] args) {
        Person person1 = new Person("Hristijan","Todorovski", 22);
        Person person2 = new Person("Hristijan","Todorovski", 23);

        System.out.println(person1.equals(person2));


    }
}
