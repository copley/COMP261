package week_03_04_2015;

import java.util.*;

class Cat {
  protected String name;
  public Cat(String name) { this.name = name; }
  public void fight(Cat target, List<String> log) {
    log.add(name + " claws " + target.name);
    target.fightBack(this,log);
  }
  public void fightBack(Cat target, List<String> log) {
    log.add(name + " claws back " + target.name);
  }
}

class RoughCat extends Cat {
  public RoughCat(String name) {
    super(name);
  }

  public void fight(Cat target, List<String> log) {
    log.add(name + " bites " + target.name);
    target.fightBack(this,log);
  }

  public void fightBack(RoughCat target, List<String> log) {
    log.add(name + " bites back " + target.name);
  }
}

public class SA026_Polymorphism_Poly9 {
  public static void main(String[] args) {
    List<String> log = new ArrayList<String>();

    Cat jim = new Cat("Jim");
    Cat bob = new RoughCat("Bob");
    bob.fight(jim,log);

//    assert log.toString().equals([???]);
    System.out.println(log.toString());
  }
}