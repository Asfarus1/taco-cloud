truncate table Taco_Order_Tacos;
truncate table Taco_Ingredients;
truncate table Taco;
truncate table Taco_Order;
truncate table Ingredient;
insert into Ingredient (id, name, type)
values ('FLTO', 'Flour Tortilla', 'WRAP'),
       ('COTO', 'Corn Tortilla', 'WRAP'),
       ('GRBF', 'Ground Beef', 'PROTEIN'),
       ('CARN', 'Carnitas', 'PROTEIN'),
       ('TMTO', 'Diced Tomatoes', 'VEGGIES'),
       ('LETC', 'Lettuce', 'VEGGIES'),
       ('CHED', 'Cheddar', 'CHEESE'),
       ('JACK', 'Monterrey Jack', 'CHEESE'),
       ('SLSA', 'Salsa', 'SAUCE'),
       ('SRCR', 'Sour Cream', 'SAUCE');