INSERT INTO users (name)
VALUES ('Leon'),
       ('Anna'),
       ('Kate');

INSERT INTO objects_for_rent(name, price, area, describe, owner_id)
VALUES ('Blue house', 200, 34.5, 'describe1', 1),
       ('Red house', 400, 52.4, 'describe2', 2),
       ('Green House', 249.99, 40, 'describe3', 1);

INSERT INTO reservations(start, end, landlord, object_for_rent, tenant)
VALUES ('2022-04-28', '2022-05-03', 1, 1, 2),
       ('2022-05-10', '2022-05-20', 2, 2, 1),
       ('2022-06-01', '2022-06-20', 1, 3, 3);
