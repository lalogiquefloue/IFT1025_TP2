/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 16 avril 2023
 */

package server.models;

import java.io.Serializable;

/**
 * Classe représentant les données requises pour l'inscription d'un étudiant. La classe peut être transmise entre le
 * client et le serveur via un "stream".
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * Constructeur de la classe <code>RegistrationForm</code>.
     * @param prenom Prénom de l'étudiant.
     * @param nom Nom de l'étudiant.
     * @param email Email de l'étudiant.
     * @param matricule Matricule de l'étudiant.
     * @param course Cours auquel l'étudiant veut s'inscrire.
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * Accesseur du paramètre <code>prenom</code>.
     * @return Prénom de l'étudiant.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Mutateur du paramètre <code>prenom</code>.
     * @param prenom Prénom de l'étudiant.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Accesseur du paramètre <code>nom</code>.
     * @return Nom de l'étudiant.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Mutateur du paramètre <code>nom</code>.
     * @param nom Nom de l'étudiant.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Accesseur du paramètre <code>email</code>.
     * @return Email de l'étudiant.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Mutateur du paramètre <code>email</code>.
     * @param email Email de l'étudiant.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Accesseur du paramètre <code>matricule</code>.
     * @return Matricule de l'étudiant.
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Mutateur du paramètre <code>matricule</code>.
     * @param matricule Matricule de l'étudiant.
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Accesseur de l'objet <code>course</code>.
     * @return Objet de type <code>Course</code>.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Mutateur de l'objet <code>course</code>.
     * @param course Objet de type <code>Course</code>.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Méthode retournant la représentation textuelle de l'objet <code>RegistrationForm</code>.
     * @return
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
