insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');
insert into guest(id, name) values(null, 'Diego Rosa');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'Brazil Open - Court Gustavo Kuerten');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2020-08-20T20:00:00.0', '2020-01-20T21:00:00.0', 1);