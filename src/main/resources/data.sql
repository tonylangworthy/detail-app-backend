INSERT INTO reconditioning_services (name, description, price, company_id) VALUES
('Tony''s Super Shine', '2-Step buff that removes oxidation, most scratches, and leaves a high-gloss finish.', '249.99', 1001),
('Headlight Restoration', 'Our process removes yellowing and fogginess and returns your headlights to a super clear condition.', '60.00', 1001),
('Old Fashioned Hand Wash', 'Bring er down and get your car hand washed and towel dried.', '19.99', 1001),
('Ceramic Coating (2 Year)', 'One coat of ceramic', '490.00', 1001),
('Ceramic Coating (5 Year)', 'Two coats of ceramic', '790.00', 1001),
('Level 1 Interior Detail', 'Meticulous interior detail, Vacumm upholstery, Clean and protect all vinyl, Protect leather seats, ', '89.99', 1001),
('Level 2 "Down & Dirty" Interior Detail', 'Meticulous interior detail, Vacumm upholstery, Clean and protect all vinyl, Protect leather seats, Hot water shampoo, Clean windows inside and out', '149.99', 1001);
INSERT INTO reconditioning_services (name, description, price, company_id) VALUES
('Brandy''s Quick Clean', 'A quick wash ''n vac', '19.99', 1002),
('Brandy''s Slick Polish', 'Transform your paint to a showroom shine', '59.99', 1002),
('Brandy''s Heavy Duty Paint Correction', 'Removes scratches, oxidation and paint imperfections and leaves your paint looking beautiful', '150.00', 1002),
('Brandy''s Interior Maintenance', 'A quick spot cleaning', '49.99', 1002),
('Brandy''s Wheel Restoration', 'Wheels are cleaned and polished to look as close to new as possible', '79.99', 1002);
SELECT setval('reconditioning_services_id_seq', (SELECT MAX(id) from "reconditioning_services"));

INSERT INTO clocked_reasons (id, name) VALUES (1, 'Clocked In'), (2, 'Clocked Out'), (3, 'Out to Lunch'), (4, 'Back from Lunch');
SELECT setval('clocked_reasons_id_seq', (SELECT MAX(id) from "clocked_reasons"));