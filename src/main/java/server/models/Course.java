package server.models;

import java.io.Serializable;

/**
 * Classe représentant les données d'un cours.
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    /**
     * Constructeur de la classe <code>Course</code>.
     * @param name Nom du cours.
     * @param code Code du cours.
     * @param session Session durant laquelle le cours est disponible.
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * Accesseur du paramètre <code>name</code>.
     * @return Nom du cours.
     */
    public String getName() {
        return name;
    }

    /**
     * Mutateur du paramètre <code>name</code>.
     * @param name Nom du cours.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accesseur du paramètre <code>code</code>.
     * @return Code du cours.
     */
    public String getCode() {
        return code;
    }

    /**
     * Mutateur du paramètre <code>code</code>.
     * @param code Code du cours.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Accesseur du paramètre <code>session</code>.
     * @return Session durant laquelle le cours est disponible.
     */
    public String getSession() {
        return session;
    }

    /**
     * Mutateur du paramètre <code>session</code>.
     * @param session Session durant laquelle le cours est disponible.
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Méthode retournant la représentation textuelle de l'objet <code>Course</code>.
     * @return
     */
    @Override
    public String toString() {
        return "Course{" +
                  "name="    + name +
                ", code="    + code +
                ", session=" + session +
                '}';
    }
}
