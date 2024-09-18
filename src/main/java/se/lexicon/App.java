package se.lexicon;

import org.w3c.dom.ls.LSOutput;
import se.lexicon.dao.DBDao.DBDaoImpl.PeopleDaoImp;
import se.lexicon.model.Person;

import java.util.Collection;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        PeopleDaoImp dao = new PeopleDaoImp();
        //Person person = dao.create(new Person("Musse","Pigg"));;
        //person.setFirstName("Testar");
        //person = dao.update(person);
        //System.out.println(person);
        Collection<Person> olikalistor = dao.findByName("Kalle");
        System.out.println(olikalistor);
        boolean delete = dao.deleteById(1);
        Collection<Person> alla = dao.findAll();
        System.out.println(alla);

    }


}
