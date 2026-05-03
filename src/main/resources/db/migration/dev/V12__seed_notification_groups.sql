INSERT INTO notification_groups (id, name, description, linked_role, created_at)
VALUES
    ('a1b2c3d4-0001-0001-0001-000000000001', 'Administradores', 'Grupo dos administradores do sistema',  'ADMINISTRADOR', now()),
    ('a1b2c3d4-0002-0002-0002-000000000002', 'Líderes',         'Grupo dos líderes de equipe',           'LIDER',         now()),
    ('a1b2c3d4-0003-0003-0003-000000000003', 'Supervisores',    'Grupo dos supervisores',                'SUPERVISOR',    now()),
    ('a1b2c3d4-0004-0004-0004-000000000004', 'Gestores',        'Grupo dos gestores',                    'GESTOR',        now()),
    ('a1b2c3d4-0005-0005-0005-000000000005', 'Eletricistas',    'Grupo dos eletricistas',                'ELETRICISTA',   now()),
    ('a1b2c3d4-0006-0006-0006-000000000006', 'Mecânicos',       'Grupo dos mecânicos',                   'MECANICO',      now());