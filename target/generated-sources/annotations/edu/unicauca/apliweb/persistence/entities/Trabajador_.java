package edu.unicauca.apliweb.persistence.entities;

import edu.unicauca.apliweb.persistence.entities.Administrador;
import edu.unicauca.apliweb.persistence.entities.Regente;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T19:35:53")
@StaticMetamodel(Trabajador.class)
public class Trabajador_ { 

    public static volatile SingularAttribute<Trabajador, String> telUsuario;
    public static volatile SingularAttribute<Trabajador, String> contraseniaUsuario;
    public static volatile SingularAttribute<Trabajador, Administrador> administrador;
    public static volatile SingularAttribute<Trabajador, Regente> regente;
    public static volatile SingularAttribute<Trabajador, Integer> ccUsuario;
    public static volatile SingularAttribute<Trabajador, String> nombreUsuario;
    public static volatile SingularAttribute<Trabajador, String> loginUsuario;

}