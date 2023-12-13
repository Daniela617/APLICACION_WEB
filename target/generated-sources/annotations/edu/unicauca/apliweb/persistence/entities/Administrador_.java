package edu.unicauca.apliweb.persistence.entities;

import edu.unicauca.apliweb.persistence.entities.Compra;
import edu.unicauca.apliweb.persistence.entities.Trabajador;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T19:35:53")
@StaticMetamodel(Administrador.class)
public class Administrador_ { 

    public static volatile SingularAttribute<Administrador, Trabajador> trabajador;
    public static volatile SingularAttribute<Administrador, Integer> ccUsuario;
    public static volatile SingularAttribute<Administrador, Integer> sueldoAdmin;
    public static volatile ListAttribute<Administrador, Compra> compraList;
    public static volatile SingularAttribute<Administrador, Integer> comisionesAdmin;

}