package dev.mobile.zenithhouseapp;


public class Contact
{
    private int id;
    private String nom,number;

    public Contact()
    {
    }

    public Contact(String nom, String number)
    {
        this.nom=nom; this.number=number;
    }

    public String getNom()
    {
        return nom;
    }

    public int getId()
    {
        return id;
    }

    public String getNumber()
    {
        return number;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "Contact{" + "id=" + id + ", nom='" + nom + '\'' + ", number='" + number + '\'' + '}';
    }
}
