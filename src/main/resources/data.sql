INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact,amount_free_tickets,amount_payed_tickets,price_ticket) VALUES ('curso de culinária','culinária francesa','2021-10-03','2021-10-05','19:30','10:00', 'gabi@gmail.com',10,10,10.0 )
INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact,amount_free_tickets,amount_payed_tickets,price_ticket) VALUES ('curso de desenho','desenhos realistas','2021-12-20','2021-12-22','18:00','21:30', 'luciane@hotmail.com',10,10,15.0 )
INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact,amount_free_tickets,amount_payed_tickets,price_ticket) VALUES ('curso de violão','acordes básicos','2021-08-07','2021-08-08','19:30','22:00', 'pedro@gmail.com',10,10,5.0  )
INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact,amount_free_tickets,amount_payed_tickets,price_ticket) VALUES ('curso de ukulele','notas e batidas','2021-05-07','2021-05-09','18:30','11:00', 'ricardo@hotmail.com',10,10,10.0  )

INSERT INTO TB_PLACE (name, address) VALUES ('SorocabaClub','Rua São Bento n 121')
INSERT INTO TB_PLACE (name, address) VALUES ('VotorantimClub','Avenida 31 de Março n 1000')
INSERT INTO TB_PLACE (name, address) VALUES ('SoroTimClub','Avenida 01 de Abril n 42')

INSERT INTO TB_BASEUSER(name, email_contact) VALUES ('Charlies Junior', 'charles@outlook.com')
INSERT INTO TB_BASEUSER(name, email_contact) VALUES ('Bia Albuquerque', 'bia12@outlook.com')
INSERT INTO TB_BASEUSER(name, email_contact) VALUES ('Afonso Malta', 'affonso45@outlook.com')
INSERT INTO TB_BASEUSER(name, email_contact) VALUES ('Jessy Silva', 'Jessy@hotmail.com')
INSERT INTO TB_BASEUSER(name, email_contact) VALUES ('Maurício Gabriel', 'mgg@hotmail.com')
INSERT INTO TB_BASEUSER(name, email_contact) VALUES ('Julia Pires', 'Jpeg@hotmail.com')

INSERT INTO TB_ADMIN(baseuser_id, phone_number) VALUES (1,'996543234')
INSERT INTO TB_ADMIN(baseuser_id, phone_number) VALUES (2,'990549234')
INSERT INTO TB_ADMIN(baseuser_id, phone_number) VALUES (3,'996553234')

INSERT INTO TB_ATTEND(baseuser_id, balance) VALUES (4, 0.0)
INSERT INTO TB_ATTEND(baseuser_id, balance) VALUES (5, 0.0)
INSERT INTO TB_ATTEND(baseuser_id, balance) VALUES (6, 0.0)

INSERT INTO TB_TICKET (price,type,date) VALUES (15.00,1,'2021-06-14')
INSERT INTO TB_TICKET (price,type,date) VALUES (20.00,1,'2021-07-14')
INSERT INTO TB_TICKET (price,type,date) VALUES (10.00,1,'2021-07-15')
