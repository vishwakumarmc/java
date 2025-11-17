package com.pack.test;

import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sample {
    public static void main(String[] args) {

        LocalDate dob = LocalDate.of(1984, 8, 11);
        int dbofYear = dob.getYear();
        int currentDay = LocalDate.now().getYear();
        System.out.println(currentDay - dbofYear);

        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 2, 28);
        List<LocalDate> dates = new ArrayList<>();
        while (startDate.isBefore(endDate)) {
            dates.add(startDate);
            startDate = startDate.plusDays(1);
        }
        System.out.println(startDate);

        LocalDate sDate = LocalDate.parse("1984-08-11");
        LocalDate eDate = LocalDate.parse("2025-08-11");

        Period p = Period.between(sDate, eDate);
        System.out.println("Year: "+p);

        System.out.print("Equality Test: ");
        System.out.println((0.1+0.2)==0.3);

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        list.forEach(i->System.out.println(i));

        List<Integer> square = list.stream().map(i->i*i).collect(Collectors.toList());
        System.out.println(square);

        int even = list.stream().filter(i->i%2==0).collect(Collectors.toList()).size();
        System.out.println("Even Size: "+even);

        //Sum of even numbers
        int sum = list.stream().filter(i->i%2==0).reduce(0,(a,b)->a+b);
        System.out.println("Sum: "+sum);

        System.out.println("------------------Product-------------------");
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Samsung ", 17000f));
        products.add(new Product(3, "Iphone ", 65000f));
        products.add(new Product(2, "Sony ", 25000f));
        products.add(new Product(4, "Nokia ", 15000f));
        products.add(new Product(5, "Redmi ", 26000f));
        products.add(new Product(6, "Lenevo ", 19000f));

        //Filter based on name
        Predicate<Product> name = p1->p1.name.startsWith("S");
        Stream<Product> s1 = products.stream().filter(name);
        s1.forEach(pr->System.out.println(pr.name));

        //Sum of all prices
        Float totalPrice = products.stream().map(i->i.price).reduce(0f,(a,b)->a+b);
        System.out.println(totalPrice);

        //Filter based on price
        Predicate<Product> price = p1->p1.price>20000f;
        Stream<Product> s = products.stream().filter(price);
        s.forEach(pr->System.out.println(pr.price));

        //Count
        long count = products.stream().filter(price).count();
        System.out.println("Count: "+count);

        //Min
        Product min = products.stream().min(Comparator.comparing(Product::getPrice)).get();
        System.out.println("Minimum Price: "+min.price);

        //Max
        Product max = products.stream().max((p1,p2)->p1.price>p2.price?1:-1).get();
        System.out.println("Maximum Price: "+max.price);

        //Sort
        List<Product> sorted = products.stream().sorted((p1,p2)->p1.price>p2.price?1:-1).collect(Collectors.toList());
        sorted.forEach(pr->System.out.println(pr.price));

        List<Product> sort = products.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        sort.forEach(pr->System.out.println(pr.price));

        System.out.println("------------------Items-------------------");
        List<Item>  items = new ArrayList<>();
        items.add(new Item("Pen", 10, new BigDecimal("9.99")));
        items.add(new Item("Book", 20, new BigDecimal("19.99")));
        items.add(new Item("Pencil", 10, new BigDecimal("29.99")));
        items.add(new Item("Notebook", 20, new BigDecimal("19.99")));
        items.add(new Item("Scale", 30, new BigDecimal("29.99")));
        items.add(new Item("Eraser", 10, new BigDecimal("9.99")));
        items.add(new Item("Pen", 20, new BigDecimal("9.99")));

        //Sum of all prices by name
        Map<String,Integer> prices = items.stream()
                .collect(Collectors.groupingBy(Item::getName,
                        TreeMap::new,
                        Collectors.summingInt(Item::getQty)));
        System.out.println("Group by name: "+ prices);

        Map<Integer,List<String>> groupByQty = items.stream()
                .collect(Collectors.groupingBy(Item::getQty,
                        TreeMap::new,
                        Collectors.mapping(Item::getName, Collectors.toList())));
        System.out.println("Group by quantity: "+groupByQty);

        Map<BigDecimal,List<String>> groupByPrice = items.stream()
                .collect(Collectors.groupingBy(Item::getPrice,
                        TreeMap::new,
                        Collectors.mapping(Item::getName, Collectors.toList())));
        System.out.println("Group by price: "+groupByPrice);

        //Group by length and count
        List<String> str = Arrays.asList("kiwi","apple","banana","orange","grapes","peach");
        Map<Integer,Long> counts = str.stream().
                collect(Collectors.groupingBy(String::length, Collectors.collectingAndThen(Collectors.counting(), c->c)));
        System.out.println("Count by length: "+counts);

        //Group by identity and count occurrence
        List<String> lists = Arrays.asList("kiwi","apple","banana","apple","orange","kiwi","apple");
        Map<String,Long> occurences = lists.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Count by identity: "+occurences);

        //Concurrent modification exception on ArrayList
        //List<String> l = new ArrayList<>();
        List<String> l = new CopyOnWriteArrayList<>();

        l.add("one");
        l.add("two");
        l.add("three");

        try{
            Iterator<String> i = l.iterator();
            while(i.hasNext()){
                System.out.println(i.next() +", ");
                l.add("four");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       // System.out.println(l);

    }

}
