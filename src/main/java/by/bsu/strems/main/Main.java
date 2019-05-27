package by.bsu.strems.main;

import by.bsu.strems.item.Item;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // Utility function
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    private static List<String> getRandomListOfProducingCountries(String []counties){
        int origin = 1;
        int bound = 15;
        return Arrays.asList(counties).subList(0, ThreadLocalRandom.current().nextInt(origin, bound));
    }

    public static void main(String[] args) {
        List<Item> itemList =  new ArrayList<>();

        int n = 10;
        for(int i = 0; i < n; i++){
            itemList.add(
                    new Item(new Random().nextDouble()*1000, new Faker().name().title()));
        }

        String []producingCountries = {
                "China", "Japan", "Germany",
                "India", "South Korea", "United States",
                "Spain", "Brazil", "Mexico",
                "France", "United Kingdom", "Iran",
                "Czech Republic", "Russia", "Turkey"};

        itemList.forEach(item ->
                item.setProducingCountries(getRandomListOfProducingCountries(producingCountries)));

        itemList.add(null);
        System.out.println("Original list");
        itemList.forEach(System.out::println);


        System.out.println("\nTask 1");
        System.out.println("Filter list by price and id.\n");

        int filterPrice = 400;
        int filterId = 5000;

        ArrayList<Item> filteredListByPriceAndId = itemList.stream()
                .filter(Objects::nonNull)
                    .filter(item -> item.getPrice() > filterPrice && item.getID() > filterId)
                        .collect(Collectors.toCollection(ArrayList::new));

        filteredListByPriceAndId.forEach(System.out::println);
        System.out.println("End Task 1");


        System.out.println("\nTask 2");
        System.out.println("Sort list by given price in alphabetical order by name of item.\n");
        int sortedPrice = 500;

        ArrayList<Item> sortedList = itemList.stream()
                .filter(Objects::nonNull)
                    .filter(item -> item.getPrice() > sortedPrice)
                        .sorted(
                                Comparator.comparing(Item::getName))
                        .collect(Collectors.toCollection(ArrayList::new));

        sortedList.forEach(System.out::println);
        System.out.println("End Task 2");


        System.out.println("\nTask 3");
        System.out.println("Find in list items by preset id and set them a specified name.\n");

        int presetId = 3500;
        String updateName = "Update name";

        ArrayList<Item> listWithUpdateItemsNames = itemList.stream()
                .filter(Objects::nonNull)
                    .filter(item -> {
                        if (item.getID() > presetId) {
                            item.setName(updateName);
                            return true;
                        }
                        return false;
                    })
                            .collect(Collectors.toCollection(ArrayList::new));
        listWithUpdateItemsNames.forEach(System.out::println);
        System.out.println("End Task 3");


        System.out.println("\nTask 4");
        System.out.println("Skip and limit specified amount of elements.\n");

        int skipCount = 4;
        int limitCount = 3;

        ArrayList<Item> listOfSkipedAndLimitedItems = itemList.stream()
                .filter(Objects::nonNull)
                    .skip(skipCount)
                        .limit(limitCount)
                            .collect(Collectors.toCollection(ArrayList::new));
        listOfSkipedAndLimitedItems.forEach(System.out::println);
        System.out.println("End task 4");


        System.out.println("\nTask 5");
        System.out.println("Find first item by given id and print it\n");

        int presetId2 = 3500;
        String findedName = itemList.stream()
                .filter(item -> item.getID() > presetId2)
                .findFirst()
                .map(t -> {
                    itemList.remove(t);
                    return t;
                })
                .map(Item::getName)
                .orElse("Not found such item name");

        System.out.println("Finded name: " + findedName);
        System.out.println("End task 5");


        System.out.println("\n Task 6");
        System.out.println("Question: at least one item name in UpperCase.\n");
        ArrayList<Item> listWithUpperCaseName =
                itemList.stream()
                        .filter(Objects::nonNull)
                        .filter(item -> {
                            if (item.getID() >= 3160 && item.getID() <= 5000) {
                                item.setName(item.getName().toUpperCase());
                                return true;
                            }
                            return false;
                        }).collect(Collectors.toCollection(ArrayList::new));


        listWithUpperCaseName.forEach(System.out::println);

        boolean answer = listWithUpperCaseName
                .stream()
                .anyMatch(item -> {
                    for (int i = 0; i < item.getName().length(); i++) {
                        if (Character.isUpperCase(item.getName().charAt(i))) {
                            return true;
                        }
                    }
                    return false;
                });
        System.out.println("Answer from task 6 = " + answer);
        System.out.println("End task 6");


        System.out.println("\nTask 7");
        System.out.println("Question: price of each item less than given price.\n");

        itemList.forEach(System.out::println);
        int givenPrice = 800;

        answer = itemList.stream()
                        .filter(Objects::nonNull)
                        .allMatch(item -> item.getPrice() <= givenPrice);
        System.out.println("Answer from task 7 = " + answer);
        System.out.println("End task 7");


        System.out.println("\nTask 8");
        System.out.println("Question: there is NO items with a given id\n");

        itemList.forEach(System.out::println);
        int givenId = 4100;

        answer = itemList.stream()
                .filter(Objects::nonNull)
                .noneMatch(item -> item.getID() == givenId);
        System.out.println("Answer from task 8 = " + answer);
        System.out.println("End task 8");


        System.out.println("\nTask 9");
        System.out.println("Find item with longest name\n");

        Optional<Item> itemWithlongestStringName = Optional.of(itemList.stream()
                .filter(Objects::nonNull)
                .reduce((item1, item2) ->
                        item1.getName().length() > item2.getName().length() ? item1 : item2)
                .orElse(new Item(12345.6, "Name from Else")));

        ArrayList<Item> listAfterOptional = new ArrayList<>();
        itemWithlongestStringName.ifPresent(listAfterOptional::add);

        listAfterOptional.forEach(System.out::println);
        System.out.println("End task 9");


        System.out.println("\nTask 10");
        System.out.println("Simple task of combinations flatMap, skip, limit methods\n");
        List<List<Item>> listOfListItems = new ArrayList<>();

        listOfListItems.add(Collections.singletonList(new Item(12345.6, "Suzuki")));
        listOfListItems.add(Collections.singletonList(new Item(9876.5, "Volvo")));
        listOfListItems.add(Collections.singletonList(new Item(73657.088, "Sedan")));

        listOfListItems.add(Collections.singletonList(new Item(3745.489, "Mercedes")));
        listOfListItems.add(Collections.singletonList(new Item(3657.5365, "Audi")));
        listOfListItems.add(Collections.singletonList(new Item(33567.054548, "Crossover")));

        listOfListItems.add(Collections.singletonList(new Item(5465.24, "Honda")));
        listOfListItems.add(Collections.singletonList(new Item(11212.55, "Skoda")));
        listOfListItems.add(Collections.singletonList(new Item(7473.058, "Cabriolet")));

        listOfListItems
                .forEach(list -> list
                        .forEach(item -> item
                                .setProducingCountries(getRandomListOfProducingCountries(producingCountries))));


        int skipNum = 2;
        int limitNum = 3;
        List<Item> flatMapListItems = listOfListItems
                .stream()
                .flatMap(Collection::stream).collect(Collectors.toList())
                .stream()
                .skip(skipNum)
                .limit(limitNum)
                .collect(Collectors.toCollection(ArrayList::new));

        flatMapListItems.forEach(System.out::println);
        System.out.println("End task 10");


        System.out.println("\nTask 11");
        System.out.println("Find items with distinct names\n");
        itemList.add(new Item(12345.6, "Suzuki", getRandomListOfProducingCountries(producingCountries)));
        itemList.add(new Item(9876.5, "Volvo", getRandomListOfProducingCountries(producingCountries)));
        itemList.add(new Item(12345.6, "Suzuki", getRandomListOfProducingCountries(producingCountries)));
        itemList.add(new Item(73657.088, "Sedan", getRandomListOfProducingCountries(producingCountries)));
        itemList.add(new Item(9876.5, "Volvo", getRandomListOfProducingCountries(producingCountries)));

        ArrayList<Item> listOfDistinctItems = itemList
                .stream()
                .filter(Objects::nonNull)
                .filter(distinctByKey(Item::getName))
                .collect(Collectors.toCollection(ArrayList::new));

        listOfDistinctItems.forEach(System.out::println);
        System.out.println("End task 11");


        System.out.println("\n Task 12");
        System.out.println("Find count items with distinct names\n");
        itemList.forEach(System.out::println);

        int countDistinctItemsNames = (int) itemList
                .stream()
                .filter(Objects::nonNull)
                .map(Item::getName)
                .distinct()
                .count();

        System.out.println("Count distinct items names: " + countDistinctItemsNames);
        System.out.println("End task 12");


        System.out.println("\nTask 13");
        System.out.println("Print all distinct countries occurring in items\n");

        List<String> countries = itemList
                .stream()
                .filter(Objects::nonNull)
                .flatMap(item -> item.getProducingCountries().stream())
                .distinct()
                .collect(Collectors.toList());

        countries.forEach(System.out::println);
        System.out.println("End task 13");


        System.out.println("\n Task 14");
        System.out.println("Count of items that have a producing country Japan\n");

        String jap = "Japan";

        int japanProducing = (int) itemList
                .stream()
                .filter(Objects::nonNull)
                .flatMap(item ->
                        item.getProducingCountries()
                        .stream())
                        .filter(country -> country.equals(jap))
                        .count();

        System.out.println("Count of items that have a producing country Japan: " + japanProducing);
        System.out.println("End task 14");

    }
}
