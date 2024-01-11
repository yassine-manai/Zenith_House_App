package dev.mobile.zenithhouseapp;


public class note
{
    private int id;
    private String nom,text;

    public note()
    {
    }

    public note(String nom, String text)
    {
        this.nom=nom; this.text=text;
    }

    public String getNom()
    {
        return nom;
    }

    public int getId()
    {
        return id;
    }

    public String getText()
    {
        return text;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "note{" + "id=" + id + ", nom='" + nom + '\'' + ", text ='" + text + '\'' + '}';
    }
}
