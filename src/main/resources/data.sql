ALTER TABLE APP_USER ALTER COLUMN ID RESTART WITH 10;

INSERT INTO APP_USER
(id, email                 , password                                           , role, name        ) VALUES
(1 , 'caprichosa@email.com', 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 0   , 'Caprichosa'),
(2 , 'carnes@email.com'    , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Carniceria'),
(3 , 'lacteos@email.com'   , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Lacteos'   ),
(4 , 'pescados@email.com'  , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Pescaderia'),
(5 , 'vinos@email.com'     , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Bodega'    ),
(6 , 'cerveza@email.com'   , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Cervecería'),
(7 , 'frutas@email.com'    , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Frutería'  ),
(8 , 'refrescos@email.com' , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Refrescos' ),
(9 , 'postres@email.com'   , 'ui5ZvyPp6mHtb0/d8bTagw==:MaP9vP+OqaGFwbqezdfE8w==', 1   , 'Repostería');

ALTER TABLE PRODUCT ALTER COLUMN ID RESTART WITH 19;

INSERT INTO PRODUCT
(id, product_group, product_name, product_price, app_user_id) VALUES
(1 , 'Carne'      , 'Solomillo' , 10           , 2),
(2 , 'Carne'      , 'Pollo'     , 3            , 2),
(3 , 'Carne'      , 'Ternera'   , 6            , 2),
(4 , 'Lacteos'    , 'Leche'     , 1            , 3),
(5 , 'Lacteos'    , 'Queso'     , 2            , 3),
(6 , 'Lacteos'    , 'Yogur'     , 1            , 3),
(7 , 'Pescado'    , 'Merluza'   , 8            , 4),
(8 , 'Pescado'    , 'Salmón'    , 6            , 4),
(9 , 'Vino'       , 'Tinto'     , 5            , 5),
(10, 'Vino'       , 'Verdejo'   , 6            , 5),
(11, 'Cerveza'    , 'Mahou'     , 1            , 6),
(12, 'Fruta'      , 'Melón'     , 4            , 7),
(13, 'Fruta'      , 'Sandía'    , 3            , 7),
(14, 'Refresco'   , 'CocaCola'  , 2            , 8),
(15, 'Refresco'   , 'Aquarius'  , 1            , 8),
(16, 'Postre'     , 'Flan'      , 3            , 9),
(17, 'Postre'     , 'Tarta'     , 5            , 9),
(18, 'Postre'     , 'Helado'    , 3            , 9);
