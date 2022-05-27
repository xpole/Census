import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        //Считаем и выводим число несовершеннолентних
        long stream = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество людей младше 18 лет: " + stream);
        //Отбираем мужчин призывного возраста и выводим список их фамилий
        System.out.println("Cписок фамилий призывников: ");

        List<String> person = persons.stream()
                .filter(youngman -> youngman.getAge() >= 18 && youngman.getAge() <= 27)
                .filter(youngman -> youngman.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        for (String s : person) {
            System.out.println(s);
        }
        //Ищем потенциально трудоспособных людей и выводим их, отсортированных по фамилии
        System.out.println("Cписок фамилий потенциально работоспособных людей: ");

        List<Person> workable = persons.stream()
                .filter(person2 -> person2.getEducation() == Education.HIGHER)
                .filter(person2 -> person2.getSex() == Sex.WOMAN ? person2.getAge() >= 18 && person2.getAge() <= 60
                        : person2.getAge() >= 18 && person2.getAge() <= 65)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        for (Person w : workable) {
            System.out.println(w);
        }
    }
}
