delete from skills;
insert into skills (name, active) values
    ('programming', true),
    ('managing', true),
    ('translating', true);

delete from specialties;
insert into specialties (name, active) values
    ('developer', true),
    ('designer', true),
    ('manager', true)

delete from developers;
insert into developers (first_name, last_name, specialty_id, active) values
    ('Azamat', 'Komaev', 1, true),
    ('Sasha', 'Sashavich', 2, true),
    ('Alina', 'John', 3, true)

delete from developers_skills;
insert into developers_skills (developer_id, skill_id) values
    (1, 1),
    (1, 3),
    (2, 2),
    (3, 2),
    (3, 3);


select d.id, d.first_name, d.active, s.name
from developers d left join developers_skills ds on d.id = ds.developer_id
                  left join skills s on ds.skill_id = s.id;
