\c dservice
\echo 'runnin...'

CREATE TABLE public.users (
                              id uuid DEFAULT gen_random_uuid() NOT NULL,
                              first_name text,
                              last_name text,
                              age integer
);


ALTER TABLE public.users OWNER TO postgres;
ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

INSERT INTO public.users VALUES ('cc337513-db12-4d22-a7d1-c9ca07eb2841', 'Иван', 'Иванов', 28);
INSERT INTO public.users VALUES ('b962d33b-d4d5-4e5e-a05e-3375951971a7', 'Мария', 'Петрова', 34);
INSERT INTO public.users VALUES ('edb293df-0a28-43b5-8f19-a34f9b3c364b', 'Алексей', 'Смирнов', 22);
INSERT INTO public.users VALUES ('83d7cee0-8fcb-4afe-b234-981dab1b86ea', 'Елена', 'Кузнецова', 45);
INSERT INTO public.users VALUES ('23881a3e-2300-4401-aca8-b911f27505ba', 'Дмитрий', 'Попов', 31);
INSERT INTO public.users VALUES ('d5224d3c-8e32-4013-9232-d97a2ae22072', 'Анна', 'Соколова', 29);
INSERT INTO public.users VALUES ('9b069a09-e9da-4780-85bb-53488d210974', 'Сергей', 'Лебедев', 40);
INSERT INTO public.users VALUES ('9126628d-82c0-4be6-a885-4a8faaab9ba0', 'Ольга', 'Козлова', 26);
INSERT INTO public.users VALUES ('8a6fb425-30fa-414c-b3eb-a1abbeb1b53e', 'Михаил', 'Новиков', 53);
INSERT INTO public.users VALUES ('d13bdd05-a417-4cad-bd26-171224b624cf', 'Татьяна', 'Морозова', 37);
INSERT INTO public.users VALUES ('5975ee36-a612-4631-b68b-dd64a5150916', 'Иван', 'Иванов', 28);
INSERT INTO public.users VALUES ('f6b8d129-654c-4dae-800d-906c1e1b84a1', 'Мария', 'Петрова', 34);
INSERT INTO public.users VALUES ('e26e04b1-3973-4fa8-8aea-bb6a6025da5a', 'Сергей', 'Смирнов', 22);
INSERT INTO public.users VALUES ('af1b6e08-be4a-40a6-84b0-65e7f5607344', 'Елена', 'Кузнецова', 45);
INSERT INTO public.users VALUES ('2d7aa5be-c26e-41a3-8b25-4af523296217', 'Сергей', 'Попов', 31);
INSERT INTO public.users VALUES ('c3b6035a-0740-4ab2-91ee-1af061a61e86', 'Анна', 'Соколова', 29);
INSERT INTO public.users VALUES ('6f72d2a6-c16e-44d5-a03c-9b8391a8713b', 'Сергей', 'Лебедев', 40);
INSERT INTO public.users VALUES ('b905f42e-b0a5-45ae-ac91-de9b1a720929', 'Ольга', 'Козлова', 26);
INSERT INTO public.users VALUES ('5c9749a4-639e-4879-92d4-a316f474370f', 'Сергей', 'Новиков', 53);
INSERT INTO public.users VALUES ('017aabe4-3658-4462-b263-bb9b2257b121', 'Татьяна', 'Морозова', 37);
INSERT INTO public.users VALUES ('1146ca62-09a5-4702-93af-6c61fe33366f', 'Настасья', 'Говнова', 67);
INSERT INTO public.users VALUES ('73154fa6-ccc9-47f6-86bf-ac30f6e42605', 'Иван', 'Говнов', 52);
INSERT INTO public.users VALUES ('c746448a-2922-4f84-bf31-3d649af9a058', 'Сикс', 'Севен', 67);