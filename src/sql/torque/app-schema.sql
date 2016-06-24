
-----------------------------------------------------------------------------
-- centros
-----------------------------------------------------------------------------
DROP TABLE centros CASCADE;


CREATE TABLE centros
(
                                    idcentro serial,
                                    nombre varchar (50) default '',
                                    direccion varchar (50) default '',
                                    cp varchar (5) default '',
                                      -- REFERENCES localidades (idlocalidad)
    idlocalidad integer default 0,
                                    telefono varchar (9) default '',
                                    fax varchar (9) default '',
                                    contacto varchar (50) default '',
                                    email varchar (50) default '',
    PRIMARY KEY (idcentro)
);


-----------------------------------------------------------------------------
-- composiciones
-----------------------------------------------------------------------------
DROP TABLE composiciones CASCADE;


CREATE TABLE composiciones
(
                                    idcomposicion serial,
                                      -- REFERENCES materiales (idmaterial)
    idmaterial integer default 0,
                                      -- REFERENCES productos (idproducto)
    idproducto integer default 0,
                                    unidades float default 0,
    PRIMARY KEY (idcomposicion)
);


-----------------------------------------------------------------------------
-- localidades
-----------------------------------------------------------------------------
DROP TABLE localidades CASCADE;


CREATE TABLE localidades
(
                                    idlocalidad serial,
                                      -- REFERENCES provincias (idprovincia)
    idprovincia integer default 0,
                                    nombre varchar (50) default '',
    PRIMARY KEY (idlocalidad)
);


-----------------------------------------------------------------------------
-- materiales
-----------------------------------------------------------------------------
DROP TABLE materiales CASCADE;


CREATE TABLE materiales
(
                                    idmaterial serial,
                                    nombre varchar (50) default '',
                                      -- REFERENCES unidadesmedida (idunidadmedida)
    idunidadmedida integer default 0,
                                    coste float default 0,
    PRIMARY KEY (idmaterial)
);


-----------------------------------------------------------------------------
-- operarios
-----------------------------------------------------------------------------
DROP TABLE operarios CASCADE;


CREATE TABLE operarios
(
                                    idoperario serial,
                                      -- REFERENCES perfiles (idperfil)
    idperfil integer default 0,
                                    nif varchar (10) default '',
                                    nombre varchar (50) default '',
                                    apellidouno varchar (50) default '',
                                    apellidodos varchar (50) default '',
                                    fechaalta varchar (8) default '',
                                    fechabaja varchar (8) default '',
                                    numeross varchar (20) default '',
                                    direccion varchar (50) default '',
                                    cp varchar (5) default '',
                                      -- REFERENCES localidades (idlocalidad)
    idlocalidad integer default 0,
                                    telefono varchar (9) default '',
                                    movil varchar (9) default '',
                                    email varchar (25) default '',
    PRIMARY KEY (idoperario)
);


-----------------------------------------------------------------------------
-- partes
-----------------------------------------------------------------------------
DROP TABLE partes CASCADE;


CREATE TABLE partes
(
                                    idparte serial,
                                      -- REFERENCES productos (idproducto)
    idproducto integer default 0,
                                      -- REFERENCES operarios (idoperario)
    idoperario integer default 0,
                                      -- REFERENCES turnos (idturno)
    idturno integer default 0,
                                    fecha varchar (8) default '',
                                    descripcion varchar (50) default '',
                                    observaciones varchar (255) default '',
                                    horas float default 0,
                                    unidades float default 0,
    PRIMARY KEY (idparte)
);


-----------------------------------------------------------------------------
-- perfiles
-----------------------------------------------------------------------------
DROP TABLE perfiles CASCADE;


CREATE TABLE perfiles
(
                                    idperfil serial,
                                    descripcion varchar (50) default '',
    PRIMARY KEY (idperfil)
);


-----------------------------------------------------------------------------
-- perfilturno
-----------------------------------------------------------------------------
DROP TABLE perfilturno CASCADE;


CREATE TABLE perfilturno
(
                                    idtp serial,
                                      -- REFERENCES perfiles (idperfil)
    idperfil integer default 0,
                                      -- REFERENCES turnos (idturno)
    idturno integer default 0,
                                    valorcoste float default 0,
    PRIMARY KEY (idtp)
);


-----------------------------------------------------------------------------
-- producciones
-----------------------------------------------------------------------------
DROP TABLE producciones CASCADE;


CREATE TABLE producciones
(
                                    idproduccion serial,
                                    fechainicio varchar (8) default '',
                                    fechafin varchar (8) default '',
                                      -- REFERENCES tipotrabajos (idtipotrabajo)
    idtipotrabajo integer default 0,
                                      -- REFERENCES estadosproduccion (idestadoproduccion)
    idestadoproduccion integer default 0,
                                      -- REFERENCES centros (idcentro)
    idcentro integer default 0,
    PRIMARY KEY (idproduccion)
);


-----------------------------------------------------------------------------
-- productos
-----------------------------------------------------------------------------
DROP TABLE productos CASCADE;


CREATE TABLE productos
(
                                    idproducto serial,
                                      -- REFERENCES producciones (idproduccion)
    idproduccion integer default 0,
                                    fecha varchar (8) default '',
                                    referencia varchar (20) default '',
                                    almacen varchar (50) default '',
    PRIMARY KEY (idproducto)
);


-----------------------------------------------------------------------------
-- provincias
-----------------------------------------------------------------------------
DROP TABLE provincias CASCADE;


