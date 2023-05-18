
SELECT * FROM producto p;

-- Creando Tabla de categorias

/*
CREATE TABLE CATEGORIA (
ID int AUTO_INCREMENT,
NOMBRE varchar(50) NOT NULL,
PRIMARY KEY(ID)
)ENGINE=INNODB;
*/

SELECT * FROM CATEGORIA c;


/*
INSERT INTO categoria(NOMBRE) VALUES('MUEBLES'),('TECNOLOGIA'),('COCINA'),
                                    ('ZAPATILLAS'),('JUGUETE'),('ROPA'),
                                    ('FRUTAS'),('ESCOLAR'),('MOTOS'),
                                    ('AUTOS'),('HOGAR'),('BELLEZA');
*/


ALTER TABLE producto ADD COLUMN CATEGORIA_ID int;

ALTER TABLE producto ADD FOREIGN KEY (CATEGORIA_ID) REFERENCES categoria(ID);

DESC producto;

/*
UPDATE producto p set 
p.CATEGORIA_ID = 1
WHERE ID = 1
*/
;

--

SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD
  FROM CATEGORIA C
 INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID
 ;


                  