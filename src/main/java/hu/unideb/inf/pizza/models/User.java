package hu.unideb.inf.pizza.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * Egy vásárlót reprezentáló osztály.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * A felhasználó egyedi azonosítója.
     */
    private int id;

    /**
     * A felhasználó neve.
     */
    private String name;

    /**
     * A felhasználó email címe.
     */
    private String email;

    /**
     * A felhasználó jelszava.
     */
    private String password;

    /**
     * A felhasználó címe.
     */
    private String address;

    /**
     * A felhasználó telefonszáma.
     */
    private String phone;

    /**
     * A felhasználó rendelései.
     */
    private List<Order> orders;

    /**
     * Az osztály paraméter nélküli konstruktora.
     */
    public User() {
        super();
    }

    /**
     * Konstruktor egy felhasználót reprezentáló objektum létrehozására.
     *
     * @param name      A felhasználó neve
     * @param email     A felhasználó email címe
     * @param address   A felhasználó címe
     * @param phone     A felhasználó telefonszáma
     */
    public User(String name, String email, String address, String phone) {
        this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

    /**
     * Visszaadja a felhasználó egyedi azonosítóját.
     *
     * @return A felhasználó egyedi azonosítója
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Beállítja a felhasználó egyedi azonosítóját.
     *
     * @param id A felhasználó egyedi azonosítója
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Visszaadja a felhasználó nevét.
     *
     * @return A felhasználó neve
     */
    @Basic
    @Column(name = "name")
    @NotEmpty(message = "A név mező nem lehet üres.")
    public String getName() {
        return name;
    }

    /**
     * Beállítja a felhasználó nevét.
     *
     * @param name A felhasználó neve
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Visszaadja a felhasználó email címét.
     *
     * @return A felhasználó email címe
     */
    @Basic
    @Column(name = "email", unique = true)
    @Email(message = "Helytelen email címet adott meg.")
    @NotEmpty(message = "Az email cím mező nem lehet üres.")
    public String getEmail() {
        return email;
    }

    /**
     * Beállítja a felhasználó email címét.
     *
     * @param email A felhasználó email címe
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Visszaadja a felhasználó jelszavát.
     *
     * @return A felhasználó jelszava
     */
    @Basic
    @Column(name = "password")
    @Length(min = 5, message = "Minimum 5 karakter hosszú jelszót válasszon.")
    @NotEmpty(message = "A jelszó mező nem lehet üres.")
    public String getPassword() {
        return password;
    }

    /**
     * Beállítja a felhasználó jelszavát.
     *
     * @param password A felhasználó jelszava
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Ellenőrzi, hogy a megadott jelszó és a felhasználó jelszava megegyezik-e.
     *
     * @param password Az ellenőrzendő jelszó
     * @return Igaz, ha a megadott jelszó megegyezik a felhasználó jelszavával
     */
    public boolean checkPassword(String password) {
        return (this.password != null && this.password.equals(password));
    }

    /**
     * Visszaadja a felhasználó címét.
     *
     * @return A felhasználó címe
     */
    @Basic
    @Column(name = "address")
    @NotEmpty(message = "A cím mező nem lehet üres.")
    public String getAddress() {
        return address;
    }

    /**
     * Beállítja a felhasználó címét.
     *
     * @param address A felhasználó címe
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Visszaadja a felhasználó telefonszámát.
     *
     * @return A felhasználó telefonszáma
     */
    @Basic
    @Column(name = "phone")
    @NotEmpty(message = "A telefonszám mező nem lehet üres.")
    public String getPhone() {
        return phone;
    }

    /**
     * Beállítja a felhasználó telefonszámát.
     *
     * @param phone A vásáló telefonszáma
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Visszaadja a felhasználó rendeléseit.
     *
     * @return A felhasználó rendelései
     */
    @OneToMany(mappedBy = "user")
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Beállítja a felhasználó rendeléseit.
     *
     * @param orders A felhasználó rendelései
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