CREATE TABLE provincias
(
                                    idprovincia serial,
                                    nombre varchar (50) default '',
    PRIMARY KEY (idprovincia)
);


-----------------------------------------------------------------------------
-- tipotrabajos
-----------------------------------------------------------------------------
DROP TABLE tipotrabajos CASCADE;


CREATE TABLE tipotrabajos
(
                                    idtipotrabajo serial,
                                    nombre varchar (50) default '',
                                    descripcion varchar (255) default '',
                                    coste float default 0,
    PRIMARY KEY (idtipotrabajo)
);


-----------------------------------------------------------------------------
-- turnos
-----------------------------------------------------------------------------
DROP TABLE turnos CASCADE;


CREATE TABLE turnos
(
                                    idturno serial,
                                    descripcion varchar (50) default '',
    PRIMARY KEY (idturno)
);


-----------------------------------------------------------------------------
-- estadosproduccion
-----------------------------------------------------------------------------
DROP TABLE estadosproduccion CASCADE;


CREATE TABLE estadosproduccion
(
                                    idestadoproduccion serial,
                                    descripcion varchar (50) default '',
    PRIMARY KEY (idestadoproduccion)
);


-----------------------------------------------------------------------------
-- unidadesmedida
-----------------------------------------------------------------------------
DROP TABLE unidadesmedida CASCADE;


CREATE TABLE unidadesmedida
(
                                    idunidadmedida serial,
                                    descripcion varchar (50) default '',
    PRIMARY KEY (idunidadmedida)
);


----------------------------------------------------------------------
-- unidadesmedida                                                      
----------------------------------------------------------------------

ALTER TABLE centros
    ADD CONSTRAINT centros_FK_1 FOREIGN KEY (idlocalidad)
    REFERENCES localidades (idlocalidad)
;

----------------------------------------------------------------------
-- centros                                                      
----------------------------------------------------------------------

ALTER TABLE composiciones
    ADD CONSTRAINT composiciones_FK_1 FOREIGN KEY (idmaterial)
    REFERENCES materiales (idmaterial)
;
ALTER TABLE composiciones
    ADD CONSTRAINT composiciones_FK_2 FOREIGN KEY (idproducto)
    REFERENCES productos (idproducto)
;

----------------------------------------------------------------------
-- composiciones                                                      
----------------------------------------------------------------------

ALTER TABLE localidades
    ADD CONSTRAINT localidades_FK_1 FOREIGN KEY (idprovincia)
    REFERENCES provincias (idprovincia)
;

----------------------------------------------------------------------
-- localidades                                                      
----------------------------------------------------------------------

ALTER TABLE materiales
    ADD CONSTRAINT materiales_FK_1 FOREIGN KEY (idunidadmedida)
    REFERENCES unidadesmedida (idunidadmedida)
;

----------------------------------------------------------------------
-- materiales                                                      
----------------------------------------------------------------------

ALTER TABLE operarios
    ADD CONSTRAINT operarios_FK_1 FOREIGN KEY (idlocalidad)
    REFERENCES localidades (idlocalidad)
;
ALTER TABLE operarios
    ADD CONSTRAINT operarios_FK_2 FOREIGN KEY (idperfil)
    REFERENCES perfiles (idperfil)
;

----------------------------------------------------------------------
-- operarios                                                      
----------------------------------------------------------------------

ALTER TABLE partes
    ADD CONSTRAINT partes_FK_1 FOREIGN KEY (idoperario)
    REFERENCES operarios (idoperario)
;
ALTER TABLE partes
    ADD CONSTRAINT partes_FK_2 FOREIGN KEY (idproducto)
    REFERENCES productos (idproducto)
;
ALTER TABLE partes
    ADD CONSTRAINT partes_FK_3 FOREIGN KEY (idturno)
    REFERENCES turnos (idturno)
;

----------------------------------------------------------------------
-- partes                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- perfiles                                                      
----------------------------------------------------------------------

ALTER TABLE perfilturno
    ADD CONSTRAINT perfilturno_FK_1 FOREIGN KEY (idperfil)
    REFERENCES perfiles (idperfil)
;
ALTER TABLE perfilturno
    ADD CONSTRAINT perfilturno_FK_2 FOREIGN KEY (idturno)
    REFERENCES turnos (idturno)
;

----------------------------------------------------------------------
-- perfilturno                                                      
----------------------------------------------------------------------

ALTER TABLE producciones
    ADD CONSTRAINT producciones_FK_1 FOREIGN KEY (idcentro)
    REFERENCES centros (idcentro)
;
ALTER TABLE producciones
    ADD CONSTRAINT producciones_FK_2 FOREIGN KEY (idtipotrabajo)
    REFERENCES tipotrabajos (idtipotrabajo)
;
ALTER TABLE producciones
    ADD CONSTRAINT producciones_FK_3 FOREIGN KEY (idestadoproduccion)
    REFERENCES estadosproduccion (idestadoproduccion)
;

----------------------------------------------------------------------
-- producciones                                                      
----------------------------------------------------------------------

ALTER TABLE productos
    ADD CONSTRAINT productos_FK_1 FOREIGN KEY (idproduccion)
    REFERENCES producciones (idproduccion)
;

----------------------------------------------------------------------
-- productos                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- provincias                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- tipotrabajos                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- turnos                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- estadosproduccion                                                      
----------------------------------------------------------------------

